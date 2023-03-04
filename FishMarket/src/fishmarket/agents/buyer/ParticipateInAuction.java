package fishmarket.agents.buyer;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;

public class ParticipateInAuction extends AchieveREInitiator {

    public ParticipateInAuction(Agent a, ACLMessage msg) {
        super(a, msg);
    }

    @Override
    protected void handleInform(ACLMessage inform) {
        super.handleInform(inform);
    }

    @Override
    protected void handleRefuse(ACLMessage refuse) {
        super.handleRefuse(refuse);
    }

    @Override
    protected void handleFailure(ACLMessage failure) {
        super.handleFailure(failure);
    }
    
}
