package game.items.Weapons;

import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.items.Marketable;
import game.status.Status;

/**
 * Class for the Wrench item that the player can use as a Weapon.
 * <p>
 * This class inherits from the WeaponItem class and implements
 * the interface Marketable.
 */
public class Wrench extends WeaponItem implements Marketable {

    /**
     * Class attribute, how much the Wrench costs from Toad.
     */
    private double toadPrice = 200;

    /**
     * Constructor for the Wrench class.
     */
    public Wrench() {
        super("Wrench", 'w', 50, "Smacks", 80);
        this.addCapability(Status.WRENCH);
    }

    /**
     * Method that returns the amount of damage the weapon does.
     *
     * @return an integer value.
     */
    @Override
    public int damage() {
        return super.damage();
    }

    /**
     * A verb to use when displaying the results of attacking with this Weapon
     *
     * @return String, e.g. "punches", "zaps"
     */
    @Override
    public String verb() {
        return super.verb();
    }

    /**
     * An integer of how many percent (as dividend/100) the Actor can hit with this weapon.
     *
     * @return a chance to hit.
     */
    @Override
    public int chanceToHit() {
        return super.chanceToHit();
    }

    /**
     * toString method.
     *
     * @return A String.
     */
    @Override
    public String toString() {
        return "Wrench";
    }

    /**
     * Method that returns the price of the Wrench.
     *
     * @return An integer value.
     */
    @Override
    public double getPrice() {
        return toadPrice;
    }

    /**
     * Method that sets the price of the Wrench Item.
     *
     * @param newPrice the new price of the marketable item.
     */
    @Override
    public void setPrice(double newPrice) {
        this.toadPrice = newPrice;
    }
}


