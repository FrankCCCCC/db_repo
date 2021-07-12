<<<<<<< HEAD
package org.vanilladb.comm.client;

import java.io.Serializable;

import org.vanilladb.comm.view.ProcessType;

public interface VanillaCommClientListener {
	
	void onReceiveP2pMessage(ProcessType senderType, int senderId, Serializable message);
	
}
=======
package org.vanilladb.comm.client;

import java.io.Serializable;

import org.vanilladb.comm.view.ProcessType;

public interface VanillaCommClientListener {
	
	void onReceiveP2pMessage(ProcessType senderType, int senderId, Serializable message);
	
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
