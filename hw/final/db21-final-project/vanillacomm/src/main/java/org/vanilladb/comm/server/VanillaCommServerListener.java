<<<<<<< HEAD
package org.vanilladb.comm.server;

import java.io.Serializable;

import org.vanilladb.comm.view.ProcessType;

public interface VanillaCommServerListener {
	
	void onServerReady();
	
	void onServerFailed(int failedServerId);
	
	void onReceiveP2pMessage(ProcessType senderType, int senderId, Serializable message);
	
	void onReceiveTotalOrderMessage(long serialNumber, Serializable message);
	
}
=======
package org.vanilladb.comm.server;

import java.io.Serializable;

import org.vanilladb.comm.view.ProcessType;

public interface VanillaCommServerListener {
	
	void onServerReady();
	
	void onServerFailed(int failedServerId);
	
	void onReceiveP2pMessage(ProcessType senderType, int senderId, Serializable message);
	
	void onReceiveTotalOrderMessage(long serialNumber, Serializable message);
	
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
