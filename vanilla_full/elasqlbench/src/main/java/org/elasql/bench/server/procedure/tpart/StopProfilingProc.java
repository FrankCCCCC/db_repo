package org.elasql.bench.server.procedure.tpart;

import java.util.Map;

import org.elasql.cache.CachedRecord;
import org.elasql.procedure.tpart.TPartStoredProcedure;
import org.elasql.server.Elasql;
import org.elasql.sql.RecordKey;
import org.vanilladb.core.sql.storedprocedure.StoredProcedureParamHelper;

public class StopProfilingProc extends TPartStoredProcedure<StoredProcedureParamHelper> {

	public StopProfilingProc(long txNum) {
		super(txNum, StoredProcedureParamHelper.newDefaultParamHelper());
	}

	@Override
	protected void prepareKeys() {
		// do nothing

	}

	@Override
	protected void executeSql(Map<RecordKey, CachedRecord> readings) {
		Elasql.stopProfilerAndReport();
	}
	
	@Override
	public ProcedureType getProcedureType() {
		return ProcedureType.UTILITY;
	}
	
	@Override
	public double getWeight() {
		return 0;
	}
}
