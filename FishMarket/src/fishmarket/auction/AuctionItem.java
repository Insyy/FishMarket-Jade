package fishmarket.auction;

import java.io.Serializable;
import java.util.UUID;

/**
 * AuctionItem
 */
public class AuctionItem implements Serializable {

    private UUID id;
    private String name;
    private int price;
    private int delay;
    private float step_rise;
    private float step_decrease;

    public String[] toStringArrayGUI(String vendorOrBuyer) {
        if (vendorOrBuyer == null) return new String[] { name, String.valueOf(price) };
        return new String[] { vendorOrBuyer, name, String.valueOf(price) };
    }

    public AuctionItem(String name, int price, int delay, float step_rise, float step_decrease) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.delay = delay;
        this.step_rise = step_rise;
        this.step_decrease = step_decrease;
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

    public float getStep_rise() {
        return step_rise;
    }

    public float getStep_decrease() {
        return step_decrease;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "AuctionItem [id=" + id + ", name=" + name + ", price=" + price + ", delay=" + delay + ", step_rise="
                + step_rise + ", step_decrease=" + step_decrease + "]";
    }

    public UUID getId() {
        return id;
    }

}