<<<<<<< HEAD
package org.elasql.migration.squall;

import org.elasql.migration.MigrationStoredProcFactory;
import org.elasql.procedure.calvin.CalvinStoredProcedure;
import org.elasql.procedure.calvin.CalvinStoredProcedureFactory;

public class SquallStoredProcFactory extends MigrationStoredProcFactory {
	
	public static final int SP_BG_PUSH = -103;
	
	public SquallStoredProcFactory(CalvinStoredProcedureFactory underlayerFactory) {
		super(underlayerFactory);
	}
	
	@Override
	protected CalvinStoredProcedure<?> getMigrationStoredProcedure(int pid, long txNum) {
		switch (pid) {
			case SP_BG_PUSH:
				return new BgPushProcedure(txNum);
			default:
				return null;
		}
	}
}
=======
package org.elasql.migration.squall;

import org.elasql.migration.MigrationStoredProcFactory;
import org.elasql.procedure.calvin.CalvinStoredProcedure;
import org.elasql.procedure.calvin.CalvinStoredProcedureFactory;

public class SquallStoredProcFactory extends MigrationStoredProcFactory {
	
	public static final int SP_BG_PUSH = -103;
	
	public SquallStoredProcFactory(CalvinStoredProcedureFactory underlayerFactory) {
		super(underlayerFactory);
	}
	
	@Override
	protected CalvinStoredProcedure<?> getMigrationStoredProcedure(int pid, long txNum) {
		switch (pid) {
			case SP_BG_PUSH:
				return new BgPushProcedure(txNum);
			default:
				return null;
		}
	}
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
