<<<<<<< HEAD
package org.elasql.schedule.tpart.hermes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Comparator;
import com.rits.cloning.Cloner;

import org.elasql.procedure.tpart.TPartStoredProcedureTask;
import org.elasql.schedule.tpart.BatchNodeInserter;
import org.elasql.schedule.tpart.graph.Edge;
import org.elasql.schedule.tpart.graph.TGraph;
import org.elasql.schedule.tpart.graph.TxNode;
import org.elasql.server.Elasql;
import org.elasql.sql.PrimaryKey;
import org.elasql.storage.metadata.PartitionMetaMgr;
import org.elasql.util.ElasqlProperties;

import java.io.*;
import java.util.*;
// This class represents a directed graph
// using adjacency list representation
class Graph {
    // No. of vertices
    private int V;
 
    // Adjacency List as ArrayList of ArrayList's
    private ArrayList<ArrayList<Integer> > adj;
 
    // Constructor
    Graph(int v){
        V = v;
        adj = new ArrayList<ArrayList<Integer> >(v);
        for (int i = 0; i < v; ++i)
            adj.add(new ArrayList<Integer>());
    }
 
    // Function to add an edge into the graph
    void addEdge(int v, int w) { adj.get(v).add(w); }

	void bulidGraph(List<TPartStoredProcedureTask> tasks){
		for(int i=0; i < tasks.size(); i++) {
			for(int j=0; j < tasks.size(); j++){
				int judge = compare(tasks.get(i), tasks.get(j));

				if(judge > 0){
					addEdge(i, j);
				}else if(judge < 0){
					addEdge(j, i);
				}
			}
		}
	}
 
    // A recursive function used by topologicalSort
    void topologicalSortUtil(int v, boolean visited[], Stack<Integer> stack){
        // Mark the current node as visited.
        visited[v] = true;
        Integer i;
 
        // Recur for all the vertices adjacent
        // to thisvertex
        Iterator<Integer> it = adj.get(v).iterator();
        while (it.hasNext()) {
            i = it.next();
            if (!visited[i])
                topologicalSortUtil(i, visited, stack);
        }
 
        // Push current vertex to stack
        // which stores result
        stack.push(new Integer(v));
    }
 
    // The function to do Topological Sort.
    // It uses recursive topologicalSortUtil()
    public Stack<Integer> topologicalSort()
    {
        Stack<Integer> stack = new Stack<Integer>();
 
        // Mark all the vertices as not visited
        boolean visited[] = new boolean[V];
        for (int i = 0; i < V; i++)
            visited[i] = false;
 
        // Call the recursive helper
        // function to store
        // Topological Sort starting
        // from all vertices one by one
        for (int i = 0; i < V; i++)
            if (visited[i] == false)
                topologicalSortUtil(i, visited, stack);
 
        // Print contents of stack
        // while (stack.empty() == false)
        //     System.out.print(stack.pop() + " ");
		return stack;
    }

	public List<TPartStoredProcedureTask> sort(List<TPartStoredProcedureTask> tasks){
		bulidGraph(tasks);

		Stack<Integer> stack = topologicalSort();
		List<TPartStoredProcedureTask> sort_list = new ArrayList<TPartStoredProcedureTask>();
		while (stack.empty() == false)
			sort_list.add(tasks.get(stack.pop()));
		
		// Collections.reverse(sort_list);
		return sort_list;
	}
 
