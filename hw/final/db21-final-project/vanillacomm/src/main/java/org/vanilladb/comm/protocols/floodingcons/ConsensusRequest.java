<<<<<<< HEAD
package org.vanilladb.comm.protocols.floodingcons;

import net.sf.appia.core.AppiaEventException;
import net.sf.appia.core.Channel;
import net.sf.appia.core.Direction;
import net.sf.appia.core.Event;
import net.sf.appia.core.Session;

public class ConsensusRequest extends Event {
	
	private Value value;
	
	public ConsensusRequest(Channel channel, Session src, Value value)
			  throws AppiaEventException {
		super(channel, Direction.DOWN, src);
		this.value = value;
	}
	
	public Value getValue() {
		return value;
	}
	
}
=======
package org.vanilladb.comm.protocols.floodingcons;

import net.sf.appia.core.AppiaEventException;
import net.sf.appia.core.Channel;
import net.sf.appia.core.Direction;
import net.sf.appia.core.Event;
import net.sf.appia.core.Session;

public class ConsensusRequest extends Event {
	
	private Value value;
	
	public ConsensusRequest(Channel channel, Session src, Value value)
			  throws AppiaEventException {
		super(channel, Direction.DOWN, src);
		this.value = value;
	}
	
	public Value getValue() {
		return value;
	}
	
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
