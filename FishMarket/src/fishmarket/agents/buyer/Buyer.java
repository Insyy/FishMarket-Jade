package fishmarket.agents.buyer;

import jade.core.Agent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.SwingUtilities;

import fishmarket.auction.AuctionItem;

public class Buyer extends Agent {

	private static String TAG;

	private static final List<AuctionItem> auctions = new ArrayList<>();
	private static final List<UUID> wonAuctionsUUID = new ArrayList<>();

	public static List<UUID> getWonAuctionsUUID() {
		return wonAuctionsUUID;
	}

	BuyerGUI gui;

	Integer moneyLeft;

	public List<AuctionItem> getAuctions() {
		return auctions;
	}

	public Integer getMoneyLeft() {
		return moneyLeft;
	}

	public void setMoneyLeft(final Integer moneyLeft) {
		this.moneyLeft = moneyLeft;
	}

	public void addAuction(final AuctionItem auction) {
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
		

		moneyLeft = Integer.valueOf((String.valueOf(getArguments()[0])));


		// Read name of broker as argument
		addBehaviour(new ListenForAuction(this, null));
	}


}


