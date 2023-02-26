package fishmarket.agents.broker;

import java.util.ArrayList;
import java.util.List;

import fishmarket.auction.AuctionInstance;
import fishmarket.auction.AuctionItem;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Broker extends Agent {

    private BrokerGUI table;
    private List<AuctionInstance> auctions;
    private MessageTemplate template;

    @Override
    protected void setup() {
        System.out.println("BROKER Agent " + getLocalName() + " ONLINE");

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                table = new BrokerGUI();
            }
        });

        template = MessageTemplate.and(
                MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST));

        auctions = new ArrayList<>();

        addBehaviour(new BrokerBehaviour(this, template));

    }

    public void createAuctionInstance(AuctionItem item, AID seller) {
        AuctionInstance auctionInstance = new AuctionInstance(item, seller);
        System.out.println("creating auction " + auctionInstance.toString());
        auctions.add(auctionInstance);
        table.refreshTableData(auctions);
    }

}
