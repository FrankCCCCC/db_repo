<<<<<<< HEAD
package org.elasql.migration.planner;

import java.util.Set;

import org.elasql.migration.MigrationPlan;
import org.elasql.sql.PrimaryKey;

public interface MigrationPlanner {
	
	void monitorTransaction(Set<PrimaryKey> reads, Set<PrimaryKey> writes);
	
	MigrationPlan generateMigrationPlan();
	
	void reset();
	
}
=======
package org.elasql.migration.planner;

import java.util.Set;

import org.elasql.migration.MigrationPlan;
import org.elasql.sql.PrimaryKey;

public interface MigrationPlanner {
	
	void monitorTransaction(Set<PrimaryKey> reads, Set<PrimaryKey> writes);
	
	MigrationPlan generateMigrationPlan();
	
	void reset();
	
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
