package fishmarket.agents.seller;

import java.util.Date;

import fishmarket.auction.AuctionInstance;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;

public class EndOfAuctionWaitTime extends WakerBehaviour {

    Seller agent;


    public EndOfAuctionWaitTime(Agent a) {
        super(a, new Date(System.currentTimeMillis() + (int) ((Seller) a).getAuction().getItem().getDelay() * 1000));
        agent = (Seller) a;

    }

    @Override
    protected void onWake() {
        System.out.println("EndOfAuctionWaitTime it is time to bid !");
        //TODO
    }
    
}
