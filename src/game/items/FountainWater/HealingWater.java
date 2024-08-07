package game.items.FountainWater;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.WaterDrinker;
import game.items.Consumable;

/**
 * Class for the Healing Water
 * <p>
 * This class extends form the abstract class item and
 * implements the interface Consumable.
 */
public class HealingWater extends Item implements Consumable {

    /**
     * Constructor for Healing Water.
     */
    public HealingWater() {
        super("Healing water", 'h', false);
    }

    /**
     * Method that performs the effect of consuming the HealingWater.
     *
     * @param map   the map the actor is currently on.
     * @param actor the actor that is being healed.
     */
    @Override
    public void consumptionEffect(GameMap map, Actor actor) {
        actor.heal(50);
    }
}

