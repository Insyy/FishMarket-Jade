package fishmarket.agents.buyer;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.proto.SubscriptionInitiator;

public class SubscribeToAuction extends SubscriptionInitiator {

    private String TAG = "SubscribeToAuction |> ";

    public SubscribeToAuction(Agent a, ACLMessage msg) {
        super(a, msg);
        //TODO Auto-generated constructor stub
    }

    @Override
    protected void handleAgree(ACLMessage agree) {
        System.out.println(TAG + " handleAgree");
        // TODO Auto-generated method stub
        super.handleAgree(agree);
    }

    @Override
    protected void handleRefuse(ACLMessage refuse) {
        System.out.println(TAG + " handleRefuse");
        // TODO Auto-generated method stub
        super.handleRefuse(refuse);
    }

    @Override
    protected void handleFailure(ACLMessage failure) {
        System.out.println(TAG + " handleFailure");
        // TODO Auto-generated method stub
        super.handleFailure(failure);
    }

    @Override
    protected void handleInform(ACLMessage inform) {
        System.out.println(TAG + " handleInform");
        // TODO Auto-generated method stub
        super.handleInform(inform);
    }

    
    
}
