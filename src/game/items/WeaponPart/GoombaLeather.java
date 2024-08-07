package game.items.WeaponPart;

import edu.monash.fit2099.engine.items.Item;
import game.items.Marketable;
import game.status.Status;

public class GoombaLeather extends Item implements Marketable {
    private double toadPrice = 1750;

    /**
     * Constructor.
     */
    public GoombaLeather() {
        super("Goomba Leather", '~', true);
        addCapability(Status.GOOMBALEATHER);
    }

    @Override
    public double getPrice() {
        return toadPrice;
    }

    @Override
    public void setPrice(double newPrice) {
        this.toadPrice = newPrice;
    }
}