	public int compare(TPartStoredProcedureTask task1, TPartStoredProcedureTask task2) {
		// if(task1 == null){System.out.println("Task1 is NULL");}
		// if(task2 == null){System.out.println("Task2 is NULL");}

		Set<PrimaryKey> writeset1 = new HashSet<PrimaryKey>(task1.getWriteSet());
		Set<PrimaryKey> writeset2 = new HashSet<PrimaryKey>(task2.getWriteSet());

        if (task1.isReadOnly()&&task2.isReadOnly()) return 0;
    	// else if (task1.isReadOnly() || writeset1.size() == 0) return +1; // task1 before than task2
		// else if (task2.isReadOnly() || writeset2.size() == 0) return -1; // task2 first
		else if (task1.isReadOnly() || writeset1.size() == 0) return -1; // task1 before than task2
		else if (task2.isReadOnly() || writeset2.size() == 0) return +1; // task2 first
		
		Set<PrimaryKey> readset1 = new HashSet<PrimaryKey>(task1.getReadSet());
		Set<PrimaryKey> readset2 = new HashSet<PrimaryKey>(task2.getReadSet());

		Set<PrimaryKey> interset1 = new HashSet<PrimaryKey>(readset1);
		int things1 = 0;
		// Eliminate NullPointers
		if(readset1.size() != 0){
			try{
				interset1.retainAll(writeset2);
			}catch(NullPointerException e){
				System.out.println("Interset1 Caught Null Pointer Exception" + e);
			}
			
			things1 = interset1.size();
		}

		Set<PrimaryKey> interset2 = new HashSet<PrimaryKey>(readset2);
		int things2 = 0;
		// Eliminate NullPointers
		if(readset2.size() !=0){
			try{
				interset2.retainAll(writeset1);
			}catch(NullPointerException e){
				System.out.println("Interset2 Caught Null Pointer Exception" + e);
			}
			things2 = interset2.size();
		}
		
		if(things1 > 0 && things2 > 0) {
			return things1 - things2;
		}
		else if(things1 == 0 && things2 > 0) {
			// return -1;
			return +1;
		}
		else if(things2 == 0 && things1 > 0) {
			// return +1;
			return -1;
		}
		else{
			return 0;
		}
    }
}

// class tasksComparator implements Comparator<TPartStoredProcedureTask> {
    
// }

public class HermesNodeInserter implements BatchNodeInserter {
	
	private static final double IMBALANCED_TOLERANCE;

	static {
		IMBALANCED_TOLERANCE = ElasqlProperties.getLoader()
				.getPropertyAsDouble(HermesNodeInserter.class.getName() + ".IMBALANCED_TOLERANCE", 0.25);
	}
	
	private PartitionMetaMgr partMgr = Elasql.partitionMetaMgr();
	public double[] loadPerPart = new double[PartitionMetaMgr.NUM_PARTITIONS];
	private Set<Integer> overloadedParts = new HashSet<Integer>();
	private Set<Integer> saturatedParts = new HashSet<Integer>();
	private int overloadedThreshold;
	private static int counter = 0;
	public double[] loadPerPartCache = new double[PartitionMetaMgr.NUM_PARTITIONS];

	public double[] getLoadPerPart(){
		return loadPerPart;
	}

	@Override
	public void insertBatch(TGraph graph, List<TPartStoredProcedureTask> tasks) {
		counter += 1;
		
		// Step 0: Reset statistics
		resetStatistics();
		
		//reorder
		// Collections.sort(tasks, new tasksComparator());
		// Graph Dgraph = new Graph(tasks.size());
		// tasks = Dgraph.sort(tasks);
		
		// System.out.println("before: "+tasks.get(tasks.size()-1).getTxNum());
		// System.out.println("before: "+tasks.get(tasks.size()-2).getTxNum());
		// System.out.println("before: "+tasks.get(tasks.size()-3).getTxNum());
		// Collections.shuffle(tasks);
		
		Greedy g = new Greedy();
		// tasks = new ArrayList<TPartStoredProcedureTask>(g.sortReadOnly(tasks));
		tasks = new ArrayList<TPartStoredProcedureTask>(g.sort(tasks));
		// System.out.println("Greedy sorted batch size: " + tasks.size());

		// System.out.println("Partition Size: " + partMgr.getCurrentNumOfParts());

		int allWrite_num = 0;
		int others = 0;
		int readonly = 0;
		int readWrite = 0;
		
		for (TPartStoredProcedureTask task : tasks) {
			if(task.getReadSet().size() == 0 && task.getWriteSet().size() > 0){
				allWrite_num += 1;
			}else if(task.getReadSet().size() == 0 && task.getWriteSet().size() == 0){
				others += 1;
			}else if(task.isReadOnly()){
				readonly += 1;
			}else{
				readWrite += 1;
			}
		}

		// System.out.println("WriteOnly_num: "+ allWrite_num + "/" + tasks.size());
		// System.out.println("ReadOnly_num: "+ allWrite_num + "/" + tasks.size());
		// System.out.println("ReadWrite: "+ readWrite + "/" + tasks.size());
		// System.out.println("readonly: "+ readonly + "/" + tasks.size());

		// System.out.println("after: "+tasks.get(tasks.size()-2).getTxNum());
		// System.out.println("after: "+tasks.get(tasks.size()-3).getTxNum());


		// Step 1: Insert nodes to the graph
		//long start = System.currentTimeMillis();

		for (TPartStoredProcedureTask task : tasks) {

			insertAccordingRemoteEdges(graph, task);
		}
		
		// Step 2: Find overloaded machines
		overloadedThreshold = (int) Math.ceil(
				((double) tasks.size() / partMgr.getCurrentNumOfParts()) * (IMBALANCED_TOLERANCE + 1));
		if (overloadedThreshold < 1) {
			overloadedThreshold = 1;
		}
		List<TxNode> candidateTxNodes = findTxNodesOnOverloadedParts(graph, tasks.size());
		
//		System.out.println(String.format("Overloaded threshold is %d (batch size: %d)", overloadedThreshold, tasks.size()));
//		System.out.println(String.format("Overloaded machines: %s, loads: %s", overloadedParts.toString(), Arrays.toString(loadPerPart)));
		
		// Step 3: Move tx nodes from overloaded machines to underloaded machines
		int increaseTolerence = 1;
		while (!overloadedParts.isEmpty()) {
//			System.out.println(String.format("Overloaded machines: %s, loads: %s, increaseTolerence: %d", overloadedParts.toString(), Arrays.toString(loadPerPart), increaseTolerence));
			candidateTxNodes = rerouteTxNodesToUnderloadedParts(candidateTxNodes, increaseTolerence);
			increaseTolerence++;
			
			if (increaseTolerence > 100)
				throw new RuntimeException("Something wrong");
		}
		
		// System.out.println(String.format("Final loads: %s", Arrays.toString(loadPerPart)));
		// long end = System.currentTimeMillis();      
		// System.out.println("Hermes Elapsed Time in milli seconds: " + (end - start));
		
		// if(counter % 100 == 0){
		// 	for(int i = 0; i < loadPerPartCache.length; i++){
		// 		loadPerPartCache[i] += loadPerPart[i];
		// 	}
			
		// 	System.out.println("loading " + loadPerPartCache[0] + "," + loadPerPartCache[1] + " / " + counter);
		// }
	}

