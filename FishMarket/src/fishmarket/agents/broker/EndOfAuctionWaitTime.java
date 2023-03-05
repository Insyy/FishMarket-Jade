package fishmarket.agents.broker;

import java.util.Date;

import fishmarket.auction.AuctionInstance;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;

public class EndOfAuctionWaitTime extends WakerBehaviour {

    AuctionInstance auctionInstance = null;

    public EndOfAuctionWaitTime(Agent a, AuctionInstance auctionInstance) {
        super(a, new Date(System.currentTimeMillis() + (int) auctionInstance.getItem().getDelay() * 1000));
        this.auctionInstance = auctionInstance;
    }

    @Override
    protected void onWake() {
        System.out.println("EndOfAuctionWaitTime it is time to bid on auction " + auctionInstance);
        //TODO
    }
    
}
