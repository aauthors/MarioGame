package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.BottleConsumptionAction;
import game.items.FountainWater.PowerWater;
import game.status.Status;

import java.util.Stack;

/**
 * Class for the Bottle.
 * <p>
 * This class inherits from the abstract Item class
 * and implements the interface Consumable.
 */
public class Bottle extends Item implements Consumable {

    /**
     * Class attribute for the consumption actions
     * from drinking from the bottle.
     */
    private BottleConsumptionAction bottleConsumptionAction = new BottleConsumptionAction(this);

    /**
     * Class Attribute for the water level of the bottle.
     * Utilises a stack data structure.
     */
    Stack<Consumable> waterLevel;

    /**
     * Class attribute for adding a slot of
     * water to the bottle.
     */
    boolean actionAdded;

    /**
     * Constructor for the Bottle class.
     */
    public Bottle() {
        super("Bottle", 'b', false);
        addCapability(Status.BOTTLE);
        waterLevel = new Stack<Consumable>();

        actionAdded = false;

    }

    /**
     * Method for refilling the water bottle.
     *
     * @param water the content of the bottle.
     */
    public void refillWater(Consumable water) {
        waterLevel.push(water);
    }

    /**
     * Method that gets the water bottle level as a
     * string.
     *
     * @return a formatted string indicating the level of water
     * filled in the bottle.
     */
    public String stackToString() {
        String res = null;
        if (waterLevel.size() > 0) {
            res = "[";
            for (int i = waterLevel.size() - 1; i >= 1; i--) {
                res += waterLevel.get(i).toString() + ", ";
            }
            res += waterLevel.get(0).toString() + "]";

        }
        return res;
    }

    /**
     * method that returns the top slot of water level in
     * the bottle.
     *
     * @return A string indicating where the water level is.
     */
    public String getTopStack() {
        return waterLevel.get(waterLevel.size() - 1).toString();
    }

    /**
     * Method that either adds the ability to drink from the bottle
     * or removes the ability to drink from the bottle.
     *
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor           The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {

        if (!actionAdded && waterLevel.size() > 0) {
            addAction(bottleConsumptionAction);
            actionAdded = true;
        } else if (actionAdded && waterLevel.size() == 0) {
            removeAction(bottleConsumptionAction);
            actionAdded = false;
        }
    }

    /**
     * Method that applies the effect of drinking form the bottle.
     *
     * @param map   the map the actor is on.
     * @param actor the actor performing the consumption.
     */
    @Override
    public void consumptionEffect(GameMap map, Actor actor) {
        Consumable effect = waterLevel.pop();
        effect.consumptionEffect(map, actor);
    }


}

