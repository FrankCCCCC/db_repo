<<<<<<< HEAD
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
package org.vanilladb.bench.benchmarks.tpcc.rte;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.vanilladb.bench.StatisticMgr;
import org.vanilladb.bench.BenchTransactionType;
import org.vanilladb.bench.benchmarks.tpcc.TpccConstants;
import org.vanilladb.bench.benchmarks.tpcc.TpccTransactionType;
import org.vanilladb.bench.remote.SutConnection;
import org.vanilladb.bench.rte.RemoteTerminalEmulator;

public class TpccRte extends RemoteTerminalEmulator<TpccTransactionType> {
	
	private static final Random TX_TYPE_RANDOM = new Random();
	
	private int homeWid;
	private Map<BenchTransactionType, TpccTxExecutor> executors;

	public TpccRte(SutConnection conn, StatisticMgr statMgr, int homeWarehouseId) {
		super(conn, statMgr);
		homeWid = homeWarehouseId;
		executors = new HashMap<BenchTransactionType, TpccTxExecutor>();
		executors.put(TpccTransactionType.NEW_ORDER, new TpccTxExecutor(new NewOrderParamGen(homeWid)));
		executors.put(TpccTransactionType.PAYMENT, new TpccTxExecutor(new PaymentParamGen(homeWid)));
		executors.put(TpccTransactionType.ORDER_STATUS, new TpccTxExecutor(new OrderStatusParamGen(homeWid)));
		executors.put(TpccTransactionType.DELIVERY, new TpccTxExecutor(new DeliveryParamGen(homeWid)));
		executors.put(TpccTransactionType.STOCK_LEVEL, new TpccTxExecutor(new StockLevelParamGen(homeWid)));
	}
	
	protected TpccTransactionType getNextTxType() {
		int index = TX_TYPE_RANDOM.nextInt(TpccConstants.FREQUENCY_TOTAL);
		if (index < TpccConstants.RANGE_NEW_ORDER)
			return TpccTransactionType.NEW_ORDER;
		else if (index < TpccConstants.RANGE_PAYMENT)
			return TpccTransactionType.PAYMENT;
		else if (index < TpccConstants.RANGE_ORDER_STATUS)
			return TpccTransactionType.ORDER_STATUS;
		else if (index < TpccConstants.RANGE_DELIVERY)
			return TpccTransactionType.DELIVERY;
		else
			return TpccTransactionType.STOCK_LEVEL;
	}
	
	protected TpccTxExecutor getTxExeutor(TpccTransactionType type) {
		return executors.get(type);
	}
}
=======
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
package org.vanilladb.bench.benchmarks.tpcc.rte;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.vanilladb.bench.StatisticMgr;
import org.vanilladb.bench.BenchTransactionType;
import org.vanilladb.bench.benchmarks.tpcc.TpccConstants;
import org.vanilladb.bench.benchmarks.tpcc.TpccTransactionType;
import org.vanilladb.bench.remote.SutConnection;
import org.vanilladb.bench.rte.RemoteTerminalEmulator;

public class TpccRte extends RemoteTerminalEmulator<TpccTransactionType> {
	
	private static final Random TX_TYPE_RANDOM = new Random();
	
	private int homeWid;
	private Map<BenchTransactionType, TpccTxExecutor> executors;

	public TpccRte(SutConnection conn, StatisticMgr statMgr, int homeWarehouseId) {
		super(conn, statMgr);
		homeWid = homeWarehouseId;
		executors = new HashMap<BenchTransactionType, TpccTxExecutor>();
		executors.put(TpccTransactionType.NEW_ORDER, new TpccTxExecutor(new NewOrderParamGen(homeWid)));
		executors.put(TpccTransactionType.PAYMENT, new TpccTxExecutor(new PaymentParamGen(homeWid)));
		executors.put(TpccTransactionType.ORDER_STATUS, new TpccTxExecutor(new OrderStatusParamGen(homeWid)));
		executors.put(TpccTransactionType.DELIVERY, new TpccTxExecutor(new DeliveryParamGen(homeWid)));
		executors.put(TpccTransactionType.STOCK_LEVEL, new TpccTxExecutor(new StockLevelParamGen(homeWid)));
	}
	
	protected TpccTransactionType getNextTxType() {
		int index = TX_TYPE_RANDOM.nextInt(TpccConstants.FREQUENCY_TOTAL);
		if (index < TpccConstants.RANGE_NEW_ORDER)
			return TpccTransactionType.NEW_ORDER;
		else if (index < TpccConstants.RANGE_PAYMENT)
			return TpccTransactionType.PAYMENT;
		else if (index < TpccConstants.RANGE_ORDER_STATUS)
			return TpccTransactionType.ORDER_STATUS;
		else if (index < TpccConstants.RANGE_DELIVERY)
			return TpccTransactionType.DELIVERY;
		else
			return TpccTransactionType.STOCK_LEVEL;
	}
	
	protected TpccTxExecutor getTxExeutor(TpccTransactionType type) {
		return executors.get(type);
	}
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
