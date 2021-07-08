<<<<<<< HEAD
package org.elasql.migration;

import org.elasql.sql.PartitioningKey;

public class DummyMigrationComponentFactory extends MigrationComponentFactory {
	
	private String message;
	
	public DummyMigrationComponentFactory(String message) {
		this.message = message;
	}

	@Override
	public MigrationPlan newPredefinedMigrationPlan() {
		throw new RuntimeException(message);
	}

	@Override
	public MigrationRange toMigrationRange(int sourceId, int destId, PartitioningKey partitioningKey) {
		throw new RuntimeException(message);
	}

}
=======
package org.elasql.migration;

import org.elasql.sql.PartitioningKey;

public class DummyMigrationComponentFactory extends MigrationComponentFactory {
	
	private String message;
	
	public DummyMigrationComponentFactory(String message) {
		this.message = message;
	}

	@Override
	public MigrationPlan newPredefinedMigrationPlan() {
		throw new RuntimeException(message);
	}

	@Override
	public MigrationRange toMigrationRange(int sourceId, int destId, PartitioningKey partitioningKey) {
		throw new RuntimeException(message);
	}

}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
