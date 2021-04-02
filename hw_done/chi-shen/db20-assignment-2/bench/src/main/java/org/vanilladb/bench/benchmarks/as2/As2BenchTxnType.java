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
package org.vanilladb.bench.benchmarks.as2;

import org.vanilladb.bench.BenchTransactionType;


public enum As2BenchTxnType implements BenchTransactionType {
	// Loading procedures
	TESTBED_LOADER(false),
	
	// Database checking procedures
	CHECK_DATABASE(false),
	
	// Benchmarking procedures
	READ_ITEM(true),
	
	// I want to create a new type update_item
	UPDATE_ITEM(true);
	
	public static As2BenchTxnType fromProcedureId(int pid) {
		return As2BenchTxnType.values()[pid];
	}
	
	private boolean isBenchProc;
	
	As2BenchTxnType(boolean isBenchProc) {
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
