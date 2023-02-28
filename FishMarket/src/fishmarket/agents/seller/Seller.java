package fishmarket.agents.seller;

import jade.core.Agent;
import jade.core.AID;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;

import fishmarket.auction.AuctionItem;
import fishmarket.performatifs.Performatifs;
import fishmarket.performatifs.MessageCreator;

public class Seller extends Agent {

	private static String TAG;
	private AID broker;

	protected void setup() {
		TAG = getName() + " |> ";
		// Read name of broker as argument
		Object[] args = getArguments();

		if (!(args != null && args.length > 0)) {
			System.out.println(TAG + "No broker name specified.");
			this.takeDown();
		}

		broker = new AID(String.valueOf(args[0]), AID.ISLOCALNAME);
		System.out.println(TAG + "Name of broker agent is " + broker.getName());

		try {

			publishAuctionItem("Dourade", new Random().nextInt(100), 1, (float) .5, (float) .9);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void publishAuctionItem(String name, int price, int delay, float step_rise, float step_decrease)
			throws IOException {

		AuctionItem item = new AuctionItem(name, price, delay, step_rise, step_decrease);

		System.out.println(TAG + "Publishing auction item " + item.toString());

		addBehaviour(new PublishAuctionBehaviour(this,
				MessageCreator.createMessageToBroker(
						broker,
						Performatifs.V_TO_ANNOUNCE,
						Optional.of(item))));
	}
}
