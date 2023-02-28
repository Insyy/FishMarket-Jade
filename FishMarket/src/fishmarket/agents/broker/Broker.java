package fishmarket.agents.broker;

import java.util.ArrayList;
import java.util.List;

import fishmarket.auction.AuctionInstance;
import fishmarket.auction.AuctionItem;
import jade.core.AID;
import jade.core.Agent;

public class Broker extends Agent {

    public static String TAG;

    private BrokerGUI brokerGUI = new BrokerGUI();

    private final List<AID> vendors = new ArrayList<>();
    private final List<AuctionInstance> auctions = new ArrayList<>();

    @Override
    protected void setup() {
        TAG = getName() + " |> ";
        System.out.println(TAG + getLocalName() + " ONLINE");

        Object[] args = getArguments();

        if (args != null && args.length > 0)
            for (Object object : args) {
                vendors.add(new AID(String.valueOf(object), AID.ISLOCALNAME));
            }

        addBehaviour(new WaitForSeller(this, null));
    }

    public void createAuctionInstance(AuctionItem item, AID seller) {
        auctions.add(new AuctionInstance(item, seller));
        brokerGUI.recreateGUI(auctions);
    }
}
