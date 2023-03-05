package fishmarket.agents.seller;

import jade.core.Agent;
import jade.core.AID;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.swing.SwingUtilities;

import fishmarket.auction.AuctionBid;
import fishmarket.auction.AuctionInstance;
import fishmarket.auction.AuctionItem;
import fishmarket.performatifs.Performatifs;
import fishmarket.performatifs.MessageCreator;

public class Seller extends Agent {

	private static String TAG;
	private AID broker;

	List<AuctionBid> bids = new ArrayList<>();
	
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

		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	private void publishAuctionItem(final String name, final int price, final int delay, final float step_rise, final float step_decrease)
			throws IOException {

		final AuctionItem item = new AuctionItem(name, price, delay, step_rise, step_decrease);

		System.out.println(TAG + "Publishing auction item " + item.toString());

		addBehaviour(new PublishAuctionBehavior(this,
				MessageCreator.createMessageToAgent(
						broker,
						Performatifs.V_TO_ANNOUNCE,
						Optional.of(item),
						Optional.empty())));

	}

	public void handleAuctionPublished(AuctionBid bid){
		bids.add(bid);
		gui.addBidToTable(bid);
	}
}
