package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Interface for the Consumable items.
 */
public interface Consumable {

    /**
     * method that will enable effects to be taken after
     * consumption.
     *
     * @param map   the map of the Consumable item
     * @param actor the actor performing the consumption.
     */
    void consumptionEffect(GameMap map, Actor actor);

}
