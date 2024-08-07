package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.Bottle;
import game.items.Consumable;
import game.status.Status;

/**
 * Class for Fountain actions
 */
public class FountainAction extends Action {

    /**
     * private variable for the consumable type water.
     */
    private Consumable water;

    /**
     * Constructor for FountainAction
     *
     * @param water the consumable
     */
    public FountainAction(Consumable water) {
        this.water = water;
    }

    /**
     * Method that executes the Fountain actions
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return A descriptive formatted String.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Bottle bottle = null;
        for (Item item : actor.getInventory()) {
            if (item.hasCapability(Status.BOTTLE)) {
                bottle = (Bottle) item;
                break;
            }
        }
        assert bottle != null;
        bottle.refillWater(water);
        return menuDescription(actor);
    }

    /**
     * Method returns a descriptive formatted String
     * for the player to see in the menu actions.
     *
     * @param actor The actor performing the action.
     * @return
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " refill " + water;
    }
}
