package fishmarket.agents.seller;

import jade.core.Agent;
import jade.core.AID;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.SwingUtilities;

import fishmarket.auction.AuctionBid;
import fishmarket.auction.AuctionInstance;
import fishmarket.auction.AuctionItem;
import fishmarket.performatifs.Performatifs;
import fishmarket.performatifs.MessageCreator;

public class Seller extends Agent {

	String TAG;
	private AID broker;

	private AuctionInstance auction = null;
	private List<AuctionBid> bids = new ArrayList<>();
	private List<AID> subscribedAgents = new ArrayList<>();
	
	SellerGUI gui;

	protected void setup() {
		TAG = getName() + " |> ";
		// Read name of broker as argument
		Seller localref = this;

			SwingUtilities.invokeLater(new Runnable() {
			  @Override
			  public void run() {
				gui = new SellerGUI(localref);
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

	}

	public void publishAuctionItem(AuctionItem item)
			throws IOException {

		System.out.println(TAG + "Publishing auction item " + item.toString());
		
        addBehaviour(new SellerSubscriptionResponder(this, null));

		addBehaviour(new PublishAuctionBehavior(this,
				MessageCreator.createMessageToAgent(
						broker,
						Performatifs.V_TO_ANNOUNCE,
						Optional.of(item),
						Optional.empty())));



	}

	public void handleBidReceived(AuctionBid bid){
		System.out.println(TAG + " Bid received " + bid.toString());
		bids.add(bid);
		gui.addBidToTable(bid);
	}

	public void addSubscriber(AID agent){
		if (subscribedAgents.contains(agent)) return;
		subscribedAgents.add(agent);
	}

	public AuctionInstance getAuction(){
		return auction;
	}
}
