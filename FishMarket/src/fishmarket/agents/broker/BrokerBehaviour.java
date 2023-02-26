package fishmarket.agents.broker;

import fishmarket.auction.AuctionItem;
import fishmarket.performatifs.Performatifs;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.AchieveREResponder;

public class BrokerBehaviour extends AchieveREResponder {

    public BrokerBehaviour(Agent a, MessageTemplate mt) {
        super(a, mt);
    }

    @Override
    protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {

        System.out.println("BROKER: Handle request");

        int jadePerformative = request.getPerformative();

        if (jadePerformative == Performatifs.V_TO_ANNOUNCE.getJadeEquivalent())
            return toAnnounceHandler(request);
        else if (jadePerformative == Performatifs.P_TO_BID.getJadeEquivalent()) {

        }

        return super.handleRequest(request);
    }

    private ACLMessage toAnnounceHandler(ACLMessage request) {
        System.out.println("TO ANNOUNCE HANDLER");
        Broker brokerAgent = (Broker) getAgent();
        try {
            brokerAgent.createAuctionInstance((AuctionItem) request.getContentObject(), request.getSender());
        } catch (UnreadableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return request;
    }
}
