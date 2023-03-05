package fishmarket.agents.broker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fishmarket.auction.AuctionInstance;
import fishmarket.auction.AuctionItem;
import fishmarket.performatifs.MessageCreator;
import fishmarket.performatifs.Performatifs;
import jade.core.AID;
import jade.core.Agent;

public class Broker extends Agent {

    public static String TAG;

    private final BrokerGUI brokerGUI = new BrokerGUI();
    
    private final List<AID> buyers = new ArrayList<>();
    private final List<AuctionInstance> auctions = new ArrayList<>();

    public void createAuctionInstance(final AuctionItem item, final AID seller) {
        auctions.add(new AuctionInstance(item, seller));
        try {
            sendAuctionItemToBuyers(getLastAuctionInstance());
        } catch (IndexOutOfBoundsException | IOException e) {
            e.printStackTrace();
        }
        brokerGUI.recreateGUI(auctions);
    }

    public void sendAuctionItemToBuyers(AuctionInstance item) throws IOException {
        for (final AID buyerAID : buyers) {
            send(MessageCreator.createMessageToAgent(buyerAID, Performatifs.V_TO_ANNOUNCE,Optional.of(getLastAuctionInstance()), Optional.empty()));   
        }
    }

    public AuctionInstance getLastAuctionInstance() throws IndexOutOfBoundsException {
        return auctions.get(auctions.size() - 1);
    }

    @Override
    protected void setup() {
        TAG = getName() + " |> ";
        System.out.println(TAG + getLocalName() + " ONLINE");

        final Object[] args = getArguments();

        if (args != null && args.length > 0)
            for (final Object object : args) {
                buyers.add(new AID(String.valueOf(object), AID.ISLOCALNAME));
            }

        addBehaviour(new Listener(this, null));
    }

}
