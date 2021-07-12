<<<<<<< HEAD
package org.vanilladb.comm.protocols.events;

import org.vanilladb.comm.process.ProcessList;

import net.sf.appia.core.AppiaEventException;
import net.sf.appia.core.Channel;
import net.sf.appia.core.Direction;
import net.sf.appia.core.Event;
import net.sf.appia.core.Session;

public class ProcessListInit extends Event {
	
	private ProcessList processList;
	
	public ProcessListInit(Channel channel, Session src, ProcessList processList)
			throws AppiaEventException {
		super(channel, Direction.DOWN, src);
		this.processList = processList;
	}
	
	public ProcessList copyProcessList() {
		return new ProcessList(processList);
	}
}
=======
package org.vanilladb.comm.protocols.events;

import org.vanilladb.comm.process.ProcessList;

import net.sf.appia.core.AppiaEventException;
import net.sf.appia.core.Channel;
import net.sf.appia.core.Direction;
import net.sf.appia.core.Event;
import net.sf.appia.core.Session;

public class ProcessListInit extends Event {
	
	private ProcessList processList;
	
	public ProcessListInit(Channel channel, Session src, ProcessList processList)
			throws AppiaEventException {
		super(channel, Direction.DOWN, src);
		this.processList = processList;
	}
	
	public ProcessList copyProcessList() {
		return new ProcessList(processList);
	}
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
