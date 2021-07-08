<<<<<<< HEAD
package org.elasql.migration;

import org.elasql.schedule.calvin.ReadWriteSetAnalyzer;
import org.elasql.sql.PrimaryKey;
import org.vanilladb.core.storage.tx.Transaction;

public interface MigrationMgr {
	
	void initializeMigration(Transaction tx, MigrationPlan plan, Object[] params);
	
	void finishMigration(Transaction tx, Object[] params);
	
	boolean isMigratingRecord(PrimaryKey key);
	
	boolean isMigrated(PrimaryKey key);
	
	void setMigrated(PrimaryKey key);
	
	int checkSourceNode(PrimaryKey key);
	
	int checkDestNode(PrimaryKey key);
	
	boolean isInMigration();
	
	ReadWriteSetAnalyzer newAnalyzer();
}
=======
package org.elasql.migration;

import org.elasql.schedule.calvin.ReadWriteSetAnalyzer;
import org.elasql.sql.PrimaryKey;
import org.vanilladb.core.storage.tx.Transaction;

public interface MigrationMgr {
	
	void initializeMigration(Transaction tx, MigrationPlan plan, Object[] params);
	
	void finishMigration(Transaction tx, Object[] params);
	
	boolean isMigratingRecord(PrimaryKey key);
	
	boolean isMigrated(PrimaryKey key);
	
	void setMigrated(PrimaryKey key);
	
	int checkSourceNode(PrimaryKey key);
	
	int checkDestNode(PrimaryKey key);
	
	boolean isInMigration();
	
	ReadWriteSetAnalyzer newAnalyzer();
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
