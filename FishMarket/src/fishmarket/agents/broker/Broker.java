package fishmarket.agents.broker;

import java.util.ArrayList;
import java.util.List;

import fishmarket.auction.AuctionInstance;
import fishmarket.auction.AuctionItem;
import jade.core.AID;
import jade.core.Agent;

public class Broker extends Agent {

    public static final String TAG = "BROKER AGENT |> ";

    private BrokerGUI brokerGUI;
    private List<AuctionInstance> auctions;

    @Override
    protected void setup() {
        System.out.println(TAG + getLocalName() + " ONLINE");

        brokerGUI = new BrokerGUI();
        auctions = new ArrayList<>();

        addBehaviour(new BrokerBehaviour(this, null));

    }

    public void createAuctionInstance(AuctionItem item, AID seller) {
        AuctionInstance auctionInstance = new AuctionInstance(item, seller);
        auctions.add(auctionInstance);
        brokerGUI.recreateGUI(auctions);
    }

}
