<<<<<<< HEAD
package org.elasql.bench.server.migration;

import java.util.Iterator;

import org.elasql.sql.PrimaryKey;

public interface TableKeyIterator extends Iterator<PrimaryKey> {
	
	String getTableName();
	
	boolean isInSubsequentKeys(PrimaryKey key);
	
	TableKeyIterator copy();
	
}
=======
package org.elasql.bench.server.migration;

import java.util.Iterator;

import org.elasql.sql.PrimaryKey;

public interface TableKeyIterator extends Iterator<PrimaryKey> {
	
	String getTableName();
	
	boolean isInSubsequentKeys(PrimaryKey key);
	
	TableKeyIterator copy();
	
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
