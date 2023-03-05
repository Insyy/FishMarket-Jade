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

public class WaitForPublishedAuctions extends AchieveREResponder {

    private String TAG = "BROKER BEHAVIOUR |> ";

    public WaitForPublishedAuctions(Agent a, MessageTemplate mt) {
        super(a, mt);
    }

    @Override
    protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {

        getAgent().addBehaviour(new WaitForPublishedAuctions(getAgent(), null));

        // System.out.println(TAG + " Handle request");
        int jadePerformative = request.getPerformative();
        if (jadePerformative == Performatifs.V_TO_ANNOUNCE.getJadeEquivalent())
            try {
                return toAnnounceHandler(request);
            } catch (UnreadableException | IOException e) {
                e.printStackTrace();
                return super.handleRequest(request);
            }
        return super.handleRequest(request);
    }

    private ACLMessage toAnnounceHandler(ACLMessage request) throws UnreadableException, IOException {

        ((Broker) getAgent()).createAuctionInstance((AuctionItem) request.getContentObject(), request.getSender());
        getAgent().addBehaviour(new EndOfAuctionWaitTime(myAgent, ((Broker) getAgent()).getLastAuctionInstance()));

        ACLMessage msg = request.createReply();
        msg.setContentObject(((Broker) getAgent()).getLastAuctionInstance());
        msg.setPerformative(ACLMessage.INFORM);
        ((Broker) getAgent()).sendAuctionItemsToBuyer();
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
