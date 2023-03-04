package fishmarket.agents.buyer;

import java.io.IOException;
import java.util.Optional;

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

    private final String TAG = "LISTENFORAUCTION BEHAVIOUR |> ";

    public ListenForAuction(final Agent a, final MessageTemplate mt) {
        super(a, mt);
    }

    @Override
    protected ACLMessage handleRequest(final ACLMessage request) throws NotUnderstoodException, RefuseException {

        getAgent().addBehaviour(new ListenForAuction(getAgent(), null));
        //System.out.println(TAG + " Handle request");

        final int jadePerformative = request.getPerformative();
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

    @Override
    protected ACLMessage prepareResponse(final ACLMessage request) throws NotUnderstoodException, RefuseException {
        return request;
        
    }


    @Override
    protected ACLMessage prepareResultNotification(final ACLMessage request, final ACLMessage response) throws FailureException {
        return response;
        
    }

    private ACLMessage toAnnounceHandler(final ACLMessage request) throws UnreadableException, IOException, TooBrokeException {
        final AuctionItem auctionItem = (AuctionItem) request.getContentObject();

        ((Buyer) getAgent()).addAuction(auctionItem);

        if (auctionItem.getPrice() < ((Buyer) getAgent()).getMoneyLeft()) {
            System.out.println("Buyer " + getAgent().getName() + " bidded on auction " + auctionItem.toString());
            return MessageCreator.createMessageToAgent(request.getSender(), Performatifs.P_TO_BID, Optional.empty(), Optional.empty());
        } else throw new TooBrokeException();        
    }

    
}
