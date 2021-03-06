<<<<<<< HEAD
package org.vanilladb.bench.server.procedure.tpce;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.vanilladb.core.sql.storedprocedure.StoredProcedure;
import org.vanilladb.core.sql.storedprocedure.StoredProcedureParamHelper;

public class TpceCheckDatabaseProc extends StoredProcedure<StoredProcedureParamHelper> {
	private static Logger logger = Logger.getLogger(TpceCheckDatabaseProc.class.getName());
	
	public TpceCheckDatabaseProc() {
		super(StoredProcedureParamHelper.newDefaultParamHelper());
	}

	@Override
	protected void executeSql() {
		if (logger.isLoggable(Level.INFO))
			logger.info("Checking database for the TPC-E benchmarks...");

		// TODO: Implement verification
		abort("TODO: implement the checking procedure for TPC-E");

		if (logger.isLoggable(Level.INFO))
			logger.info("Checking completed.");
	}
}
=======
package org.vanilladb.bench.server.procedure.tpce;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.vanilladb.core.sql.storedprocedure.StoredProcedure;
import org.vanilladb.core.sql.storedprocedure.StoredProcedureParamHelper;

public class TpceCheckDatabaseProc extends StoredProcedure<StoredProcedureParamHelper> {
	private static Logger logger = Logger.getLogger(TpceCheckDatabaseProc.class.getName());
	
	public TpceCheckDatabaseProc() {
		super(StoredProcedureParamHelper.newDefaultParamHelper());
	}

	@Override
	protected void executeSql() {
		if (logger.isLoggable(Level.INFO))
			logger.info("Checking database for the TPC-E benchmarks...");

		// TODO: Implement verification
		abort("TODO: implement the checking procedure for TPC-E");

		if (logger.isLoggable(Level.INFO))
			logger.info("Checking completed.");
	}
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
