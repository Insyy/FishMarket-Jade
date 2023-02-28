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

public class WaitForSeller extends AchieveREResponder {

    private String TAG = "BROKER BEHAVIOUR |> ";

    public WaitForSeller(Agent a, MessageTemplate mt) {
        super(a, mt);
    }

    @Override
    protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {

        getAgent().addBehaviour(new WaitForSeller(getAgent(), null));

        System.out.println(TAG + " Handle request");

        int jadePerformative = request.getPerformative();
        if (jadePerformative == Performatifs.V_TO_ANNOUNCE.getJadeEquivalent())
            try {
                return toAnnounceHandler(request);
            } catch (UnreadableException e) {
                return super.handleRequest(request);
            }

        return super.handleRequest(request);
    }

    private ACLMessage toAnnounceHandler(ACLMessage request) throws UnreadableException {
            ((Broker) getAgent()).createAuctionInstance((AuctionItem) request.getContentObject(), request.getSender());
        ACLMessage msg = request.createReply();
        msg.setPerformative(ACLMessage.INFORM);
        return msg;
    }
}
