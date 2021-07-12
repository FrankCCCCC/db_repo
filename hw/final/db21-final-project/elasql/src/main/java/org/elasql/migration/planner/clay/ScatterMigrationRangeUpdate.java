<<<<<<< HEAD
package org.elasql.migration.planner.clay;

import java.util.Deque;

import org.elasql.migration.MigrationRangeUpdate;
import org.elasql.sql.PrimaryKey;

public class ScatterMigrationRangeUpdate implements MigrationRangeUpdate {
	
	private static final long serialVersionUID = 20181101001L;

	private int sourcePartId, destPartId;
	private Deque<PrimaryKey> keysToPush;
	
	ScatterMigrationRangeUpdate(int sourcePartId, int destPartId,
			Deque<PrimaryKey> keysToPush) {
		this.keysToPush = keysToPush;
		this.sourcePartId = sourcePartId;
		this.destPartId = destPartId;
	}
	
	@Override
	public int getSourcePartId() {
		return sourcePartId;
	}

	@Override
	public int getDestPartId() {
		return destPartId;
	}
	
	Deque<PrimaryKey> getKeysToPush() {
		return keysToPush;
	}
}
=======
package org.elasql.migration.planner.clay;

import java.util.Deque;

import org.elasql.migration.MigrationRangeUpdate;
import org.elasql.sql.PrimaryKey;

public class ScatterMigrationRangeUpdate implements MigrationRangeUpdate {
	
	private static final long serialVersionUID = 20181101001L;

	private int sourcePartId, destPartId;
	private Deque<PrimaryKey> keysToPush;
	
	ScatterMigrationRangeUpdate(int sourcePartId, int destPartId,
			Deque<PrimaryKey> keysToPush) {
		this.keysToPush = keysToPush;
		this.sourcePartId = sourcePartId;
		this.destPartId = destPartId;
	}
	
	@Override
	public int getSourcePartId() {
		return sourcePartId;
	}

	@Override
	public int getDestPartId() {
		return destPartId;
	}
	
	Deque<PrimaryKey> getKeysToPush() {
		return keysToPush;
	}
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
