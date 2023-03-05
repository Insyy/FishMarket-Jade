package fishmarket.auction;

import java.io.Serializable;

public class AuctionBid implements Serializable {

    private String itemName;
    private String bidderName;
    private float price;

    public AuctionBid(String itemName, String bidderName, float price) {
        this.itemName = itemName;
        this.bidderName = bidderName;
        this.price = price;
    }

    public String[] toStringArray() {
        return new String[]{itemName, bidderName, Float.toString(price)};
    }
    
}
