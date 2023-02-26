package fishmarket.agents.seller;

import jade.core.Agent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.util.Random;

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

	private static String TAG = "SELLER AGENT";
	private String brokerAgentName;
	private PerformativeCreator pCreator;

	protected void setup() {
		TAG += getName() + " |> ";
		// Read names of responders as arguments
		Object[] args = getArguments();
		if (args != null && args.length > 0) {

			brokerAgentName = (String) args[0];
			System.out.println(TAG + "Name of broker agent is " + brokerAgentName);

			pCreator = new PerformativeCreator(new AID(brokerAgentName, AID.ISLOCALNAME));

			publishAuctionItem("Dourade", new Random().nextInt(100), 1, (float) .5, (float) .9);

		} else
			System.out.println(TAG + "No broker name specified.");

	}

	private void publishAuctionItem(String name, int price, int delay, float step_rise, float step_decrease) {
		// Fill the REQUEST message
		ACLMessage msg;
		try {
			AuctionItem item = new AuctionItem(name, price, delay, step_rise, step_decrease);
			System.out.println(TAG + "Publishing auction item " + item.toString());
			msg = pCreator.createToAnnounceMsg(item);
			addBehaviour(new SellerBehaviour(this, msg));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
