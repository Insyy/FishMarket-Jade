package fishmarket.agents.seller;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;

public class SellerBehaviour extends AchieveREInitiator {

    private static final String TAG = "SELLER BEHAVIOUR | ";

    public SellerBehaviour(Agent a, ACLMessage msg) {
        super(a, msg);
        System.out.println(TAG + " behaviour added");
    }

    protected void handleInform(ACLMessage inform) {
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
        } else {
            System.out.println(TAG + failure.getSender().getName() + " failed to perform the requested action");
        }
    }
}
