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

import org.vanilladb.bench.benchmarks.tpcc.TpccConstants;
import org.vanilladb.bench.benchmarks.tpcc.TpccTransactionType;
import org.vanilladb.bench.benchmarks.tpcc.TpccValueGenerator;

public class DeliveryParamGen implements TpccTxParamGenerator {

	private int homeWid;
	private TpccValueGenerator valueGen = new TpccValueGenerator();

	public DeliveryParamGen(int homeWarehouseId) {
		this.homeWid = homeWarehouseId;
	}

	@Override
	public TpccTransactionType getTxnType() {
		return TpccTransactionType.DELIVERY;
	}

	@Override
	public Object[] generateParameter() {
		Object[] pars = new Object[2];
		pars[0] = homeWid;
		pars[1] = valueGen.number(1, 10);
		return pars;
	}

	@Override
	public long getKeyingTime() {
		return TpccConstants.KEYING_DELIVERY * 1000;
	}

	@Override
	public long getThinkTime() {
		double r = valueGen.rng().nextDouble();
		return (long) -Math.log(r) * TpccConstants.THINKTIME_DELIVERY * 1000;
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

import org.vanilladb.bench.benchmarks.tpcc.TpccConstants;
import org.vanilladb.bench.benchmarks.tpcc.TpccTransactionType;
import org.vanilladb.bench.benchmarks.tpcc.TpccValueGenerator;

public class DeliveryParamGen implements TpccTxParamGenerator {

	private int homeWid;
	private TpccValueGenerator valueGen = new TpccValueGenerator();

	public DeliveryParamGen(int homeWarehouseId) {
		this.homeWid = homeWarehouseId;
	}

	@Override
	public TpccTransactionType getTxnType() {
		return TpccTransactionType.DELIVERY;
	}

	@Override
	public Object[] generateParameter() {
		Object[] pars = new Object[2];
		pars[0] = homeWid;
		pars[1] = valueGen.number(1, 10);
		return pars;
	}

	@Override
	public long getKeyingTime() {
		return TpccConstants.KEYING_DELIVERY * 1000;
	}

	@Override
	public long getThinkTime() {
		double r = valueGen.rng().nextDouble();
		return (long) -Math.log(r) * TpccConstants.THINKTIME_DELIVERY * 1000;
	}

}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
