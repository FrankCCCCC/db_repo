<<<<<<< HEAD
package org.elasql.migration;

import java.util.Set;

import org.elasql.sql.PrimaryKey;

public interface MigrationRange {
	
	boolean addKey(PrimaryKey key);
	
	boolean contains(PrimaryKey key);
	
	boolean isMigrated(PrimaryKey key);
	
	void setMigrated(PrimaryKey key);
	
	Set<PrimaryKey> generateNextMigrationChunk(boolean useBytesForSize, int maxChunkSize);
	
	int getSourcePartId();
	
	int getDestPartId();
	
	MigrationRangeUpdate generateStatusUpdate();
	
	boolean updateMigrationStatus(MigrationRangeUpdate update);
}
=======
package org.elasql.migration;

import java.util.Set;

import org.elasql.sql.PrimaryKey;

public interface MigrationRange {
	
	boolean addKey(PrimaryKey key);
	
	boolean contains(PrimaryKey key);
	
	boolean isMigrated(PrimaryKey key);
	
	void setMigrated(PrimaryKey key);
	
	Set<PrimaryKey> generateNextMigrationChunk(boolean useBytesForSize, int maxChunkSize);
	
	int getSourcePartId();
	
	int getDestPartId();
	
	MigrationRangeUpdate generateStatusUpdate();
	
	boolean updateMigrationStatus(MigrationRangeUpdate update);
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
