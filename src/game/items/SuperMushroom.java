package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.ConsumableAction;
import game.status.Status;

/**
 * Class for the SuperMushroom item.
 * <p>
 * This class inherits from the abstract Item class and
 * implements the interfaces Marketable and Capable.
 */
public class SuperMushroom extends Item implements Marketable, Consumable {

    /**
     * Class attribute representing the price of the SuperMushroom
     * from the Toad.
     */
    private double toadPrice = 400;

    /**
     * Class attribute representing whether the item has been
     * added or not.
     */
    private boolean actionAdded = false;

    /**
     * Constructor for the class Super Mushroom.
     *
     * @param portable A boolean value indicating whether the SuperMushroom
     *                 is portable or not.
     */
    public SuperMushroom(boolean portable) {

        super("Super Mushroom", '^', portable);
        addCapability(Status.SUPERMUSHROOM);
    }

    /**
     * Method that checks if an Actor can add the SuperMushroom to
     * their inventory.
     *
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {
        if (currentLocation.containsAnActor()) {
            if (!(actionAdded)) {
                this.addAction(getConsumableAction(currentLocation.getActor()));
                actionAdded = true;
            }
        }
    }

    /**
     * Method that checks if an Actor can add the SuperMushroom to
     * their inventory.
     *
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor           The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        //This will only be used for items bought from Toad
        if (currentLocation.containsAnActor()) {
            if (!(actionAdded)) {
                this.addAction(getConsumableAction(currentLocation.getActor()));
                actionAdded = true;
            }
        }
    }

    /**
     * Method that creates and returns a new ConsumableAction.
     *
     * @param actor The actor acting.
     * @return A new ConsumableAction.
     */
    public ConsumableAction getConsumableAction(Actor actor) {
        return new ConsumableAction(this);
    }

    /**
     * Method that gets the value of the SuperMushroom.
     *
     * @return A double value.
     */
    @Override
    public double getPrice() {
        return toadPrice;
    }

    /**
     * Method that sets the value of the SuperMushroom.
     *
     * @param newPrice the new price of the marketable item.
     */
    @Override
    public void setPrice(double newPrice) {
        this.toadPrice = newPrice;
    }

    /**
     * Method that adds the effect of consuming the SuperMushroom.
     *
     * @param map   the map of the Consumable item
     * @param actor the actor performing the consumption.
     */
    @Override
    public void consumptionEffect(GameMap map, Actor actor) {
        actor.removeItemFromInventory(this);
        map.locationOf(actor).removeItem(this);
        actor.addCapability(Status.SUPERMUSHROOM);
        actor.increaseMaxHp(50);
        actor.addCapability(Status.TALL);
    }
}

