<<<<<<< HEAD
package org.elasql.migration;

import org.elasql.procedure.calvin.CalvinStoredProcedure;
import org.elasql.procedure.calvin.CalvinStoredProcedureFactory;

public abstract class MigrationStoredProcFactory implements CalvinStoredProcedureFactory {
	
	public static final int SP_MIGRATION_START = -101;
	public static final int SP_MIGRATION_END = -102;
	
	private CalvinStoredProcedureFactory underlayerFactory;
	
	public MigrationStoredProcFactory(CalvinStoredProcedureFactory underlayerFactory) {
		this.underlayerFactory = underlayerFactory;
	}
	
	@Override
	public CalvinStoredProcedure<?> getStoredProcedure(int pid, long txNum) {
		CalvinStoredProcedure<?> sp;
		switch (pid) {
			case SP_MIGRATION_START:
				sp = new MigrationStartProcedure(txNum);
				break;
			case SP_MIGRATION_END:
				sp = new MigrationEndProcedure(txNum);
				break;
			default:
				sp = getMigrationStoredProcedure(pid, txNum);
				if (sp == null)
					sp = underlayerFactory.getStoredProcedure(pid, txNum);
		}
		return sp;
	}
	
	protected abstract CalvinStoredProcedure<?> getMigrationStoredProcedure(int pid, long txNum);
}
=======
package org.elasql.migration;

import org.elasql.procedure.calvin.CalvinStoredProcedure;
import org.elasql.procedure.calvin.CalvinStoredProcedureFactory;

public abstract class MigrationStoredProcFactory implements CalvinStoredProcedureFactory {
	
	public static final int SP_MIGRATION_START = -101;
	public static final int SP_MIGRATION_END = -102;
	
	private CalvinStoredProcedureFactory underlayerFactory;
	
	public MigrationStoredProcFactory(CalvinStoredProcedureFactory underlayerFactory) {
		this.underlayerFactory = underlayerFactory;
	}
	
	@Override
	public CalvinStoredProcedure<?> getStoredProcedure(int pid, long txNum) {
		CalvinStoredProcedure<?> sp;
		switch (pid) {
			case SP_MIGRATION_START:
				sp = new MigrationStartProcedure(txNum);
				break;
			case SP_MIGRATION_END:
				sp = new MigrationEndProcedure(txNum);
				break;
			default:
				sp = getMigrationStoredProcedure(pid, txNum);
				if (sp == null)
					sp = underlayerFactory.getStoredProcedure(pid, txNum);
		}
		return sp;
	}
	
	protected abstract CalvinStoredProcedure<?> getMigrationStoredProcedure(int pid, long txNum);
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
