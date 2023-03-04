package fishmarket.agents.seller;

import jade.core.Agent;
import jade.core.AID;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;

import javax.swing.SwingUtilities;

import fishmarket.auction.AuctionItem;
import fishmarket.performatifs.Performatifs;
import fishmarket.performatifs.MessageCreator;

public class Seller extends Agent {

	private static String TAG;
	private AID broker;
	
	SellerGUI gui;

	protected void setup() {
		TAG = getName() + " |> ";
		// Read name of broker as argument

			SwingUtilities.invokeLater(new Runnable() {
			  @Override
			  public void run() {
				gui = new SellerGUI();
				//gui.refreshTableData(auctions, wonAuctionsUUID);
			  }
			});
		

		Object[] args = getArguments();

		if (!(args != null && args.length > 0)) {
			System.out.println(TAG + "No broker name specified.");
			this.takeDown();
		}

		broker = new AID(String.valueOf(args[0]), AID.ISLOCALNAME);
		System.out.println(TAG + "Name of broker agent is " + broker.getName());

		try {

			publishAuctionItem("Dourade", new Random().nextInt(100), 5, (float) .5, (float) .9);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void publishAuctionItem(String name, int price, int delay, float step_rise, float step_decrease)
			throws IOException {

		AuctionItem item = new AuctionItem(name, price, delay, step_rise, step_decrease);

		System.out.println(TAG + "Publishing auction item " + item.toString());

		addBehaviour(new PublishAuctionBehavior(this,
				MessageCreator.createMessageToAgent(
						broker,
						Performatifs.V_TO_ANNOUNCE,
						Optional.of(item),
						Optional.empty())));

	}
}
