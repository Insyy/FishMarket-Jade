package fishmarket.agents.seller;

import jade.core.Agent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import fishmarket.auction.AuctionItem;
import fishmarket.performatifs.PerformativeCreator;

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

	private static final String TAG = "SELLER AGENT | ";
	private String brokerAgentName;
	private PerformativeCreator pCreator;

	protected void setup() {
		// Read names of responders as arguments
		Object[] args = getArguments();
		if (args != null && args.length > 0) {

			brokerAgentName = (String) args[0];
			System.out.println(TAG + "Name of broker agent is :" + brokerAgentName);

			pCreator = new PerformativeCreator(new AID(brokerAgentName, AID.ISLOCALNAME));

			publishAuctionItem("Dorade", 26, 1, (float) .5);

		} else
			System.out.println(TAG + " No broker name specified.");

	}

	private void publishAuctionItem(String name, int price, int delay, float step) {
		// Fill the REQUEST message
		ACLMessage msg;
		try {
			AuctionItem item = new AuctionItem(name, price, delay, step);
			System.out.println(TAG + "publishing auction item " + item.toString());
			msg = pCreator.createToAnnounceMsg(item);
			addBehaviour(new SellerBehaviour(this, msg));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
