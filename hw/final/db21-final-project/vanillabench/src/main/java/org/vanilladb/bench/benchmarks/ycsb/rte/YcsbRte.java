<<<<<<< HEAD
package org.vanilladb.bench.benchmarks.ycsb.rte;

import org.vanilladb.bench.StatisticMgr;
import org.vanilladb.bench.benchmarks.ycsb.YcsbTransactionType;
import org.vanilladb.bench.remote.SutConnection;
import org.vanilladb.bench.rte.RemoteTerminalEmulator;
import org.vanilladb.bench.rte.TransactionExecutor;

public class YcsbRte extends RemoteTerminalEmulator<YcsbTransactionType> {
	
	private YcsbTxExecutor executor;
	
	public YcsbRte(SutConnection conn, StatisticMgr statMgr) {
		super(conn, statMgr);
		executor = new YcsbTxExecutor(new YcsbParamGen());
	}
	
	protected YcsbTransactionType getNextTxType() {
		return YcsbTransactionType.YCSB;
	}
	
	protected TransactionExecutor<YcsbTransactionType> getTxExeutor(YcsbTransactionType type) {
		return executor;
	}
}
=======
package org.vanilladb.bench.benchmarks.ycsb.rte;

import org.vanilladb.bench.StatisticMgr;
import org.vanilladb.bench.benchmarks.ycsb.YcsbTransactionType;
import org.vanilladb.bench.remote.SutConnection;
import org.vanilladb.bench.rte.RemoteTerminalEmulator;
import org.vanilladb.bench.rte.TransactionExecutor;

public class YcsbRte extends RemoteTerminalEmulator<YcsbTransactionType> {
	
	private YcsbTxExecutor executor;
	
	public YcsbRte(SutConnection conn, StatisticMgr statMgr) {
		super(conn, statMgr);
		executor = new YcsbTxExecutor(new YcsbParamGen());
	}
	
	protected YcsbTransactionType getNextTxType() {
		return YcsbTransactionType.YCSB;
	}
	
	protected TransactionExecutor<YcsbTransactionType> getTxExeutor(YcsbTransactionType type) {
		return executor;
	}
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
