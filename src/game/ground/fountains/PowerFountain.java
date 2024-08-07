package game.ground.fountains;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.FountainAction;
import game.items.FountainWater.HealingWater;
import game.items.FountainWater.PowerWater;

/**
 * Class for the PowerFountain, extends from the Ground class.
 */
public class PowerFountain extends Ground {

    /**
     * Constructor.
     */
    public PowerFountain() {
        super('A');
    }

    /**
     * Method that returns an ActionList containing a list of
     * Actions that can be performed by the actor.
     *
     * @param actor     the Actor acting
     * @param location  the current Location
     * @param direction the direction of the Ground from the Actor
     * @return ActionList -- a list of actions.
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();
        if (location.containsAnActor()) {
            actions.add(new FountainAction(new PowerWater()));
        }

        return actions;
    }
}
