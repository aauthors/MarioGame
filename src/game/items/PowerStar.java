package game.items;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.ConsumableAction;
import game.status.Status;

import java.util.List;

/**
 * Class for the Item PowerStar.
 * <p>
 * This class extends from the abtract Item class and
 * implements the interfaces Marketable and Consumable
 */
public class PowerStar extends Item implements Marketable, Consumable {

    /**
     * Class attribute for the price sold by Toad Actor.
     */
    private double toadPrice = 600;

    /**
     * Class attribute for the indication that it has been added
     * to a Player's inventory.
     */
    private boolean actionAdded = false;

    /**
     * Class attribute for the age of the PowerStar after consumption.
     */
    private int ConsumeAge;

    /**
     * Class attribute for the age of the PowerStar
     */
    private int age;
    private boolean portable;

    /**
     * Class attribute for the consumable action.
     */
    private Action consumableAction = new ConsumableAction(this);

    /**
     * Constructor for the PowerStar class
     * <p>
     * Takes in one parameter, indicating whether it is portable
     * or not.
     *
     * @param portable a boolean value indicating whether the item
     *                 is portable or not.
     */
    public PowerStar(boolean portable) {
        super("Power Star", '*', portable);
        this.portable = portable;
        age = 0;
        this.addCapability(Status.POWERSTAR);
    }

    /**
     * Method that tracks the age of the PowerStar item and
     * lets the actor know that it can be consumed.
     *
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {
        age++;
        if (age == 10) {
            currentLocation.removeItem(this);
        }
        if (currentLocation.containsAnActor() && !(currentLocation.getActor().hasCapability(Status.POWERSTAREFFECT))) {
            if (!(actionAdded)) {
                this.addAction(consumableAction);
                actionAdded = true;
            }

        }
    }

    /**
     * Method that tracks the duration left of the PowerStar after consumption
     * and adds/removes the effects of consuming the PowerStar.
     *
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor           The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        if (hasCapability(Status.CONSUMED)) {
            playerHasConsumedPowerStar(actor);
        } else {
            age++;
            powerStarDurationEnded(actor);
            //This will only be used for Items bought from Toad
            addPowerStartConsumeAction(actor);
        }
    }

    /**
     * Method that adds the action to consume the PowerStar
     *
     * @param actor The actor acting
     */
    private void addPowerStartConsumeAction(Actor actor) {
        if ((!(actor.hasCapability(Status.POWERSTAREFFECT)))) {
            if (!(actionAdded)) {
                this.addAction(consumableAction);
                actionAdded = true;
            }
        }
    }

    /**
     * Method that removes the power star once its duration has ended.
     *
     * @param actor The actor that has consumed the Power Star.
     */
    private void powerStarDurationEnded(Actor actor) {
        if (age == 10) {
            actor.removeItemFromInventory(this);
        }
    }

    /**
     * Method that tracks the duration left of the Power Star
     * after the actor has consumed it.
     *
     * @param actor the Actor that has consumed the Power Star.
     */
    private void playerHasConsumedPowerStar(Actor actor) {
        this.removeAction(consumableAction);
        if (!(ConsumeAge == 10)) {
            System.out.println("Mario Consumes Power Star - " + (10 - ConsumeAge) + " turns remaining");
            ConsumeAge++;
        } else {
            actor.removeCapability(Status.POWERSTAREFFECT);
            actor.removeItemFromInventory(this);

        }
    }

    /**
     * Method that returns a new ConsumableAction.
     *
     * @param actor the Actor consuming.
     * @return A new ConsumableAction.
     */
    public ConsumableAction getConsumableAction(Actor actor) {
        return new ConsumableAction(this);
    }

    /**
     * Method that returns the price of the PowerStar.
     *
     * @return double value
     */
    @Override
    public double getPrice() {
        return toadPrice;
    }

    /**
     * Method that sets the price of the PowerStar
     *
     * @param newPrice the new price of the marketable item.
     */
    @Override
    public void setPrice(double newPrice) {
        this.toadPrice = newPrice;
    }


    /**
     * Method that execute the effects of consuming the Power Star.
     *
     * @param map   the map of the Consumable item
     * @param actor the actor performing the consumption.
     */
    @Override
    public void consumptionEffect(GameMap map, Actor actor) {
        actor.addCapability(Status.POWERSTAREFFECT);
        this.addCapability(Status.CONSUMED);

        if(this.portable){
            this.togglePortability();
        }

        map.locationOf(actor).removeItem(this);
        if (!(actor.getInventory().contains(this))) {
            actor.addItemToInventory(this);

        }
    }
}
