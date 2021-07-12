package org.elasql.schedule.tpart.hermes;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.lang.Math;
import com.rits.cloning.Cloner;

import org.elasql.procedure.tpart.TPartStoredProcedureTask;
import org.elasql.schedule.tpart.graph.TGraph;
import org.elasql.sql.PrimaryKey;

class Cost{
    private static final int AgtB = -1, AwtB = 1, AeqB = 0;
    public double remoteEdge = 0;
    public double pending = 0;
    public double dirtySetIncrement = 0;
    public double conflictInFuture = 0;

    // Return +1: taskA better than taskB (The cost of taskA is lower than taskB)
    // Return 0: taskA = taskB (The cost of taskA is equal to taskB)
    // Return -1: taskA worse than taskB (The cost of taskA is higher than taskB)
    public static int compare(Cost cA, Cost cB){
        // Compare pending time
        if(cA.pending < cB.pending){
            return AgtB;
        }else if(cA.pending > cB.pending){
            return AwtB;
        }else{
            // Compare the increment of dirty size
            if(cA.dirtySetIncrement < cB.dirtySetIncrement){
                return AgtB;
            }else if(cA.dirtySetIncrement > cB.dirtySetIncrement){
                return AwtB;
            }else{
                // Compare the count of the conflict in the future
                if(cA.conflictInFuture < cB.conflictInFuture){
                    return AgtB;
                }else if(cA.conflictInFuture > cB.conflictInFuture){
                    return AwtB;
                }else{
                    return AeqB;
                }       
            }   
        }
    }
}

public class Greedy {
    // Keep the modified records, and corresponding modified
    private HashMap<PrimaryKey, Integer> dirtyMap = new HashMap<PrimaryKey, Integer>();
    // Keep the records that will be modified in the future
    private HashMap<PrimaryKey, Integer> futureMap = new HashMap<PrimaryKey, Integer>();
    // Ordered 
    private List<TPartStoredProcedureTask> sortedTasks = new LinkedList<TPartStoredProcedureTask>();
    private List<TPartStoredProcedureTask> unsortedTasks = new ArrayList<TPartStoredProcedureTask>();

    // Hyperparameters
    // private double alpha = 1;
    // private double beta = 1;
    // private double m = 1;
    // private double n = 1;

    public Greedy(){

    }

    // Construct futureMap and put read-only transaction to the tail
    private void buildFutureMap_sortR(List<TPartStoredProcedureTask> tasks){
        for (TPartStoredProcedureTask task : tasks) {
            Set<PrimaryKey> readSet = task.getReadSet();
            // Build read set of transactions
            for(PrimaryKey key: readSet) { 
                if(futureMap.containsKey(key)){
                    // If key exist
                    int oldCount = futureMap.get(key);
                    futureMap.put(key, oldCount + 1);
                }else{
                    // If absent
                    futureMap.put(key, 1);
                }
            }

            if (task.isReadOnly()) {
                // Move Read-only Transaction to the head of
                sortedTasks.add(task);
            }else{
                // Add unsorted tasks into unsorted tasks
                unsortedTasks.add(task);
            }
        }
    }

    // Formula for computing pending cost
    private double pendingCost(int serialDistance){
        // return Math.sqrt(1 - (1 / alpha) * Math.pow(serialDistance, 2));
        if(serialDistance == 0){
            return 0;
        }else{
            return 1 / serialDistance;
        }
    }
    
    // Get the approximated waiting time cost
    // private double pending(int id, TPartStoredProcedureTask task){
    //     Set<PrimaryKey> writeSet = task.getWriteSet();
    //     double maxWaitingTime = 0; 
        
    //     for(PrimaryKey w: writeSet) {
    //         if(dirtyMap.containsKey(w)){
    //             // Dirty Write
    //             double waitingTime = pendingCost(id - dirtyMap.get(w));
    //             if(maxWaitingTime < waitingTime){
    //                 maxWaitingTime = waitingTime;
    //             }
    //         }
    //     }
    //     return maxWaitingTime;
    // }

