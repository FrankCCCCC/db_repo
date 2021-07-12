<<<<<<< HEAD
package org.vanilladb.comm.protocols.zabelection;

import net.sf.appia.core.AppiaEventException;
import net.sf.appia.core.Channel;
import net.sf.appia.core.Direction;
import net.sf.appia.core.Event;
import net.sf.appia.core.Session;

public class LeaderChanged extends Event {
	
	private int newLeaderId;
	private int newEpochId;
	
	public LeaderChanged(Channel channel, Session src, int newLeaderId, int newEpochId)
			throws AppiaEventException {
		super(channel, Direction.UP, src);
		this.newLeaderId = newLeaderId;
		this.newEpochId = newEpochId;
	}
	
	public int getNewLeaderId() {
		return newLeaderId;
	}
	
	public int getNewEpochId() {
		return newEpochId;
	}
}
=======
package org.vanilladb.comm.protocols.zabelection;

import net.sf.appia.core.AppiaEventException;
import net.sf.appia.core.Channel;
import net.sf.appia.core.Direction;
import net.sf.appia.core.Event;
import net.sf.appia.core.Session;

public class LeaderChanged extends Event {
	
	private int newLeaderId;
	private int newEpochId;
	
	public LeaderChanged(Channel channel, Session src, int newLeaderId, int newEpochId)
			throws AppiaEventException {
		super(channel, Direction.UP, src);
		this.newLeaderId = newLeaderId;
		this.newEpochId = newEpochId;
	}
	
	public int getNewLeaderId() {
		return newLeaderId;
	}
	
	public int getNewEpochId() {
		return newEpochId;
	}
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
