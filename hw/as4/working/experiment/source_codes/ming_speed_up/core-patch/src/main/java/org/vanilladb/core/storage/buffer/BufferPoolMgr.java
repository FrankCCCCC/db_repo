/*******************************************************************************
 * Copyright 2016, 2017 vanilladb.org contributors
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
package org.vanilladb.core.storage.buffer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.vanilladb.core.server.VanillaDb;
import org.vanilladb.core.storage.file.BlockId;
import org.vanilladb.core.storage.file.FileMgr;

/**
 * Manages the pinning and unpinning of buffers to blocks.
 */
class BufferPoolMgr {
	private static final Logger logger = Logger.getLogger(BufferPoolMgr.class.getName());
	private final Buffer[] bufferPool;
	private final Map<BlockId, Buffer> blockMap;
	private final AtomicInteger numAvailable;
	private int lastReplacedBuff;

	private final Object[] buckets = new Object[1024];

	/**
	 * Creates a buffer manager having the specified number of buffer slots. This
	 * constructor depends on both the {@link FileMgr} and
	 * {@link org.vanilladb.core.storage.log.LogMgr LogMgr} objects that it gets
	 * from the class {@link VanillaDb}. Those objects are created during system
	 * initialization. Thus this constructor cannot be called until
	 * {@link VanillaDb#initFileAndLogMgr(String)} or is called first.
	 * 
	 * @param numBuffs the number of buffer slots to allocate
	 */
	BufferPoolMgr(int numBuffs) {
		if (logger.isLoggable(Level.INFO))
			logger.info("Creating " + numBuffs + " buffers");

		bufferPool = new Buffer[numBuffs];
		blockMap = new ConcurrentHashMap<BlockId, Buffer>(numBuffs);
		numAvailable = new AtomicInteger(numBuffs);
		lastReplacedBuff = 0;
		for (int i = 0; i < numBuffs; i++)
			bufferPool[i] = new Buffer();

		for (int i = 0; i < buckets.length; i++) {
			buckets[i] = new Object();
		}

		if (logger.isLoggable(Level.INFO))
			logger.info("Buffer pool is ready (assignment 4 version)");
	}

    // Optimization: Lock striping
    private Object getBlockObject(Object o) {
        int hash = o.hashCode() % buckets.length;
        if (hash < 0) {
			hash += buckets.length;
		}
		return buckets[hash];
    }

    /**
	 * Flushes all dirty buffers.
	 */
	synchronized void flushAll() {
		for (Buffer buff : bufferPool)
			buff.flush();
	}

	/**
	 * Pins a buffer to the specified block. If there is already a buffer assigned
	 * to that block then that buffer is used; otherwise, an unpinned buffer from
	 * the pool is chosen. Returns a null value if there are no available buffers.
	 * 
	 * @param blk a block ID
	 * @return the pinned buffer
	 */
	Buffer pin(BlockId blk) {
		synchronized(getBlockObject(blk)) {
			Buffer buff = findExistingBuffer(blk);

			if (buff == null) {
				buff = chooseUnpinnedBuffer();
				if (buff == null) {
					return null;
				}

				try {
					BlockId oldBlk = buff.block();
					if (oldBlk != null) {
						blockMap.remove(oldBlk);
					}

					buff.assignToBlock(blk);
					blockMap.put(blk, buff);

					numAvailable.decrementAndGet();

					return buff;
				} finally {
					buff.RWLock.writeLock().unlock();
				}
			}

			buff.RWLock.writeLock().lock();
			try {
				if (!buff.block().equals(blk)) {
					return null;
				}

				if (!buff.isPinned()) {
					numAvailable.decrementAndGet();
				}

				buff.pin();

				return buff;
			} finally {
				buff.RWLock.writeLock().unlock();
			}
		}
	}

	/**
	 * Allocates a new block in the specified file, and pins a buffer to it. Returns
	 * null (without allocating the block) if there are no available buffers.
	 * 
	 * @param fileName the name of the file
	 * @param fmtr     a pageformatter object, used to format the new block
	 * @return the pinned buffer
	 */
	synchronized Buffer pinNew(String fileName, PageFormatter fmtr) {
		Buffer buff = chooseUnpinnedBuffer();
		if (buff == null) {
			return null;
		}

		BlockId oldBlk = buff.block();
		if (oldBlk != null)
			blockMap.remove(oldBlk);

		buff.assignToNew(fileName, fmtr);
		numAvailable.decrementAndGet();
		blockMap.put(buff.block(), buff);
		buff.RWLock.writeLock().unlock();

		return buff;
	}

	/**
	 * Unpins the specified buffers.
	 * 
	 * @param buffs the buffers to be unpinned
	 */
	void unpin(Buffer... buffs) {
		for (Buffer buff : buffs) {
		    buff.RWLock.writeLock().lock();
			buff.unpin();
			if (!buff.isPinned()) {
				numAvailable.incrementAndGet();
			}
			buff.RWLock.writeLock().unlock();
		}
	}

	/**
	 * Returns the number of available (i.e. unpinned) buffers.
	 * 
	 * @return the number of available buffers
	 */
	int available() {
		return numAvailable.get();
	}

	private Buffer findExistingBuffer(BlockId blk) {
		Buffer buff = blockMap.get(blk);
		if (buff != null && buff.block().equals(blk))
			return buff;
		return null;
	}

	private Buffer chooseUnpinnedBuffer() {
		int currBlk = (lastReplacedBuff + 1) % bufferPool.length;
		while (currBlk != lastReplacedBuff) {
			Buffer buff = bufferPool[currBlk];

			if (buff.RWLock.writeLock().tryLock()) {
				if (!buff.isPinnedAndPin()) {
					lastReplacedBuff = currBlk;
					return buff;
				}

				buff.RWLock.writeLock().unlock();
			}

			currBlk = (currBlk + 1) % bufferPool.length;
		}
		return null;
	}
}
