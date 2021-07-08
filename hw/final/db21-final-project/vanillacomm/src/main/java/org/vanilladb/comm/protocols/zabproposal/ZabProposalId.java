<<<<<<< HEAD
package org.vanilladb.comm.protocols.zabproposal;

import java.io.Serializable;

public class ZabProposalId implements Serializable {
	
	private static final long serialVersionUID = 20200501002L;
	
	private int epochId;
	private long serialNumber;
	
	public ZabProposalId(int epochId, long serialNumber) {
		this.epochId = epochId;
		this.serialNumber = serialNumber;
	}
	
	public int getEpochId() {
		return epochId;
	}
	
	public long getSerialNumber() {
		return serialNumber;
	}
}
=======
package org.vanilladb.comm.protocols.zabproposal;

import java.io.Serializable;

public class ZabProposalId implements Serializable {
	
	private static final long serialVersionUID = 20200501002L;
	
	private int epochId;
	private long serialNumber;
	
	public ZabProposalId(int epochId, long serialNumber) {
		this.epochId = epochId;
		this.serialNumber = serialNumber;
	}
	
	public int getEpochId() {
		return epochId;
	}
	
	public long getSerialNumber() {
		return serialNumber;
	}
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