	public void balance(TGraph graph, List<TPartStoredProcedureTask> tasks){
		// Step 2: Find overloaded machines
		overloadedThreshold = (int) Math.ceil(
				((double) tasks.size() / partMgr.getCurrentNumOfParts()) * (IMBALANCED_TOLERANCE + 1));
		if (overloadedThreshold < 1) {
			overloadedThreshold = 1;
		}
		List<TxNode> candidateTxNodes = findTxNodesOnOverloadedParts(graph, tasks.size());
		
		// Step 3: Move tx nodes from overloaded machines to underloaded machines
		int increaseTolerence = 1;
		while (!overloadedParts.isEmpty()) {
			candidateTxNodes = rerouteTxNodesToUnderloadedParts(candidateTxNodes, increaseTolerence);
			increaseTolerence++;
			
			if (increaseTolerence > 100)
				throw new RuntimeException("Something wrong");
		}
	}

	public void resetStatistics() {
		Arrays.fill(loadPerPart, 0);
		overloadedParts.clear();
		saturatedParts.clear();
	}
	
	private void insertAccordingRemoteEdges(TGraph graph, TPartStoredProcedureTask task) {
		int bestPartId = 0;
		int minRemoteEdgeCount = task.getReadSet().size();
		
		for (int partId = 0; partId < partMgr.getCurrentNumOfParts(); partId++) {
			
			// Count the number of remote edge
			int remoteEdgeCount = countRemoteReadEdge(graph, task, partId);
			
			// Find the node in which the tx has fewest remote edges.
			if (remoteEdgeCount < minRemoteEdgeCount) {
				minRemoteEdgeCount = remoteEdgeCount;
				bestPartId = partId;
			}
		}
		
		graph.insertTxNode(task, bestPartId);
		
		loadPerPart[bestPartId]++;
	}
	
	private int countRemoteReadEdge(TGraph graph, TPartStoredProcedureTask task, int partId) {
		int remoteEdgeCount = 0;
		
		for (PrimaryKey key : task.getReadSet()) {
			// Skip replicated records
			if (partMgr.isFullyReplicated(key))
				continue;
			
			if (graph.getResourcePosition(key).getPartId() != partId) {
				remoteEdgeCount++;
			}
		}
		
		return remoteEdgeCount;
	}

