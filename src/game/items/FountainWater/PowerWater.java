package game.items.FountainWater;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.Consumable;
import game.status.Status;

/**
 * Class for the Power Water
 * <p>
 * This class extends from the abstract class Item
 * and implements the interface Consumable.
 */
public class PowerWater extends Item implements Consumable {

    /**
     * Constructor for the PowerWater class.
     */
    public PowerWater() {
        super("Power water", 'p', false);
    }

    /**
     * Method that add the effect of consuming the PowerWater
     * to an Actor.
     *
     * @param map   the map that the actor is on.
     * @param actor the actor being given the effects.
     */
    @Override
    public void consumptionEffect(GameMap map, Actor actor) {
        actor.addCapability(Status.POWER_WATER);
        actor.addCapability(Status.INCREASED_DAMAGE);
    }
}
