package fishmarket.agents.seller;

import jade.core.Agent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;

import fishmarket.auction.AuctionItem;
import fishmarket.performatifs.Performatifs;
import fishmarket.performatifs.MessageCreator;

/**
 * This example shows how to implement the initiator role in
 * a FIPA-request interaction protocol. In this case in particular
 * we use an <code>AchieveREInitiator</code> ("Achieve Rational effect")
 * to request a dummy action to a number of agents (whose local
 * names must be specified as arguments).
 * 
 * @author Giovanni Caire - TILAB
 */
public class Seller extends Agent {

	private static String TAG;
	private String brokerAgentName;
	private MessageCreator messageCreator;

	protected void setup() {
		TAG = getName() + " |> ";
		// Read name of broker as argument
		Object[] args = getArguments();

		if (!(args != null && args.length > 0)) {
			System.out.println(TAG + "No broker name specified.");
			return;
		}
		brokerAgentName = (String) args[0];
		System.out.println(TAG + "Name of broker agent is " + brokerAgentName);

		messageCreator = new MessageCreator(new AID(brokerAgentName, AID.ISLOCALNAME));

		try {
			publishAuctionItem("Dourade", new Random().nextInt(100), 1, (float) .5, (float) .9);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void publishAuctionItem(String name, int price, int delay, float step_rise, float step_decrease)
			throws IOException {

		ACLMessage msg;
		AuctionItem item = new AuctionItem(name, price, delay, step_rise, step_decrease);

		System.out.println(TAG + "Publishing auction item " + item.toString());

		msg = messageCreator.createMessageToBroker(Performatifs.V_TO_ANNOUNCE, Optional.of(item));
		addBehaviour(new PublishAuctionBehaviour(this, msg));
	}
}
