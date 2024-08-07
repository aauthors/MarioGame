package game.items.WeaponPart;

import edu.monash.fit2099.engine.items.Item;
import game.items.Marketable;
import game.status.Status;

public class ShellFragment extends Item implements Marketable {
    private double toadPrice = 2500;

    /**
     * Constructor.
     */
    public ShellFragment() {
        super("Shell Fragment", ',', true);
        addCapability(Status.SHELLFRAGMENT);
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
