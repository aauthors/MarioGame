package game.items;

import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Class for the Fire Item that will be dropped when Bowser
 * attacks successfully.
 * <p>
 * This class inherits from the Item class and implements the
 * interface Flammable.
 */
public class Fire extends Item implements Flammable {

    /**
     * Private class attribute for the life span
     * of a Fire.
     */
    private int tickAge;

    /***
     * Constructor for Fire
     */
    public Fire() {
        super("Fire", 'v', false);
        this.tickAge = 0;
    }

    /**
     * Method that tracks the age and life span of a Fire as
     * well as apply burn damage to any Actor standing on top
     * of the Fire.
     *
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {
        if (this.tickAge == 3) {
            currentLocation.removeItem(this);
        } else if (currentLocation.containsAnActor()) {
            currentLocation.getActor().hurt(burnDamage());
            System.out.println(this + " " + verb() + currentLocation.getActor().toString());
        }
        this.tickAge++;
    }

    /**
     * Method that returns the damage the fire hurts.
     *
     * @return an integer -- 20 representing the amount of hitpoints it damages.
     */
    @Override
    public int burnDamage() {
        return 20;
    }

    /**
     * Method that returns the verb description of the Fire.
     *
     * @return A describing string.
     */
    @Override
    public String verb() {
        return "burns ";
    }

    /**
     * Method that returns String name of the Class.
     *
     * @return A String - Fire.
     */
    @Override
    public String toString() {
        return "Fire";
    }
}
