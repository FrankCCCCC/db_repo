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
package org.vanilladb.bench.server.procedure.as2;

// import java.util.logging.Logger;

// import org.vanilladb.bench.VanillaBench;
import org.vanilladb.bench.benchmarks.as2.As2BenchTransactionType;
import org.vanilladb.core.sql.storedprocedure.StoredProcedure;
import org.vanilladb.core.sql.storedprocedure.StoredProcedureFactory;

public class As2BenchStoredProcFactory implements StoredProcedureFactory {

	@Override
	public StoredProcedure<?> getStroredProcedure(int pid) {
		// Logger logger = Logger.getLogger(VanillaBench.class.getName());
		StoredProcedure<?> sp;
		switch (As2BenchTransactionType.fromProcedureId(pid)) {
		case TESTBED_LOADER:
			sp = new TestbedLoaderProc();
			break;
		case CHECK_DATABASE:
			sp = new As2CheckDatabaseProc();
			break;
		case UPDATE_ITEM:
			// logger.info("UPDATE_ITEM PROCEDURE" + " " + As2BenchTransactionType.UPDATE_ITEM);
			sp = new As2UpdateItemPriceTxnProc();
			break;
		case READ_ITEM:
			// logger.info("READ_ITEM PROCEDURE" + " " + As2BenchTransactionType.READ_ITEM);
			sp = new ReadItemTxnProc();
			break;
		default:
			throw new UnsupportedOperationException("The benchmarker does not recognize procedure " + pid + "");
		}
		return sp;
	}
}
