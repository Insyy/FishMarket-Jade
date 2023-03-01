package fishmarket.agents.buyer;

import jade.core.Agent;
import java.util.ArrayList;
import java.util.List;

import fishmarket.agents.buyer.behaviours.ListenForAuction;
import fishmarket.auction.AuctionInstance;

public class Buyer extends Agent {

	private static String TAG;

	private static final List<AuctionInstance> items = new ArrayList<>();
	private static final List<AuctionInstance> ignoredItems = new ArrayList<>();

	Integer moneyLeft;

	public List<AuctionInstance> getItems() {
		return items;
	}

	public Integer getMoneyLeft() {
		return moneyLeft;
	}

	

	public void setMoneyLeft(Integer moneyLeft) {
		this.moneyLeft = moneyLeft;
	}

	protected void setup() {
		TAG = getName() + " |> ";

		moneyLeft = Integer.valueOf((String.valueOf(getArguments()[0])));
		// Read name of broker as argument
		addBehaviour(new ListenForAuction(this, null));
	}


}


