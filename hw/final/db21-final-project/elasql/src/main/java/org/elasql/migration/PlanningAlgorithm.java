<<<<<<< HEAD
package org.elasql.migration;

public enum PlanningAlgorithm {
	CLAY;

	static PlanningAlgorithm fromInteger(int index) {
		switch (index) {
		case 0:
			return CLAY;
		default:
			throw new RuntimeException("Unsupport service type");
		}
	}
}
=======
package org.elasql.migration;

public enum PlanningAlgorithm {
	CLAY;

	static PlanningAlgorithm fromInteger(int index) {
		switch (index) {
		case 0:
			return CLAY;
		default:
			throw new RuntimeException("Unsupport service type");
		}
	}
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
