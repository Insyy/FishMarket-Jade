package fishmarket.performatifs;

import java.io.IOException;
import java.util.Date;

import fishmarket.auction.AuctionItem;
import jade.core.AID;
import jade.domain.FIPANames;
import jade.lang.acl.*;

public class PerformativeCreator {


	private final String TAG = "PerformativeCreator | ";

	private AID brokerAID;

	
	public PerformativeCreator(AID brokerAID) {
		this.brokerAID = brokerAID;
	}

	/**
	 * VENDEUR
	 * @throws IOException
	 */
	public ACLMessage createToAnnounceMsg(AuctionItem auctionItem) throws IOException {
		int performative = Performatifs.V_TO_ANNOUNCE.getJadeEquivalent();
		System.out.println(TAG + "Performative of announce message: " + performative);
		ACLMessage msg = new ACLMessage(performative);
		msg.addReceiver(brokerAID);
		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		msg.setContentObject(auctionItem);
		msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
		return msg;
	}

	public ACLMessage createToAttributeMsg() {
		ACLMessage msg = new ACLMessage(Performatifs.V_TO_ATTRIBUTE.getJadeEquivalent());
		return msg;
	}

	public ACLMessage createToGiveMsg(String description) {
		ACLMessage msg = new ACLMessage(Performatifs.V_TO_GIVE.getJadeEquivalent());
		msg.setContent(description);
		return msg;
	}

	public ACLMessage createRepBidMsg(boolean isOk) {
		ACLMessage msg = new ACLMessage(Performatifs.V_REP_BID.getJadeEquivalent());
		if (isOk)
			msg.setContent("OK");
		else
			msg.setContent("NOK");
		return msg;
	}

	/**
	 * PRENEUR
	 */

	public ACLMessage createToBidMsg() {
		ACLMessage msg = new ACLMessage(Performatifs.P_TO_BID.getJadeEquivalent());
		return msg;
	}

	public ACLMessage createToPayMsg() {
		ACLMessage msg = new ACLMessage(Performatifs.P_TO_PAY.getJadeEquivalent());
		return msg;
	}

	/**
	 * BROKER
	 */

}
