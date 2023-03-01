package fishmarket.agents.buyer.behaviours;

import java.io.IOException;
import java.util.Optional;

import fishmarket.agents.buyer.Buyer;
import fishmarket.agents.buyer.TooBrokeException;
import fishmarket.auction.AuctionInstance;
import fishmarket.performatifs.MessageCreator;
import fishmarket.performatifs.Performatifs;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.AchieveREResponder;

public class ListenForAuction extends AchieveREResponder {

    private String TAG = "BROKER BEHAVIOUR |> ";

    public ListenForAuction(Agent a, MessageTemplate mt) {
        super(a, mt);
    }

    @Override
    protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {

        getAgent().addBehaviour(new ListenForAuction(getAgent(), null));
        System.out.println(TAG + " Handle request");

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
        AuctionInstance auctionInstance = (AuctionInstance) request.getContentObject();

        if (auctionInstance.getItem().getPrice() < ((Buyer) getAgent()).getMoneyLeft()) {
            return MessageCreator.createMessageToAgent(request.getSender(), Performatifs.P_TO_BID, Optional.empty(),
            Optional.empty());
        } else throw new TooBrokeException();        
    }
}
