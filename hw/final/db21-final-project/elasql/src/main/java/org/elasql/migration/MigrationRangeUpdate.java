<<<<<<< HEAD
package org.elasql.migration;

import java.io.Serializable;

/**
 * Used by BG push to update the migration status.
 */
public interface MigrationRangeUpdate extends Serializable {
	
	int getSourcePartId();
	
	int getDestPartId();

}
=======
package org.elasql.migration;

import java.io.Serializable;

/**
 * Used by BG push to update the migration status.
 */
public interface MigrationRangeUpdate extends Serializable {
	
	int getSourcePartId();
	
	int getDestPartId();

}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
