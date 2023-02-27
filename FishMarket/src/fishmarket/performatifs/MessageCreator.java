package fishmarket.performatifs;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

import jade.core.AID;
import jade.lang.acl.*;

public class MessageCreator {


	private final String TAG = "PerformativeCreator |> ";

	private AID brokerAID;

	
	public MessageCreator(AID brokerAID) {
		this.brokerAID = brokerAID;
	}

	public ACLMessage createMessageToBroker(Performatifs perf, Optional<Serializable> content) throws IOException{
		
		ACLMessage msg = new ACLMessage(perf.getJadeEquivalent());
		msg.addReceiver(brokerAID);
		msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));

		if (content.isPresent())
			msg.setContentObject(content.get());
		
		return msg;
	}

	/**
	 * VENDEUR
	 * @throws IOException
	 */

	public ACLMessage createToAttributeMsg() {
		ACLMessage msg = new ACLMessage(Performatifs.V_TO_ATTRIBUTE.getJadeEquivalent());
		return msg;
	}

	public ACLMessage createToGiveMsg(String description) {
		ACLMessage msg = new ACLMessage(Performatifs.V_TO_GIVE.getJadeEquivalent());
		msg.setContent(description);
		return msg;
	}

	public ACLMessage createRepBidMsg(String isOk) {
		ACLMessage msg = new ACLMessage(Performatifs.V_REP_BID.getJadeEquivalent());
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
