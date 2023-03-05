package fishmarket.agents.seller;

import jade.core.Agent;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SubscriptionResponder;

public class SellerSubscriptionResponder extends SubscriptionResponder{

    public SellerSubscriptionResponder(Agent a, MessageTemplate mt) {
        super(a, mt);
    }

    @Override
    protected ACLMessage handleSubscription(ACLMessage subscription) throws NotUnderstoodException, RefuseException {
        ((Seller) getAgent()).addSubscriber(subscription.getSender());
        return super.handleSubscription(subscription);
    }
    
    

}
