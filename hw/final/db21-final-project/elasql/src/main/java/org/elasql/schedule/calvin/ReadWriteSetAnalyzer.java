<<<<<<< HEAD
package org.elasql.schedule.calvin;

import org.elasql.sql.PrimaryKey;

public interface ReadWriteSetAnalyzer {
	
	ExecutionPlan generatePlan();

	void addReadKey(PrimaryKey readKey);

	void addUpdateKey(PrimaryKey updateKey);
	
	void addInsertKey(PrimaryKey insertKey);
	
	void addDeleteKey(PrimaryKey deleteKey);
	
}
=======
package org.elasql.schedule.calvin;

import org.elasql.sql.PrimaryKey;

public interface ReadWriteSetAnalyzer {
	
	ExecutionPlan generatePlan();

	void addReadKey(PrimaryKey readKey);

	void addUpdateKey(PrimaryKey updateKey);
	
	void addInsertKey(PrimaryKey insertKey);
	
	void addDeleteKey(PrimaryKey deleteKey);
	
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
