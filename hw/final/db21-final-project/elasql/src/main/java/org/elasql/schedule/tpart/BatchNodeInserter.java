<<<<<<< HEAD
package org.elasql.schedule.tpart;

import java.util.List;

import org.elasql.procedure.tpart.TPartStoredProcedureTask;
import org.elasql.schedule.tpart.graph.TGraph;

public interface BatchNodeInserter {

    /**
	 * Insert a batch of transaction requests into the given T-Graph.
	 * 
	 * @param graph
	 * @param node
	 */
	void insertBatch(TGraph graph, List<TPartStoredProcedureTask> tasks);
}
=======
package org.elasql.schedule.tpart;

import java.util.List;

import org.elasql.procedure.tpart.TPartStoredProcedureTask;
import org.elasql.schedule.tpart.graph.TGraph;

public interface BatchNodeInserter {

	/**
	 * Insert a batch of transaction requests into the given T-Graph.
	 * 
	 * @param graph
	 * @param node
	 */
	void insertBatch(TGraph graph, List<TPartStoredProcedureTask> tasks);
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
