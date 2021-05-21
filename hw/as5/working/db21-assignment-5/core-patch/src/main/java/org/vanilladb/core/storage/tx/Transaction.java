/*******************************************************************************
 * Copyright 2016, 2018 vanilladb.org contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.vanilladb.core.storage.tx;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.vanilladb.core.server.VanillaDb;
import org.vanilladb.core.sql.Constant;
import org.vanilladb.core.sql.Type;
import org.vanilladb.core.storage.buffer.Buffer;
import org.vanilladb.core.storage.buffer.BufferMgr;
import org.vanilladb.core.storage.file.BlockId;
import org.vanilladb.core.storage.log.LogSeqNum;
import org.vanilladb.core.storage.record.RecordId;
import org.vanilladb.core.storage.tx.concurrency.ConcurrencyMgr;
import org.vanilladb.core.storage.tx.recovery.RecoveryMgr;

/**
 * Provides transaction management for clients, ensuring that all transactions
 * are recoverable, and in general satisfy the ACID properties with specified
 * isolation level.
 */
public class Transaction {
	private static Logger logger = Logger.getLogger(Transaction.class.getName());

	private RecoveryMgr recoveryMgr;
	private ConcurrencyMgr concurMgr;
	private BufferMgr bufferMgr;
	private List<TransactionLifecycleListener> lifecycleListeners;
	private long txNum;
	private boolean readOnly;
	// MODIFIED: Modified Code
	// Does the transaction use 2V2PL or not?
	private boolean is2V2PL = false;
	// The private workspace that keeps the accessed records
	private HashMap<RecordId, Constant> workspace = new HashMap<RecordId, Constant>();
	// The oldVals records the original values before setting values.
	private HashMap<RecordId, Constant> originalVals = new HashMap<RecordId, Constant>();
	private HashMap<RecordId, Buffer> modifiedBuffers = new HashMap<RecordId, Buffer>();
	private HashMap<RecordId, Boolean> correspondDoLogs = new HashMap<RecordId, Boolean>();

	class LogRecord{
		Buffer buffer;
		BlockId blk;
		int offset;
		Constant oldVal;
		Constant newVal;
		boolean doLog;

		LogRecord(Buffer currentBuff, BlockId blk, int offset, Constant oldVal, Constant newVal, boolean doLog){
			this.buffer = currentBuff;
			this.blk = blk;
			this.offset = offset;
			this.oldVal = oldVal;
			this.newVal = newVal;
			this.doLog = doLog;
		}
	}

	private LinkedList<LogRecord> logs = new LinkedList<LogRecord>();

	/**
	 * Creates a new transaction and associates it with a recovery manager, a
	 * concurrency manager, and a buffer manager. This constructor depends on
	 * the file, log, and buffer managers from {@link VanillaDb}, which are
	 * created during system initialization. Thus this constructor cannot be
	 * called until {@link VanillaDb#init(String)} is called first.
	 * 
	 * @param txMgr
	 *            the transaction manager
	 * @param concurMgr
	 *            the associated concurrency manager
	 * @param recoveryMgr
	 *            the associated recovery manager
	 * @param bufferMgr
	 *            the associated buffer manager
	 * @param readOnly
	 *            is read-only mode
	 * @param txNum
	 *            the number of the transaction
	 */
	public Transaction(TransactionMgr txMgr, TransactionLifecycleListener concurMgr,
			TransactionLifecycleListener recoveryMgr, TransactionLifecycleListener bufferMgr, boolean readOnly,
			long txNum) {
		this.concurMgr = (ConcurrencyMgr) concurMgr;
		this.recoveryMgr = (RecoveryMgr) recoveryMgr;
		this.bufferMgr = (BufferMgr) bufferMgr;
		this.txNum = txNum;
		this.readOnly = readOnly;

		lifecycleListeners = new LinkedList<TransactionLifecycleListener>();
		// XXX: A transaction manager must be added before a recovery manager to
		// prevent the following scenario:
		// <COMMIT 1>
		// <NQCKPT 1,2>
		//
		// Although, it may create another scenario like this:
		// <NQCKPT 2>
		// <COMMIT 1>
		// But the current algorithm can still recovery correctly during this
		// scenario.
		addLifecycleListener(txMgr);
		/*
		 * A recover manager must be added before a concurrency manager. For
		 * example, if the transaction need to roll back, it must hold all locks
		 * until the recovery procedure complete.
		 */
		addLifecycleListener(recoveryMgr);
		addLifecycleListener(concurMgr);
		addLifecycleListener(bufferMgr);
	}

