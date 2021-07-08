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
package org.vanilladb.bench.benchmarks.tpce.data;

public class Trade {
	
	private long tradeId;
	private long customerId;
	private long customerAccountId;
	private long brokerId;

	public Trade(long tradeId, long customerId, long customerAccountId,
			long brokerId) {
		this.tradeId = tradeId;
		this.customerId = customerId;
		this.customerAccountId = customerAccountId;
		this.brokerId = brokerId;
	}

	public long getTradeId() {
		return tradeId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public long getCustomerAccountId() {
		return customerAccountId;
	}

	public long getBrokerId() {
		return brokerId;
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
package org.vanilladb.bench.benchmarks.tpce.data;

public class Trade {
	
	private long tradeId;
	private long customerId;
	private long customerAccountId;
	private long brokerId;

	public Trade(long tradeId, long customerId, long customerAccountId,
			long brokerId) {
		this.tradeId = tradeId;
		this.customerId = customerId;
		this.customerAccountId = customerAccountId;
		this.brokerId = brokerId;
	}

	public long getTradeId() {
		return tradeId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public long getCustomerAccountId() {
		return customerAccountId;
	}

	public long getBrokerId() {
		return brokerId;
	}
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
