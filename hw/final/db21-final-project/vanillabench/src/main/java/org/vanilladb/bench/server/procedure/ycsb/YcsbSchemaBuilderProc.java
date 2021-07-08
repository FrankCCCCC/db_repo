<<<<<<< HEAD
package org.vanilladb.bench.server.procedure.ycsb;

import org.vanilladb.bench.server.param.ycsb.YcsbSchemaBuilderProcParamHelper;
import org.vanilladb.bench.server.procedure.StoredProcedureHelper;
import org.vanilladb.core.sql.storedprocedure.StoredProcedure;
import org.vanilladb.core.storage.tx.Transaction;

public class YcsbSchemaBuilderProc extends StoredProcedure<YcsbSchemaBuilderProcParamHelper> {

	public YcsbSchemaBuilderProc() {
		super(new YcsbSchemaBuilderProcParamHelper());
	}

	@Override
	protected void executeSql() {
		YcsbSchemaBuilderProcParamHelper paramHelper = getParamHelper();
		Transaction tx = getTransaction();
		for (String sql : paramHelper.getTableSchemas())
			StoredProcedureHelper.executeUpdate(sql, tx);
		for (String sql : paramHelper.getIndexSchemas())
			StoredProcedureHelper.executeUpdate(sql, tx);
	}
}
=======
package org.vanilladb.bench.server.procedure.ycsb;

import org.vanilladb.bench.server.param.ycsb.YcsbSchemaBuilderProcParamHelper;
import org.vanilladb.bench.server.procedure.StoredProcedureHelper;
import org.vanilladb.core.sql.storedprocedure.StoredProcedure;
import org.vanilladb.core.storage.tx.Transaction;

public class YcsbSchemaBuilderProc extends StoredProcedure<YcsbSchemaBuilderProcParamHelper> {

	public YcsbSchemaBuilderProc() {
		super(new YcsbSchemaBuilderProcParamHelper());
	}

	@Override
	protected void executeSql() {
		YcsbSchemaBuilderProcParamHelper paramHelper = getParamHelper();
		Transaction tx = getTransaction();
		for (String sql : paramHelper.getTableSchemas())
			StoredProcedureHelper.executeUpdate(sql, tx);
		for (String sql : paramHelper.getIndexSchemas())
			StoredProcedureHelper.executeUpdate(sql, tx);
	}
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
