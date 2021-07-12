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
package org.vanilladb.bench.benchmarks.as2.rte;

import org.vanilladb.bench.StatisticMgr;
import org.vanilladb.bench.benchmarks.as2.As2BenchConstants;
import org.vanilladb.bench.benchmarks.as2.As2BenchTransactionType;
import org.vanilladb.bench.remote.SutConnection;
import org.vanilladb.bench.rte.RemoteTerminalEmulator;

import java.util.logging.Logger;

public class As2BenchmarkRte extends RemoteTerminalEmulator<As2BenchTransactionType> {
	
	private As2BenchmarkTxExecutor readExecutor;
	private As2BenchmarkTxExecutor updateExecutor;
	private static Logger logger = Logger.getLogger(As2BenchmarkRte.class.getName());

	public As2BenchmarkRte(SutConnection conn, StatisticMgr statMgr) {
		super(conn, statMgr);
		
		updateExecutor = new As2BenchmarkTxExecutor(new As2UpdateItemPriceParamGen());
		readExecutor = new As2BenchmarkTxExecutor(new As2ReadItemParamGen());
	}
	
	public static int readCount = 0;
	public static int updateCount = 0;
	
	protected As2BenchTransactionType getNextTxType() {
		if (Math.random() > As2BenchConstants.READ_WRITE_TX_RATE) {
			return As2BenchTransactionType.UPDATE_ITEM;
		}
		return As2BenchTransactionType.READ_ITEM;
	}
	
	protected As2BenchmarkTxExecutor getTxExeutor(As2BenchTransactionType type) {
		switch(type){
			case UPDATE_ITEM:
				return updateExecutor;
			case READ_ITEM:
				return readExecutor;
			default:
				logger.warning("No Matched Executor, Use Default Executor Instead.");
				return readExecutor;
		}
	}
}