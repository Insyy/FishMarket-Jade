package fishmarket.agents.broker;

import java.util.ArrayList;
import java.util.List;

import fishmarket.auction.AuctionInstance;
import fishmarket.auction.AuctionItem;
import jade.core.AID;
import jade.core.Agent;

public class Broker extends Agent {

    public static String TAG;

    private BrokerGUI brokerGUI;
    private List<AuctionInstance> auctions;

    @Override
    protected void setup() {
        TAG = getName() + " |> ";
        System.out.println(TAG + getLocalName() + " ONLINE");

        auctions = new ArrayList<>();
        brokerGUI = new BrokerGUI();
        
        addBehaviour(new BrokerBehaviour(this, null));
    }

    public void createAuctionInstance(AuctionItem item, AID seller) {
        auctions.add(new AuctionInstance(item, seller));
        brokerGUI.recreateGUI(auctions);
    }
}