    // Compute the increment of the dirty set size with hyperparameter beta
    // private double dirtySetIncrement(TPartStoredProcedureTask task){
    //     double sizeIncrement = 0;
    //     Set<PrimaryKey> writeSet = task.getWriteSet();
        
    //     for(PrimaryKey w: writeSet) {
    //         if(!dirtyMap.containsKey(w)){
    //             // Clean Write
    //             sizeIncrement++;
    //         }
    //     }

    //     // return beta * sizeIncrement;
    //     return sizeIncrement;
    // }

    // Compute the sum of the WAW conflict count in the future
    // private double conflictInFuture(int id, TPartStoredProcedureTask task){
    //     double conflictCost = 0;
    //     Set<PrimaryKey> writeSet = task.getWriteSet();
        
    //     for(PrimaryKey w: writeSet) {
    //         int conflictCount = futureMap.getOrDefault(w, 0);
    //         conflictCost += conflictCount;
    //     }

    //     return conflictCost;
    // }
    
    private Cost computeCost(int id, TPartStoredProcedureTask task){
        Cost c = new Cost();
        Set<PrimaryKey> readSet = task.getReadSet();
        Set<PrimaryKey> writeSet = task.getWriteSet();

        for(PrimaryKey key : readSet) {
            // Dirty Read
            // Compute the approximated waiting time cost
            if(dirtyMap.containsKey(key)){
                double waitingTime = pendingCost(id - dirtyMap.get(key));
                if(c.pending < waitingTime){
                    c.pending = waitingTime;
                }
            }
        }

        for(PrimaryKey key : writeSet) {
            // Compute the increment of the dirty set size with hyperparameter beta
            if(!dirtyMap.containsKey(key)){
                // Clean Write
                c.dirtySetIncrement++;
            }

            // Compute the sum of the RAW conflict(Dirty Read) count in the future
            c.conflictInFuture += futureMap.getOrDefault(key, 0);
        }

        return c;
    }

    private void updateMap(int insertedTaskId, TPartStoredProcedureTask insertedTask){
        for(PrimaryKey w: insertedTask.getWriteSet()) {
            // update dirtyMap
            dirtyMap.put(w, insertedTaskId);
        }
        for(PrimaryKey r: insertedTask.getReadSet()) {
            // update futureMap
            int newCount = futureMap.getOrDefault(r, 0) - 1;
            
            if(newCount == 0){
                futureMap.remove(r);
            }else if(newCount > 0){
                futureMap.put(r, newCount);
            }
        }
    }

    public List<TPartStoredProcedureTask> sort(List<TPartStoredProcedureTask> tasks){
        //long start = System.currentTimeMillis();

        buildFutureMap_sortR(tasks);
        int now_sort_id = sortedTasks.size() + 1;
        int count = 0;
        
        // Outer loop of the greedy algorithm
        while(unsortedTasks.size() != 0){
            TPartStoredProcedureTask inserting_task = null;
            Cost inserting_cost = new Cost();
            
            // Select the best task(lowest cost) for unsortedTasks
            for (TPartStoredProcedureTask task : unsortedTasks) {
                if(inserting_task == null){ // the first task
                    inserting_task = task;
                    inserting_cost = computeCost(now_sort_id, task);
                }else{
                    Cost now_cost = computeCost(now_sort_id, task);
                    if(Cost.compare(inserting_cost, now_cost) == -1){ // find the lower cost, replace the current task
                        inserting_task = task;
                        inserting_cost = now_cost;
                    }
                }
            }

            // insert to sorted list and remove from unsorted list
            sortedTasks.add(count, inserting_task);
            // sortedTasks.add(inserting_task);
            unsortedTasks.remove(inserting_task);
            updateMap(now_sort_id, inserting_task);
            
            now_sort_id += 1;
            count += 1;
        }

        //long end = System.currentTimeMillis();      
        //System.out.println("Greedy Elapsed Time in milli seconds: " + (end - start));
        
        // Collections.reverse(sortedTasks);
        return sortedTasks;
    }

