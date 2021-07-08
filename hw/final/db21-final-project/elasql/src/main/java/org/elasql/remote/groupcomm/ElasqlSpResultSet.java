<<<<<<< HEAD
package org.elasql.remote.groupcomm;

import java.io.Serializable;

import org.elasql.server.Elasql;
import org.vanilladb.core.remote.storedprocedure.SpResultSet;

public class ElasqlSpResultSet implements Serializable {
	
	private static final long serialVersionUID = 20200122001L;
	
	private SpResultSet resultSet;
	private int creatorNodeId;

	public ElasqlSpResultSet(SpResultSet resultSet) {
		this.resultSet = resultSet;
		this.creatorNodeId = Elasql.serverId();
	}
	
	public int getCreatorNodeId() {
		return creatorNodeId;
	}
	
	public SpResultSet getResultSet() {
		return resultSet;
	}
}
=======
package org.elasql.remote.groupcomm;

import java.io.Serializable;

import org.elasql.server.Elasql;
import org.vanilladb.core.remote.storedprocedure.SpResultSet;

public class ElasqlSpResultSet implements Serializable {
	
	private static final long serialVersionUID = 20200122001L;
	
	private SpResultSet resultSet;
	private int creatorNodeId;

	public ElasqlSpResultSet(SpResultSet resultSet) {
		this.resultSet = resultSet;
		this.creatorNodeId = Elasql.serverId();
	}
	
	public int getCreatorNodeId() {
		return creatorNodeId;
	}
	
	public SpResultSet getResultSet() {
		return resultSet;
	}
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
