package fishmarket.auction;

import java.io.Serializable;

/**
 * AuctionItem
 */
public class AuctionItem implements Serializable {

    private String name;
    private int price;
    private int delay;
    private float step;

    public String[] toStringArray() {
        return new String[] { name, String.valueOf(price), String.valueOf(delay), String.valueOf(step) };

    }

    public AuctionItem(String name, int price, int delay, float step) {
        this.name = name;
        this.price = price;
        this.delay = delay;
        this.step = step;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getDelay() {
        return delay;
    }

    public float getStep() {
        return step;
    }

    @Override
    public String toString() {
        return "AuctionItem [nom=" + name + ", prix=" + price + ", delai=" + delay + ", pas_variation=" + step
                + "]";
    }
}