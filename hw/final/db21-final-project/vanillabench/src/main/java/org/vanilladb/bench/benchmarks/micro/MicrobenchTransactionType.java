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
package org.vanilladb.bench.benchmarks.micro;

import org.vanilladb.bench.BenchTransactionType;

public enum MicrobenchTransactionType implements BenchTransactionType {
	// Loading procedures
	TESTBED_LOADER(false),
	
	// Database checking procedures
	CHECK_DATABASE(false),
	
	// Benchmarking procedures
	MICRO_TXN(true);
	
	public static MicrobenchTransactionType fromProcedureId(int pid) {
		return MicrobenchTransactionType.values()[pid];
	}
	
	private boolean isBenchProc;
	
	MicrobenchTransactionType(boolean isBenchProc) {
		this.isBenchProc = isBenchProc;
	}
	
	@Override
	public int getProcedureId() {
		return this.ordinal();
	}

	@Override
	public boolean isBenchmarkingProcedure() {
		return isBenchProc;
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
package org.vanilladb.bench.benchmarks.micro;

import org.vanilladb.bench.BenchTransactionType;

public enum MicrobenchTransactionType implements BenchTransactionType {
	// Loading procedures
	TESTBED_LOADER(false),
	
	// Database checking procedures
	CHECK_DATABASE(false),
	
	// Benchmarking procedures
	MICRO_TXN(true);
	
	public static MicrobenchTransactionType fromProcedureId(int pid) {
		return MicrobenchTransactionType.values()[pid];
	}
	
	private boolean isBenchProc;
	
	MicrobenchTransactionType(boolean isBenchProc) {
		this.isBenchProc = isBenchProc;
	}
	
	@Override
	public int getProcedureId() {
		return this.ordinal();
	}

	@Override
	public boolean isBenchmarkingProcedure() {
		return isBenchProc;
	}
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
