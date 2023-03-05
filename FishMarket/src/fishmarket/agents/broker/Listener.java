package fishmarket.agents.broker;

import java.io.IOException;

import fishmarket.auction.AuctionItem;
import fishmarket.performatifs.Performatifs;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.AchieveREResponder;

public class Listener extends AchieveREResponder {

    public Listener(Agent a, MessageTemplate mt) {
        super(a, mt);
    }

    @Override
    protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {

        

        // System.out.println(TAG + " Handle request");
        int jadePerformative = request.getPerformative();
        if (jadePerformative == Performatifs.V_TO_ANNOUNCE.getJadeEquivalent())
            try {
                getAgent().addBehaviour(new Listener(getAgent(), null));
                return toAnnounceHandler(request);
            } catch (UnreadableException | IOException e) {
                e.printStackTrace();
            }
        return super.handleRequest(request);
    }

    private ACLMessage toAnnounceHandler(ACLMessage request) throws UnreadableException, IOException {

        ((Broker) getAgent()).createAuctionInstance((AuctionItem) request.getContentObject(), request.getSender());

        ACLMessage msg = request.createReply();
        msg.setContentObject(((Broker) getAgent()).getLastAuctionInstance());
        msg.setPerformative(ACLMessage.INFORM);
        return msg;
    }

    @Override
    protected ACLMessage prepareResponse(ACLMessage request) throws NotUnderstoodException, RefuseException {
        return request;
        
    }

    @Override
    protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
        return response;
        
    }

    

}
