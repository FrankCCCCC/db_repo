package org.elasql.migration.planner.clay;

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

public class PointMigrationPlan implements MigrationPlan {

	private static final long serialVersionUID = 20200705001L;
	
	private int sourceId, destId;
	private RecordKey partKey;
	
	public PointMigrationPlan(int sourceId, int destId, RecordKey partKey) {
		this.sourceId = sourceId;
		this.destId = destId;
		this.partKey = partKey;
	}

	@Override
	public PartitionPlan getNewPart() {
		PartitionPlan currentPlan = Elasql.partitionMetaMgr().getPartitionPlan();
		Map<RecordKey, Integer> partitioning;
		
		// Merge this plan with the current one to avoid duplication
		if (currentPlan.getClass().equals(ScatterPartitionPlan.class)) {
			ScatterPartitionPlan currentScatter = (ScatterPartitionPlan) currentPlan;
			partitioning = currentScatter.getMapping();
			currentPlan = currentScatter.getBasePlan();
		} else {
			partitioning = new HashMap<RecordKey, Integer>();
		}

		partitioning.put(partKey, destId);
		return new ScatterPartitionPlan(currentPlan, partitioning);
	}

	@Override
	public List<MigrationRange> getMigrationRanges(MigrationComponentFactory factory) {
		List<MigrationRange> ranges = new ArrayList<MigrationRange>();
		ranges.add(factory.toMigrationRange(sourceId, destId, partKey));
		return ranges;
	}

	@Override
	public List<MigrationPlan> splits() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String toString() {
		return String.format("{Migrates partitioning key %s from part.%d to part.%d}",
				partKey, sourceId, destId);
	}
}