    public List<TPartStoredProcedureTask> reorderWithHermes(HermesNodeInserter inserter , TGraph graph, List<TPartStoredProcedureTask> tasks){
        //long start = System.currentTimeMillis();
        List<Integer> bestPartIds = new ArrayList<Integer>();
        Cloner cloner = new Cloner();
        TGraph tempGraph = cloner.deepClone(graph);

        // Step 0: Reset statistics
		inserter.resetStatistics();

        // Step 1: Greedy Hermes Routing
        buildFutureMap_sortR(tasks);
        int now_sort_id = sortedTasks.size() + 1;

        // Insert the read-only tasks.
        for(TPartStoredProcedureTask task : sortedTasks){
            // List [0] = Best partition ID, List [1] = number of Remote-read edges
            List<Integer> bestPartIdReamoteEdge = inserter.getBestPartition(graph, task);
            inserter.insertToGraph(tempGraph, task, bestPartIdReamoteEdge.get(0));
            bestPartIds.add(bestPartIdReamoteEdge.get(0));
        }
        
        // Outer loop of the greedy algorithm
        while(unsortedTasks.size() != 0){
            TPartStoredProcedureTask inserting_task = null;
            // List [0] = Best partition ID, List [1] = number of Remote-read edges
            List<Integer> inserting_cost = new ArrayList<Integer>();
            
            // Select the best task(lowest cost) for unsortedTasks
            for (TPartStoredProcedureTask task : unsortedTasks) {
                if(inserting_task == null){ // the first task
                    inserting_task = task;
                    inserting_cost = inserter.getBestPartition(graph, task);
                }else{
                    // List [0] = Best partition ID, List [1] = number of Remote-read edges
                    List<Integer> now_cost = inserter.getBestPartition(graph, task);
                    if(now_cost.get(1) < inserting_cost.get(1)){ // find the lower cost, replace the current task
                        inserting_task = task;
                        inserting_cost = now_cost;
                    }
                }
            }

            // insert to sorted list and remove from unsorted list
            sortedTasks.add(inserting_task);
            unsortedTasks.remove(inserting_task);

            inserter.insertToGraph(tempGraph, inserting_task, inserting_cost.get(0));
            bestPartIds.add(inserting_cost.get(0));

            updateMap(now_sort_id, inserting_task);
            
            now_sort_id += 1;
        }

        //long end = System.currentTimeMillis();      
        //System.out.println("Greedy Elapsed Time in milli seconds: " + (end - start));
        
        Collections.reverse(sortedTasks);
        Collections.reverse(bestPartIds);

        // Insert nodes into graph
        for(int i=0; i < sortedTasks.size(); i++){
            inserter.insertToGraph(graph, sortedTasks.get(i), bestPartIds.get(i));
        }

        // Step 2&3: Balance the workload
        inserter.balance(graph, sortedTasks);

        return sortedTasks;
    }

    public List<TPartStoredProcedureTask> sortReadOnly(List<TPartStoredProcedureTask> tasks){
        List<TPartStoredProcedureTask> nonReadOnlyTasks = new ArrayList<TPartStoredProcedureTask>();
        
        for (TPartStoredProcedureTask task : tasks) {
            if(task.isReadOnly()){
                // Pick read-only transactions and put them into sorted list
                sortedTasks.add(task);
            }else{
                // Pick non-read-only transaction and put them into another list
                nonReadOnlyTasks.add(task);
            }
        }
        // Merge sortedTasks and nonReadOnlyTasks
        sortedTasks.addAll(nonReadOnlyTasks);
        // Collections.reverse(sortedTasks);

        return sortedTasks;
    }
}