	public List<Integer> getBestPartition(TGraph graph, TPartStoredProcedureTask task){
		// Find best partition of the task
		int bestPartId = 0;
		int minRemoteEdgeCount = task.getReadSet().size();
		
		for (int partId = 0; partId < partMgr.getCurrentNumOfParts(); partId++) {
			
			// Count the number of remote edge
			int remoteEdgeCount = countRemoteReadEdge(graph, task, partId);
			
			// Find the node in which the tx has fewest remote edges.
			if (remoteEdgeCount < minRemoteEdgeCount) {
				minRemoteEdgeCount = remoteEdgeCount;
				bestPartId = partId;
			}
		}

		// Return a list with BestPartId and MinRemoteEdgeCount
		List<Integer> res = new ArrayList<Integer>();
		res.add(bestPartId);
		res.add(minRemoteEdgeCount);
		return res;
	}

	public void insertToGraph(TGraph graph, TPartStoredProcedureTask task, int bestPartId){
		graph.insertTxNode(task, bestPartId);
		
		loadPerPart[bestPartId]++;
	}
	
	private List<TxNode> findTxNodesOnOverloadedParts(TGraph graph, int batchSize) {
		
		// Find the overloaded parts
		for (int partId = 0; partId < loadPerPart.length; partId++) {
			if (loadPerPart[partId] > overloadedThreshold)
				overloadedParts.add(partId);
			else if (loadPerPart[partId] == overloadedThreshold)
				saturatedParts.add(partId);
		}
		
		// Find out the tx nodes on these parts
		List<TxNode> nodesOnOverloadedParts = new ArrayList<TxNode>();
		for (TxNode node : graph.getTxNodes()) { // this should be in the order of tx number
			int homePartId = node.getPartId();
			if (overloadedParts.contains(homePartId)) {
				nodesOnOverloadedParts.add(node);
			}
		}
		
		// Reverse the list, which makes the tx node ordered by tx number from large to small
		Collections.reverse(nodesOnOverloadedParts);
		
		return nodesOnOverloadedParts;
	}
	
	private List<TxNode> rerouteTxNodesToUnderloadedParts(List<TxNode> candidateTxNodes, int increaseTolerence) {
		List<TxNode> nextCandidates = new ArrayList<TxNode>();
		
		for (TxNode node : candidateTxNodes) {
			// Count remote edges (including write edges)
			int currentPartId = node.getPartId();
			
			// If the home partition is no longer a overloaded part, skip it
			if (!overloadedParts.contains(currentPartId))
				continue;
			
			int currentRemoteEdges = countRemoteReadWriteEdges(node, currentPartId);
			int bestDelta = increaseTolerence + 1;
			int bestPartId = currentPartId;
			
			// Find a better partition
			for (int partId = 0; partId < partMgr.getCurrentNumOfParts(); partId++) {
				// Skip home partition
				if (partId == currentPartId)
					continue;
				
				// Skip overloaded partitions
				if (overloadedParts.contains(partId))
					continue;
				
				// Skip saturated partitions
				if (saturatedParts.contains(partId))
					continue;
				
				// Count remote edges
				int remoteEdgeCount = countRemoteReadWriteEdges(node, partId);
				
				// Calculate the difference
				int delta = remoteEdgeCount - currentRemoteEdges;
				if (delta <= increaseTolerence) {
					// Prefer the machine with lower loadn
					if ((delta < bestDelta) ||
							(delta == bestDelta && loadPerPart[partId] < loadPerPart[bestPartId])) {
						bestDelta = delta;
						bestPartId = partId;
					}
				}
			}
			
			// If there is no match, try next tx node
			if (bestPartId == currentPartId) {
				nextCandidates.add(node);
				continue;
			}
//			System.out.println(String.format("Find a better partition %d for tx.%d", bestPartId, node.getTxNum()));
			node.setPartId(bestPartId);
			
			// Update loads
			loadPerPart[currentPartId]--;
			if (loadPerPart[currentPartId] == overloadedThreshold) {
				overloadedParts.remove(currentPartId);
				saturatedParts.add(currentPartId);
			}	
			loadPerPart[bestPartId]++;
			if (loadPerPart[bestPartId] == overloadedThreshold) {
				saturatedParts.add(bestPartId);
			}
			
			// Check if there are still overloaded machines
			if (overloadedParts.isEmpty())
				return null;
		}
		
		return nextCandidates;
	}
	
