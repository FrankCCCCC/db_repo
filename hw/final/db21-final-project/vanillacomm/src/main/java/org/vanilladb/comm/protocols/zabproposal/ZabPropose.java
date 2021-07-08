<<<<<<< HEAD
package org.vanilladb.comm.protocols.zabproposal;

import org.vanilladb.comm.protocols.beb.Broadcast;

import net.sf.appia.core.AppiaEventException;
import net.sf.appia.core.Channel;
import net.sf.appia.core.Direction;
import net.sf.appia.core.Session;

public class ZabPropose extends Broadcast {
	
	// We must provide a public constructor for TcpCompleteSession
	// in order to reconstruct this on the other side
	public ZabPropose() {
		super();
	}
	
	public ZabPropose(Channel channel, Session source)
			throws AppiaEventException {
		super(channel, Direction.DOWN, source);
	}
}
=======
package org.vanilladb.comm.protocols.zabproposal;

import org.vanilladb.comm.protocols.beb.Broadcast;

import net.sf.appia.core.AppiaEventException;
import net.sf.appia.core.Channel;
import net.sf.appia.core.Direction;
import net.sf.appia.core.Session;

public class ZabPropose extends Broadcast {
	
	// We must provide a public constructor for TcpCompleteSession
	// in order to reconstruct this on the other side
	public ZabPropose() {
		super();
	}
	
	public ZabPropose(Channel channel, Session source)
			throws AppiaEventException {
		super(channel, Direction.DOWN, source);
	}
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
