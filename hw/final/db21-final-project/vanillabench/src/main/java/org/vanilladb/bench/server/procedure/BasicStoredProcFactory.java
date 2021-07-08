<<<<<<< HEAD
package org.vanilladb.bench.server.procedure;

import org.vanilladb.bench.ControlTransactionType;
import org.vanilladb.core.sql.storedprocedure.StoredProcedure;
import org.vanilladb.core.sql.storedprocedure.StoredProcedureFactory;

public class BasicStoredProcFactory implements StoredProcedureFactory {
	
	private StoredProcedureFactory underlayerFactory;
	
	public BasicStoredProcFactory(StoredProcedureFactory underlayerFactory) {
		this.underlayerFactory = underlayerFactory;
	}

	@Override
	public StoredProcedure<?> getStroredProcedure(int pid) {
		ControlTransactionType txnType = ControlTransactionType.fromProcedureId(pid);
		if (txnType != null) {
			switch (txnType) {
			case START_PROFILING:
				return new StartProfilingProc();
			case STOP_PROFILING:
				return new StopProfilingProc();
			}
		}
		
		return underlayerFactory.getStroredProcedure(pid);
	}
}
=======
package org.vanilladb.bench.server.procedure;

import org.vanilladb.bench.ControlTransactionType;
import org.vanilladb.core.sql.storedprocedure.StoredProcedure;
import org.vanilladb.core.sql.storedprocedure.StoredProcedureFactory;

public class BasicStoredProcFactory implements StoredProcedureFactory {
	
	private StoredProcedureFactory underlayerFactory;
	
	public BasicStoredProcFactory(StoredProcedureFactory underlayerFactory) {
		this.underlayerFactory = underlayerFactory;
	}

	@Override
	public StoredProcedure<?> getStroredProcedure(int pid) {
		ControlTransactionType txnType = ControlTransactionType.fromProcedureId(pid);
		if (txnType != null) {
			switch (txnType) {
			case START_PROFILING:
				return new StartProfilingProc();
			case STOP_PROFILING:
				return new StopProfilingProc();
			}
		}
		
		return underlayerFactory.getStroredProcedure(pid);
	}
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
