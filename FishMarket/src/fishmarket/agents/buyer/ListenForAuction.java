package fishmarket.agents.buyer;

import java.io.IOException;
import java.util.Optional;

import fishmarket.auction.AuctionInstance;
import fishmarket.auction.AuctionItem;
import fishmarket.performatifs.MessageCreator;
import fishmarket.performatifs.Performatifs;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.AchieveREResponder;

public class ListenForAuction extends AchieveREResponder {

    private String TAG = "LISTENFORAUCTION BEHAVIOUR |> ";

    public ListenForAuction(Agent a, MessageTemplate mt) {
        super(a, mt);
    }

    @Override
    protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {

        getAgent().addBehaviour(new ListenForAuction(getAgent(), null));
        //System.out.println(TAG + " Handle request");

        int jadePerformative = request.getPerformative();
        if (jadePerformative == Performatifs.V_TO_ANNOUNCE.getJadeEquivalent())
            try {
                return toAnnounceHandler(request);
            } catch (UnreadableException | IOException | TooBrokeException e) {
                e.printStackTrace();
                System.out.println(e.getLocalizedMessage());
                return super.handleRequest(request);
            }
        return super.handleRequest(request);
    }

    private ACLMessage toAnnounceHandler(ACLMessage request) throws UnreadableException, IOException, TooBrokeException {
        AuctionItem auctionItem = (AuctionItem) request.getContentObject();

        if (auctionItem.getPrice() < ((Buyer) getAgent()).getMoneyLeft()) {
            System.out.println("Buyer " + getAgent().getName() + " bidded on auction " + auctionItem.toString());
            return MessageCreator.createMessageToAgent(request.getSender(), Performatifs.P_TO_BID, Optional.empty(), Optional.empty());
        } else throw new TooBrokeException();        
    }

    @Override
    protected ACLMessage prepareResponse(ACLMessage request) throws NotUnderstoodException, RefuseException {
        // TODO Auto-generated method stub
        return super.prepareResponse(request);
    }

    @Override
    protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
        // TODO Auto-generated method stub
        return super.prepareResultNotification(request, response);
    }

    
}
