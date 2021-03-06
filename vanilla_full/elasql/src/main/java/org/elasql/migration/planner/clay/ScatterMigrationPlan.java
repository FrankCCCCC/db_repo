package org.elasql.migration.planner.clay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasql.migration.MigrationComponentFactory;
import org.elasql.migration.MigrationPlan;
import org.elasql.migration.MigrationRange;
import org.elasql.server.Elasql;
import org.elasql.sql.RecordKey;
import org.elasql.storage.metadata.PartitionPlan;

public class ScatterMigrationPlan implements MigrationPlan {

	private static final long serialVersionUID = 20200628001L;
	
	private static class Route implements Serializable {
		
		private static final long serialVersionUID = 20200629001L;
		
		int sourcePartId;
		int destPartId;
		
		Route(int sourcePartId, int destPartId) {
			this.sourcePartId = sourcePartId;
			this.destPartId = destPartId;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this)
				return true;
			if (obj == null)
				return false;
			if (obj.getClass() != Route.class)
				return false;
			Route r = (Route) obj;
			return r.sourcePartId == sourcePartId && r.destPartId == destPartId;
		}

		@Override
		public int hashCode() {
			return 17 + 31 * sourcePartId + 31 * destPartId;
		}
	}

	private Map<RecordKey, Route> keysToMigrate = new HashMap<RecordKey, Route>();
	
	public void addKey(int source, int dest, RecordKey partKey) {
		Route route = keysToMigrate.get(partKey);
		
		// We treat the first source as the origin, ignore all latter sources
		if (route != null) {
			// The new source must match the previous destination
			if (route.destPartId != source)
				throw new RuntimeException(String.format(
						"Merging migration plan error: previous dest %d does not match"
						+ " the current source %d for %s", route.destPartId, source, partKey));
			
			// Detect loops
			if (route.sourcePartId == dest)
				keysToMigrate.remove(partKey);
			else {
				route.destPartId = dest;
			}
		} else {
			keysToMigrate.put(partKey, new Route(source, dest));
		}
	}
	
	public void merge(ScatterMigrationPlan plan) {
		for (Map.Entry<RecordKey, Route> entry : plan.keysToMigrate.entrySet()) {
			RecordKey key = entry.getKey();
			Route r = entry.getValue();
			addKey(r.sourcePartId, r.destPartId, key);
		}
	}
	
	public List<MigrationPlan> splits() {
//		// Create reverse mapping
//		Map<Route, Set<RecordKey>> routeToKeys = new HashMap<Route, Set<RecordKey>>();
//		for (Map.Entry<RecordKey, Route> entry : keysToMigrate.entrySet()) {
//			RecordKey key = entry.getKey();
//			Route r = entry.getValue();
//			Set<RecordKey> keys = routeToKeys.get(r);
//			if (keys == null) {
//				keys = new HashSet<RecordKey>();
//				routeToKeys.put(r, keys);
//			}
//			keys.add(key);
//		}
//		
//		// Use mapping to generate plans
//		List<MigrationPlan> plans = new ArrayList<MigrationPlan>();
//		for (Map.Entry<Route, Set<RecordKey>> entry : routeToKeys.entrySet()) {
//			Route r = entry.getKey();
//			Set<RecordKey> keys = entry.getValue();
//			
//			ScatterMigrationPlan plan = new ScatterMigrationPlan();
//			for (RecordKey key : keys) {
//				plan.addKey(r.sourcePartId, r.destPartId, key);
//			}
//			plans.add(plan);
//		}
//		return plans;

		List<MigrationPlan> plans = new ArrayList<MigrationPlan>();
		for (Map.Entry<RecordKey, Route> entry : keysToMigrate.entrySet()) {
			RecordKey key = entry.getKey();
			Route r = entry.getValue();
			plans.add(new PointMigrationPlan(r.sourcePartId, r.destPartId, key));
		}
		return plans;
	}
	
	public boolean isEmpty() {
		return keysToMigrate.isEmpty();
	}
	
	public int countKeys() {
		return keysToMigrate.size();
	}

	@Override
	public PartitionPlan getNewPart() {
		Map<RecordKey, Integer> partition = new HashMap<RecordKey, Integer>();
		for (Map.Entry<RecordKey, Route> entry : keysToMigrate.entrySet()) {
			RecordKey key = entry.getKey();
			Route r = entry.getValue();
			partition.put(key, r.destPartId);
		}
		
		PartitionPlan currentPlan = Elasql.partitionMetaMgr().getPartitionPlan();
		
		// Merge this plan with the current one to avoid duplication
		if (currentPlan.getClass().equals(ScatterPartitionPlan.class)) {
			ScatterPartitionPlan currentScatter = (ScatterPartitionPlan) currentPlan;
			for (Map.Entry<RecordKey, Integer> entry : currentScatter.getMapping().entrySet()) {
				partition.putIfAbsent(entry.getKey(), entry.getValue());
			}
			
			return new ScatterPartitionPlan(currentScatter.getBasePlan(), partition);
		}
		
		return new ScatterPartitionPlan(currentPlan, partition);
	}

	@Override
	public List<MigrationRange> getMigrationRanges(MigrationComponentFactory factory) {
//		// Create reverse mapping
//		Map<Route, Set<RecordKey>> routeToKeys = new HashMap<Route, Set<RecordKey>>();
//		for (Map.Entry<RecordKey, Route> entry : keysToMigrate.entrySet()) {
//			RecordKey key = entry.getKey();
//			Route r = entry.getValue();
//			Set<RecordKey> keys = routeToKeys.get(r);
//			if (keys == null) {
//				keys = new HashSet<RecordKey>();
//				routeToKeys.put(r, keys);
//			}
//			keys.add(key);
//		}
//		
//		// Use mapping to generate ranges
//		List<MigrationRange> ranges = new ArrayList<MigrationRange>();
//		for (Map.Entry<Route, Set<RecordKey>> entry : routeToKeys.entrySet()) {
//			Route r = entry.getKey();
//			Set<RecordKey> keys = entry.getValue();
//			ScatterMigrationRange range = new ScatterMigrationRange(
//					r.sourcePartId, r.destPartId, keys);
//			ranges.add(range);
//		}
//		return ranges;
		
		throw new UnsupportedOperationException();
	}
}
