<<<<<<< HEAD
package org.vanilladb.comm.protocols.tcpfd;

import net.sf.appia.core.AppiaEventException;
import net.sf.appia.core.Channel;
import net.sf.appia.core.Direction;
import net.sf.appia.core.Event;
import net.sf.appia.core.Session;

public class FailureDetected extends Event {
	
	private int failedProcessId;
	
	public FailureDetected(Channel channel, Session src,
			int failedProcessId) throws AppiaEventException {
		super(channel, Direction.UP, src);
		this.failedProcessId = failedProcessId;
	}
	
	public int getFailedProcessId() {
		return failedProcessId;
	}
}
=======
package org.vanilladb.comm.protocols.tcpfd;

import net.sf.appia.core.AppiaEventException;
import net.sf.appia.core.Channel;
import net.sf.appia.core.Direction;
import net.sf.appia.core.Event;
import net.sf.appia.core.Session;

public class FailureDetected extends Event {
	
	private int failedProcessId;
	
	public FailureDetected(Channel channel, Session src,
			int failedProcessId) throws AppiaEventException {
		super(channel, Direction.UP, src);
		this.failedProcessId = failedProcessId;
	}
	
	public int getFailedProcessId() {
		return failedProcessId;
	}
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