	private int countRemoteReadWriteEdges(TxNode node, int homePartId) {
		int count = 0;
		
		for (Edge readEdge : node.getReadEdges()) {
			// Skip replicated records
			if (partMgr.isFullyReplicated(readEdge.getResourceKey()))
				continue;
			
			if (readEdge.getTarget().getPartId() != homePartId)
				count++;
		}
		
		for (Edge writeEdge : node.getWriteEdges()) {
			if (writeEdge.getTarget().getPartId() != homePartId)
				count++;
		}
		
		// Note: We do not consider write back edges because Hermes will make it local
		
		return count;
	}
}
=======
package org.elasql.schedule.tpart.hermes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.elasql.procedure.tpart.TPartStoredProcedureTask;
import org.elasql.schedule.tpart.BatchNodeInserter;
import org.elasql.schedule.tpart.graph.Edge;
import org.elasql.schedule.tpart.graph.TGraph;
import org.elasql.schedule.tpart.graph.TxNode;
import org.elasql.server.Elasql;
import org.elasql.sql.PrimaryKey;
import org.elasql.storage.metadata.PartitionMetaMgr;
import org.elasql.util.ElasqlProperties;

public class HermesNodeInserter implements BatchNodeInserter {
	
	private static final double IMBALANCED_TOLERANCE;

	static {
		IMBALANCED_TOLERANCE = ElasqlProperties.getLoader()
				.getPropertyAsDouble(HermesNodeInserter.class.getName() + ".IMBALANCED_TOLERANCE", 0.25);
	}
	
	private PartitionMetaMgr partMgr = Elasql.partitionMetaMgr();
	private double[] loadPerPart = new double[PartitionMetaMgr.NUM_PARTITIONS];
	private Set<Integer> overloadedParts = new HashSet<Integer>();
	private Set<Integer> saturatedParts = new HashSet<Integer>();
	private int overloadedThreshold;

	@Override
	public void insertBatch(TGraph graph, List<TPartStoredProcedureTask> tasks) {
		// Step 0: Reset statistics
		resetStatistics();
		
		// Step 1: Insert nodes to the graph
		for (TPartStoredProcedureTask task : tasks) {
			insertAccordingRemoteEdges(graph, task);
		}
		
		// Step 2: Find overloaded machines
		overloadedThreshold = (int) Math.ceil(
				((double) tasks.size() / partMgr.getCurrentNumOfParts()) * (IMBALANCED_TOLERANCE + 1));
		if (overloadedThreshold < 1) {
			overloadedThreshold = 1;
		}
		List<TxNode> candidateTxNodes = findTxNodesOnOverloadedParts(graph, tasks.size());
		
//		System.out.println(String.format("Overloaded threshold is %d (batch size: %d)", overloadedThreshold, tasks.size()));
//		System.out.println(String.format("Overloaded machines: %s, loads: %s", overloadedParts.toString(), Arrays.toString(loadPerPart)));
		
		// Step 3: Move tx nodes from overloaded machines to underloaded machines
		int increaseTolerence = 1;
		while (!overloadedParts.isEmpty()) {
//			System.out.println(String.format("Overloaded machines: %s, loads: %s, increaseTolerence: %d", overloadedParts.toString(), Arrays.toString(loadPerPart), increaseTolerence));
			candidateTxNodes = rerouteTxNodesToUnderloadedParts(candidateTxNodes, increaseTolerence);
			increaseTolerence++;
			
			if (increaseTolerence > 100)
				throw new RuntimeException("Something wrong");
		}
		
//		System.out.println(String.format("Final loads: %s", Arrays.toString(loadPerPart)));
	}
	
	private void resetStatistics() {
		Arrays.fill(loadPerPart, 0);
		overloadedParts.clear();
		saturatedParts.clear();
	}
	
	private void insertAccordingRemoteEdges(TGraph graph, TPartStoredProcedureTask task) {
		int bestPartId = 0;
		int minRemoteEdgeCount = task.getReadSet().size();
		
		for (int partId = 0; partId < partMgr.getCurrentNumOfParts(); partId++) {
			
			// Count the number of remote edge
			int remoteEdgeCount = countRemoteReadEdge(graph, task, partId);
			
			// Find the node in which the tx has fewest remote edges.
			if (remoteEdgeCount < minRemoteEdgeCount) {
				minRemoteEdgeCount = remoteEdgeCount;
				bestPartId = partId;
			}
		}
		
		graph.insertTxNode(task, bestPartId);
		
		loadPerPart[bestPartId]++;
	}
	
