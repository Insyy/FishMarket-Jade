package fishmarket.agents.buyer;

import java.io.IOException;
import java.util.Optional;

import fishmarket.auction.AuctionInstance;
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

    private final String TAG = "ListenForAnnounce |> ";

    public ListenForAuction(final Agent a, final MessageTemplate mt) {
        super(a, mt);
    }

    @Override
    protected ACLMessage handleRequest(final ACLMessage request) throws NotUnderstoodException, RefuseException {

        
        System.out.println(TAG + " Handle request");

        final int jadePerformative = request.getPerformative();
        if (jadePerformative == Performatifs.V_TO_ANNOUNCE.getJadeEquivalent())
            try {
                getAgent().addBehaviour(new ListenForAuction(getAgent(), null));
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
        final AuctionInstance auctionInstance =  (AuctionInstance) request.getContentObject();

        ((Buyer) getAgent()).addAuction(auctionInstance);

        

        return sendBid(request, auctionInstance);        
    }

    private ACLMessage sendBid(final ACLMessage request, final AuctionInstance auctionInstance)
            throws IOException, TooBrokeException {
        if (auctionInstance.getItem().getPrice() < ((Buyer) getAgent()).getMoneyLeft()) {
            System.out.println("Buyer " + getAgent().getName() + " bidded on auction " + auctionInstance.toString());
            return MessageCreator.createMessageToAgent(request.getSender(), Performatifs.B_TO_BID, Optional.empty(), Optional.empty());
        } else throw new TooBrokeException();
    }

    
}
