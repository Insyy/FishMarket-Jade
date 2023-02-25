package fishmarket;

import jade.lang.acl.*;

public class PerformativeCreator {

	public static final String BROKER_NAME = "broker";

	private enum Performatifs {
		V_TO_ANNOUNCE(ACLMessage.CFP),
		V_TO_ATTRIBUTE(ACLMessage.ACCEPT_PROPOSAL),
		V_TO_GIVE(ACLMessage.AGREE),
		V_REP_BID(ACLMessage.INFORM),

		P_TO_BID(ACLMessage.PROPOSE),
		P_TO_PAY(ACLMessage.CONFIRM);

		private int num = 0;

		private Performatifs(int num) {
			this.num = num;
		}

		public int getValue() {
			return num;
		}
	}

	/**
	 * VENDEUR
	 */
	public static ACLMessage createToAnnounceMsg(int price) {
		ACLMessage msg = new ACLMessage(Performatifs.V_TO_ANNOUNCE.getValue());
		msg.setContent(String.valueOf(price));
		return msg;
	}

	public static ACLMessage createToAttributeMsg() {
		ACLMessage msg = new ACLMessage(Performatifs.V_TO_ATTRIBUTE.getValue());
		return msg;
	}

	public static ACLMessage createToGiveMsg(String description) {
		ACLMessage msg = new ACLMessage(Performatifs.V_TO_GIVE.getValue());
		msg.setContent(description);
		return msg;
	}

	public static ACLMessage createRepBidMsg(boolean isOk) {
		ACLMessage msg = new ACLMessage(Performatifs.V_REP_BID.getValue());
		if (isOk)
			msg.setContent("OK");
		else
			msg.setContent("NOK");
		return msg;
	}

	/**
	 * PRENEUR
	 */

	public static ACLMessage createToBidMsg() {
		ACLMessage msg = new ACLMessage(Performatifs.P_TO_BID.getValue());
		return msg;
	}

	public static ACLMessage createToPayMsg() {
		ACLMessage msg = new ACLMessage(Performatifs.P_TO_PAY.getValue());
		return msg;
	}

	/**
	 * BROKER
	 */

}
