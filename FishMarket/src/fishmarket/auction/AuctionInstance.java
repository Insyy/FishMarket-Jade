package fishmarket.auction;

import jade.core.AID;
import jade.util.leap.Serializable;

public class AuctionInstance implements Serializable {
    private AuctionItem item;
    private AID seller;
    private boolean isActive = true;

    public AuctionInstance(AuctionItem item, AID seller) {
        this.item = item;
        this.seller = seller;
    }
    
    public AuctionItem getItem() {
        return item;
    }


    public AID getSeller() {
        return seller;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    
    public AuctionBid createBid(AID bidder){
        return new AuctionBid(item.getName(), bidder.getName(), item.getPrice());
    }


    @Override
    public String toString() {
        return "AuctionInstance [item=" + item + ", seller=" + seller + ", isActive=" + isActive + "]";
    }

    
    
}
