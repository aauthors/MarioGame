package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.WaterDrinker;
import game.items.Bottle;
import game.items.Consumable;

/**
 * Class for consuming water from the bottle
 */
public class BottleConsumptionAction extends Action {
    /**
     * Private class variable for Bottle object.
     */
    private Bottle bottle;

    /**
     * constructor for BottleConsumptionAction action.
     *
     * @param bottle
     */
    public BottleConsumptionAction(Bottle bottle) {
        this.bottle = bottle;
    }

    /**
     * This method executes the drinking action from the bottle.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return A descriptive formatted String.
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        String water = bottle.getTopStack();
        bottle.consumptionEffect(map, actor);
        return actor + " consumes " + water;
    }

    /**
     * Method that returns a descriptive formatted String.
     *
     * @param actor The actor performing the action.
     * @return A descriptive fomatted String.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " consumes " + bottle + " " + bottle.stackToString();
    }
}
