package fishmarket.agents.buyer;

import jade.core.Agent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.SwingUtilities;

import fishmarket.auction.AuctionInstance;

public class Buyer extends Agent {

	private static String TAG;

	private static final List<AuctionInstance> auctions = new ArrayList<>();
	private static final List<UUID> wonAuctionsUUID = new ArrayList<>();

	BuyerGUI gui;

	Integer moneyLeft  = 9999;
	
	public static List<UUID> getWonAuctionsUUID() {
		return wonAuctionsUUID;
	}

	public List<AuctionInstance> getAuctions() {
		return auctions;
	}

	public Integer getMoneyLeft() {
		return moneyLeft;
	}

	public void setMoneyLeft(final Integer moneyLeft) {
		this.moneyLeft = moneyLeft;
	}

	public void addAuction(final AuctionInstance auction) {

		for (int i = 0; i < auctions.size(); i++) {
			AuctionInstance t = auctions.get(i);
			// UPDATE IF ALREADY EXISTS
			if (t.getItem().getId().equals(auction.getItem().getId())) {
				auctions.set(i, auction);
				gui.refreshTableData(auctions, wonAuctionsUUID);
				return;
			}
		}

		auctions.add(auction);
		gui.refreshTableData(auctions, wonAuctionsUUID);
		
	}

	protected void setup() {
		TAG = getName() + " |> ";
		System.out.println(TAG + "ONLINE");

		if (!SwingUtilities.isEventDispatchThread()) {
			SwingUtilities.invokeLater(new Runnable() {
			  @Override
			  public void run() {
				gui = new BuyerGUI();
			  }
			});
		}

		// Read name of broker as argument
		addBehaviour(new ListenForAuction(this, null));
	}


}


