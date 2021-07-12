<<<<<<< HEAD
package org.vanilladb.comm.protocols.floodingcons;

import net.sf.appia.core.AppiaEventException;
import net.sf.appia.core.Channel;
import net.sf.appia.core.Direction;
import net.sf.appia.core.Event;
import net.sf.appia.core.Session;

public class ConsensusResult extends Event {
	
	private Value decision;
	
	public ConsensusResult(Channel channel, Session src, Value decision)
			  throws AppiaEventException {
		super(channel, Direction.UP, src);
		this.decision = decision;
	}
	
	public Value getDecision() {
		return decision;
	}
	
}
=======
package org.vanilladb.comm.protocols.floodingcons;

import net.sf.appia.core.AppiaEventException;
import net.sf.appia.core.Channel;
import net.sf.appia.core.Direction;
import net.sf.appia.core.Event;
import net.sf.appia.core.Session;

public class ConsensusResult extends Event {
	
	private Value decision;
	
	public ConsensusResult(Channel channel, Session src, Value decision)
			  throws AppiaEventException {
		super(channel, Direction.UP, src);
		this.decision = decision;
	}
	
	public Value getDecision() {
		return decision;
	}
	
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
