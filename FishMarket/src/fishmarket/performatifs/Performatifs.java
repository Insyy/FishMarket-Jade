package fishmarket.performatifs;

import jade.lang.acl.ACLMessage;

public enum Performatifs {
	V_TO_ANNOUNCE(ACLMessage.CFP),
	V_TO_ATTRIBUTE(ACLMessage.ACCEPT_PROPOSAL),
	V_TO_GIVE(ACLMessage.AGREE),
	V_REP_BID(ACLMessage.INFORM),

	B_TO_BID(ACLMessage.PROPOSE),
	B_TO_PAY(ACLMessage.CONFIRM);

	private int jadeEquivalent;

	Performatifs(int num) {
		this.jadeEquivalent = num;
	}

	public int getJadeEquivalent() {
		return jadeEquivalent;
	}
}