	public void addLifecycleListener(TransactionLifecycleListener listener) {
		lifecycleListeners.add(listener);
	}

	/**
	 * Commits the current transaction. Flushes all modified blocks (and their
	 * log records), writes and flushes a commit record to the log, releases all
	 * locks, and unpins any pinned blocks.
	 */
	public void commit() {
		certifyLog();

		for (TransactionLifecycleListener l : lifecycleListeners)
			l.onTxCommit(this);

		if (logger.isLoggable(Level.FINE))
			logger.fine("transaction " + txNum + " committed");
	}

	/**
	 * Rolls back the current transaction. Undoes any modified values, flushes
	 * those blocks, writes and flushes a rollback record to the log, releases
	 * all locks, and unpins any pinned blocks.
	 */
	public void rollback() {
		for (TransactionLifecycleListener l : lifecycleListeners)
			l.onTxRollback(this);

		if (logger.isLoggable(Level.FINE))
			logger.fine("transaction " + txNum + " rolled back");
	}

	/**
	 * Finishes the current statement. Releases slocks obtained so far for
	 * repeatable read isolation level and does nothing in serializable
	 * isolation level. This method should be called after each SQL statement.
	 */
	public void endStatement() {
		for (TransactionLifecycleListener l : lifecycleListeners)
			l.onTxEndStatement(this);
	}

	public long getTransactionNumber() {
		return this.txNum;
	}

	public boolean isReadOnly() {
		return this.readOnly;
	}

	public RecoveryMgr recoveryMgr() {
		return recoveryMgr;
	}

	public ConcurrencyMgr concurrencyMgr() {
		return concurMgr;
	}

	public BufferMgr bufferMgr() {
		return bufferMgr;
	}

	// MODIFIED: Modified Code
	public void use2V2PL(){
		this.is2V2PL = true;
	}

	public HashMap<RecordId, Constant> workspace(){
		return workspace;
	}

	public HashMap<RecordId, Constant> originalVals(){
		return originalVals;
	}

	public HashMap<RecordId, Buffer> modifiedBuffers(){
		return modifiedBuffers;
	}

	public Constant addGetVal(Buffer currentBuff, BlockId blk, int offset, Type type){
		RecordId target_record = new RecordId(blk, offset);
		Constant workspce_val = workspace.get(target_record);

		if(workspce_val == null){
			Constant val = currentBuff.getVal(offset, type);
			workspace.put(target_record, val);
			return val;
		}
		
		return workspce_val;
	}

	public void addSetVal(Buffer currentBuff, BlockId blk, int offset, Constant val, boolean doLog){
		RecordId target_record = new RecordId(blk, offset);
		Constant originalVal = currentBuff.getVal(offset, val.getType());

		originalVals.putIfAbsent(target_record, originalVal);
		modifiedBuffers.put(target_record, currentBuff);
		workspace.put(target_record, val);
		correspondDoLogs.put(target_record, Boolean.valueOf(doLog));
		
		// LogRecord log = new LogRecord(currentBuff, blk, offset, originalVal, val, doLog);
		// logs.add(log);
	}

	private void certifyLog(){
		if(!this.is2V2PL){return;}

		for(HashMap.Entry<RecordId, Buffer> entry : modifiedBuffers.entrySet()){
			RecordId targetRecord = entry.getKey();
			Buffer targetBuffer = entry.getValue();
			boolean doLog = correspondDoLogs.get(targetRecord);

			BlockId blk = targetRecord.block();
			int offset = targetRecord.id();

			Constant oldVal = originalVals.get(targetRecord);
			Constant newVal = workspace.get(targetRecord);

			if(oldVal != newVal){
				LogSeqNum lsn = doLog ? recoveryMgr().certifyLogSetVal(blk, offset, oldVal, newVal) : null;
				targetBuffer.setVal(offset, newVal, this.txNum, lsn);
				concurMgr.certifyRecord(targetRecord);
			}
		}

		// for(LogRecord l: logs){
		// 	LogSeqNum lsn = l.doLog ? recoveryMgr().certifyLogSetVal(l.blk, l.offset, l.oldVal, l.newVal) : null;
		// 	l.buffer.setVal(l.offset, l.newVal, this.txNum, lsn);
		// }
	}
}
