package fishmarket.agents.seller;
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

public class WaitForBidsBehavior extends AchieveREResponder {

    private String TAG = "BROKER BEHAVIOUR |> ";

    public WaitForBidsBehavior(Agent a, MessageTemplate mt) {
        super(a, mt);
    }

    @Override
    protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {

        // System.out.println(TAG + " Handle request");
        int jadePerformative = request.getPerformative();
        if (jadePerformative == Performatifs.B_TO_BID.getJadeEquivalent())
            try {
                return toBidHandler(request);
            } catch (UnreadableException | IOException e) {
                e.printStackTrace();
                return super.handleRequest(request);
            }
        return super.handleRequest(request);
    }

    private ACLMessage toBidHandler(ACLMessage request) throws UnreadableException, IOException {

        ACLMessage msg = request.createReply();
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