	private int countRemoteReadEdge(TGraph graph, TPartStoredProcedureTask task, int partId) {
		int remoteEdgeCount = 0;
		
		for (PrimaryKey key : task.getReadSet()) {
			// Skip replicated records
			if (partMgr.isFullyReplicated(key))
				continue;
			
			if (graph.getResourcePosition(key).getPartId() != partId) {
				remoteEdgeCount++;
			}
		}
		
		return remoteEdgeCount;
	}
	
	private List<TxNode> findTxNodesOnOverloadedParts(TGraph graph, int batchSize) {
		
		// Find the overloaded parts
		for (int partId = 0; partId < loadPerPart.length; partId++) {
			if (loadPerPart[partId] > overloadedThreshold)
				overloadedParts.add(partId);
			else if (loadPerPart[partId] == overloadedThreshold)
				saturatedParts.add(partId);
		}
		
		// Find out the tx nodes on these parts
		List<TxNode> nodesOnOverloadedParts = new ArrayList<TxNode>();
		for (TxNode node : graph.getTxNodes()) { // this should be in the order of tx number
			int homePartId = node.getPartId();
			if (overloadedParts.contains(homePartId)) {
				nodesOnOverloadedParts.add(node);
			}
		}
		
		// Reverse the list, which makes the tx node ordered by tx number from large to small
		Collections.reverse(nodesOnOverloadedParts);
		
		return nodesOnOverloadedParts;
	}
	
	private List<TxNode> rerouteTxNodesToUnderloadedParts(List<TxNode> candidateTxNodes, int increaseTolerence) {
		List<TxNode> nextCandidates = new ArrayList<TxNode>();
		
		for (TxNode node : candidateTxNodes) {
			// Count remote edges (including write edges)
			int currentPartId = node.getPartId();
			
			// If the home partition is no longer a overloaded part, skip it
			if (!overloadedParts.contains(currentPartId))
				continue;
			
			int currentRemoteEdges = countRemoteReadWriteEdges(node, currentPartId);
			int bestDelta = increaseTolerence + 1;
			int bestPartId = currentPartId;
			
			// Find a better partition
			for (int partId = 0; partId < partMgr.getCurrentNumOfParts(); partId++) {
				// Skip home partition
				if (partId == currentPartId)
					continue;
				
				// Skip overloaded partitions
				if (overloadedParts.contains(partId))
					continue;
				
				// Skip saturated partitions
				if (saturatedParts.contains(partId))
					continue;
				
				// Count remote edges
				int remoteEdgeCount = countRemoteReadWriteEdges(node, partId);
				
				// Calculate the difference
				int delta = remoteEdgeCount - currentRemoteEdges;
				if (delta <= increaseTolerence) {
					// Prefer the machine with lower loadn
					if ((delta < bestDelta) ||
							(delta == bestDelta && loadPerPart[partId] < loadPerPart[bestPartId])) {
						bestDelta = delta;
						bestPartId = partId;
					}
				}
			}
			
			// If there is no match, try next tx node
			if (bestPartId == currentPartId) {
				nextCandidates.add(node);
				continue;
			}
//			System.out.println(String.format("Find a better partition %d for tx.%d", bestPartId, node.getTxNum()));
			node.setPartId(bestPartId);
			
			// Update loads
			loadPerPart[currentPartId]--;
			if (loadPerPart[currentPartId] == overloadedThreshold) {
				overloadedParts.remove(currentPartId);
				saturatedParts.add(currentPartId);
			}	
			loadPerPart[bestPartId]++;
			if (loadPerPart[bestPartId] == overloadedThreshold) {
				saturatedParts.add(bestPartId);
			}
			
			// Check if there are still overloaded machines
			if (overloadedParts.isEmpty())
				return null;
		}
		
		return nextCandidates;
	}
	
	private int countRemoteReadWriteEdges(TxNode node, int homePartId) {
		int count = 0;
		
		for (Edge readEdge : node.getReadEdges()) {
			// Skip replicated records
			if (partMgr.isFullyReplicated(readEdge.getResourceKey()))
				continue;
			
			if (readEdge.getTarget().getPartId() != homePartId)
				count++;
		}
		
		for (Edge writeEdge : node.getWriteEdges()) {
			if (writeEdge.getTarget().getPartId() != homePartId)
				count++;
		}
		
		// Note: We do not consider write back edges because Hermes will make it local
		
		return count;
	}
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
