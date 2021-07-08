<<<<<<< HEAD
package org.elasql.procedure.tpart;

import org.elasql.procedure.DdStoredProcedureFactory;

public interface TPartStoredProcedureFactory
		extends DdStoredProcedureFactory<TPartStoredProcedure<?>> {
	
	@Override
	TPartStoredProcedure<?> getStoredProcedure(int pid, long txNum);

}
=======
package org.elasql.procedure.tpart;

import org.elasql.procedure.DdStoredProcedureFactory;

public interface TPartStoredProcedureFactory
		extends DdStoredProcedureFactory<TPartStoredProcedure<?>> {
	
	@Override
	TPartStoredProcedure<?> getStoredProcedure(int pid, long txNum);

}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
