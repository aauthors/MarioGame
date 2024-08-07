package game.items.WeaponPart;

import edu.monash.fit2099.engine.items.Item;
import game.items.Marketable;
import game.status.Status;

public class TreeBark extends Item implements Marketable {
    private double toadPrice = 1000;

    /**
     * Constructor.
     */
    public TreeBark() {
        super("Tree Bark", '(', true);
        addCapability(Status.TREEBARK);
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
