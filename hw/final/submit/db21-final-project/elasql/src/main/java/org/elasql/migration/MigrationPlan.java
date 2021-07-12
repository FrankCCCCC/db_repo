<<<<<<< HEAD
package org.elasql.migration;

import java.io.Serializable;
import java.util.List;

import org.elasql.storage.metadata.PartitionPlan;

public interface MigrationPlan extends Serializable {
	
	PartitionPlan getNewPart();
	
	List<MigrationRange> getMigrationRanges(MigrationComponentFactory factory);
	
	List<MigrationPlan> splits();
}
=======
package org.elasql.migration;

import java.io.Serializable;
import java.util.List;

import org.elasql.storage.metadata.PartitionPlan;

public interface MigrationPlan extends Serializable {
	
	PartitionPlan getNewPart();
	
	List<MigrationRange> getMigrationRanges(MigrationComponentFactory factory);
	
	List<MigrationPlan> splits();
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
