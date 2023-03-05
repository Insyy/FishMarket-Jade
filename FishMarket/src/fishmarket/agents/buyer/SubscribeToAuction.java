package fishmarket.agents.buyer;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.proto.SubscriptionInitiator;

public class SubscribeToAuction extends SubscriptionInitiator {

    private String TAG = "SubscribeToAuction |> ";

    public SubscribeToAuction(Agent a, ACLMessage msg) {
        super(a, msg);
    }

    @Override
    protected void handleAgree(ACLMessage agree) {
        System.out.println(TAG + " handleAgree");
        super.handleAgree(agree);
    }

    @Override
    protected void handleRefuse(ACLMessage refuse) {
        System.out.println(TAG + " handleRefuse");
        super.handleRefuse(refuse);
    }

    @Override
    protected void handleFailure(ACLMessage failure) {
        System.out.println(TAG + " handleFailure");
        super.handleFailure(failure);
    }

    @Override
    protected void handleInform(ACLMessage inform) {
        System.out.println(TAG + " handleInform");
        super.handleInform(inform);
    }

    
    
}
