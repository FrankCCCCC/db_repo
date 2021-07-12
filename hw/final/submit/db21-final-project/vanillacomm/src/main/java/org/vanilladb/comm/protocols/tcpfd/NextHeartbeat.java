<<<<<<< HEAD
package org.vanilladb.comm.protocols.tcpfd;

import net.sf.appia.core.AppiaEventException;
import net.sf.appia.core.AppiaException;
import net.sf.appia.core.Channel;
import net.sf.appia.core.Direction;
import net.sf.appia.core.EventQualifier;
import net.sf.appia.core.Session;
import net.sf.appia.core.events.channel.Timer;

public class NextHeartbeat extends Timer {
	
	public NextHeartbeat(long waitingTime, String timerID,
			Channel channel, Session source)
			throws AppiaEventException, AppiaException {
		super(waitingTime, timerID, channel,
				Direction.DOWN, source, EventQualifier.ON);
	}
}
=======
package org.vanilladb.comm.protocols.tcpfd;

import net.sf.appia.core.AppiaEventException;
import net.sf.appia.core.AppiaException;
import net.sf.appia.core.Channel;
import net.sf.appia.core.Direction;
import net.sf.appia.core.EventQualifier;
import net.sf.appia.core.Session;
import net.sf.appia.core.events.channel.Timer;

public class NextHeartbeat extends Timer {
	
	public NextHeartbeat(long waitingTime, String timerID,
			Channel channel, Session source)
			throws AppiaEventException, AppiaException {
		super(waitingTime, timerID, channel,
				Direction.DOWN, source, EventQualifier.ON);
	}
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
