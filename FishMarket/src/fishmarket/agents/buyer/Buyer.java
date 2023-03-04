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

	BuyerGUI gui;

	Integer moneyLeft;

	public List<AuctionItem> getAuctions() {
		return auctions;
	}

	public Integer getMoneyLeft() {
		return moneyLeft;
	}

	public void setMoneyLeft(Integer moneyLeft) {
		this.moneyLeft = moneyLeft;
	}

	protected void setup() {
		TAG = getName() + " |> ";

		if (!SwingUtilities.isEventDispatchThread()) {
			SwingUtilities.invokeLater(new Runnable() {
			  @Override
			  public void run() {
				gui = new BuyerGUI();
				gui.refreshTableData(auctions, wonAuctionsUUID);
			  }
			});
		}
		

		moneyLeft = Integer.valueOf((String.valueOf(getArguments()[0])));


		// Read name of broker as argument
		addBehaviour(new ListenForAuction(this, null));
	}

	public static List<UUID> getWonAuctionsUUID() {
		return wonAuctionsUUID;
	}

	public void addAuction(AuctionItem auction) {
		auctions.add(auction);
		gui.refreshTableData(auctions, wonAuctionsUUID);
	}


}


