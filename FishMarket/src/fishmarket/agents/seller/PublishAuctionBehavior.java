package fishmarket.agents.seller;

import fishmarket.auction.AuctionBid;
import fishmarket.auction.AuctionInstance;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.proto.AchieveREInitiator;

public class PublishAuctionBehavior extends AchieveREInitiator {

    private static final String TAG = "SELLER BEHAVIOUR |> ";

    public PublishAuctionBehavior(Agent a, ACLMessage msg) {
        super(a, msg);
    }

    protected void handleInform(ACLMessage inform) {
        try {
            ((Seller) getAgent()).handleAuctionPublished((AuctionBid) inform.getContentObject());
        } catch (UnreadableException e) {
            System.err.println("Tried to deserialize auction instance object sent from broker to seller.");
            e.printStackTrace();
        }
        System.out.println(TAG + inform.getSender().getName() + " successfully performed the requested action");
    }

    protected void handleRefuse(ACLMessage refuse) {
        System.out.println(TAG + refuse.getSender().getName() + " refused to perform the requested action");
    }

    protected void handleFailure(ACLMessage failure) {
        if (failure.getSender().equals(myAgent.getAMS())) {
            // FAILURE notification from the JADE runtime: the receiver
            // does not exist
            System.out.println(TAG + "Responder does not exist");
            done();
        } else {
            System.out.println(TAG + failure.getSender().getName() + " failed to perform the requested action");
            block(10_000);
        }
    }
}
