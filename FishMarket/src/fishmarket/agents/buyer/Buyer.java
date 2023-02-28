package fishmarket.agents.buyer;

import jade.core.Agent;
import java.util.ArrayList;
import java.util.List;
import fishmarket.auction.AuctionItem;

public class Buyer extends Agent {

	private static String TAG;
	private String brokerAgentName;

	List<AuctionItem> items = new ArrayList<>();

	protected void setup() {
		TAG = getName() + " |> ";
		// Read name of broker as argument
		Object[] args = getArguments();

		if (!(args != null && args.length > 0)) {
			System.out.println(TAG + "No broker name specified.");
			return;
		}
			brokerAgentName = (String) args[0];
			System.out.println(TAG + "Name of broker agent is " + brokerAgentName);
	}
}
