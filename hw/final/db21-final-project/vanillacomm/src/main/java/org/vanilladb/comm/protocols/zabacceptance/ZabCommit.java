<<<<<<< HEAD
package org.vanilladb.comm.protocols.zabacceptance;

import org.vanilladb.comm.protocols.beb.Broadcast;

import net.sf.appia.core.AppiaEventException;
import net.sf.appia.core.Channel;
import net.sf.appia.core.Direction;
import net.sf.appia.core.Session;

public class ZabCommit extends Broadcast {
	
	// We must provide a public constructor for TcpCompleteSession
	// in order to reconstruct this on the other side
	public ZabCommit() {
		super();
	}
	
	public ZabCommit(Channel channel, Session source)
			throws AppiaEventException {
		super(channel, Direction.DOWN, source);
	}
}
=======
package org.vanilladb.comm.protocols.zabacceptance;

import org.vanilladb.comm.protocols.beb.Broadcast;

import net.sf.appia.core.AppiaEventException;
import net.sf.appia.core.Channel;
import net.sf.appia.core.Direction;
import net.sf.appia.core.Session;

public class ZabCommit extends Broadcast {
	
	// We must provide a public constructor for TcpCompleteSession
	// in order to reconstruct this on the other side
	public ZabCommit() {
		super();
	}
	
	public ZabCommit(Channel channel, Session source)
			throws AppiaEventException {
		super(channel, Direction.DOWN, source);
	}
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
