package fishmarket.agents.buyer;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;

public class ParticipateInAuction extends AchieveREInitiator {

    public ParticipateInAuction(Agent a, ACLMessage msg) {
        super(a, msg);
        //TODO Auto-generated constructor stub
    }

    @Override
    protected void handleInform(ACLMessage inform) {
        // TODO Auto-generated method stub
        super.handleInform(inform);
    }

    @Override
    protected void handleRefuse(ACLMessage refuse) {
        // TODO Auto-generated method stub
        super.handleRefuse(refuse);
    }

    @Override
    protected void handleFailure(ACLMessage failure) {
        // TODO Auto-generated method stub
        super.handleFailure(failure);
    }
    
}
