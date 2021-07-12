<<<<<<< HEAD
package org.elasql.bench.benchmarks.tpcc;

import org.vanilladb.bench.StatisticMgr;
import org.vanilladb.bench.benchmarks.tpcc.TpccTransactionType;
import org.vanilladb.bench.remote.SutConnection;
import org.vanilladb.bench.rte.RemoteTerminalEmulator;

public interface TpccRteGenerator {
	
	int getNumOfRTEs();
	
	RemoteTerminalEmulator<TpccTransactionType> createRte(SutConnection conn, StatisticMgr statMgr);
	
}
=======
package org.elasql.bench.benchmarks.tpcc;

import org.vanilladb.bench.StatisticMgr;
import org.vanilladb.bench.benchmarks.tpcc.TpccTransactionType;
import org.vanilladb.bench.remote.SutConnection;
import org.vanilladb.bench.rte.RemoteTerminalEmulator;

public interface TpccRteGenerator {
	
	int getNumOfRTEs();
	
	RemoteTerminalEmulator<TpccTransactionType> createRte(SutConnection conn, StatisticMgr statMgr);
	
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
