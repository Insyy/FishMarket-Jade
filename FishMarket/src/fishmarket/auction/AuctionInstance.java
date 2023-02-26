package fishmarket.auction;

import java.util.List;

import jade.core.AID;

public class AuctionInstance {
    private AuctionItem item;
    private List<AID> buyers;
    private AID seller;
    private boolean isActive = true;

    public AuctionInstance(AuctionItem item, AID seller) {
        this.item = item;
        this.seller = seller;
    }

    public void addBuyer(AID buyer) throws BuyerAlreadyExistsException {
        if (buyers.contains(buyer)) {
            throw new BuyerAlreadyExistsException();
        }
        buyers.add(buyer);
    }

    public void removeBuyer(AID buyer) throws BuyerNotPresentException {
        if (!buyers.contains(buyer)) {
            throw new BuyerNotPresentException();
        }
        buyers.remove(buyer);
    }
    
    public AuctionItem getItem() {
        return item;
    }

    public List<AID> getBuyers() {
        return buyers;
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



    private class BuyerAlreadyExistsException extends Exception {}

    private class BuyerNotPresentException extends Exception {}

    @Override
    public String toString() {
        return "AuctionInstance [item=" + item + ", buyers=" + buyers + ", seller=" + seller + ", isActive=" + isActive
                + "]";
    }


    
}
