<<<<<<< HEAD
package org.vanilladb.bench;

public enum ControlTransactionType {

	START_PROFILING(1001), STOP_PROFILING(1002);
	
	private int pid;
	
	ControlTransactionType(int pid) {
		this.pid = pid;
	}
	
	public static ControlTransactionType fromProcedureId(int pid) {
		switch (pid) {
		case 1001:
			return START_PROFILING;
		case 1002:
			return STOP_PROFILING;
		}
		return null;
	}
	
	public int getProcedureId() {
		return pid;
	}
}
=======
package org.vanilladb.bench;

public enum ControlTransactionType {

	START_PROFILING(1001), STOP_PROFILING(1002);
	
	private int pid;
	
	ControlTransactionType(int pid) {
		this.pid = pid;
	}
	
	public static ControlTransactionType fromProcedureId(int pid) {
		switch (pid) {
		case 1001:
			return START_PROFILING;
		case 1002:
			return STOP_PROFILING;
		}
		return null;
	}
	
	public int getProcedureId() {
		return pid;
	}
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
