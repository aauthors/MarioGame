package game.ground.fountains;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.FountainAction;
import game.items.FountainWater.HealingWater;

/**
 * Class for the HealthFountain
 */
public class HealthFountain extends Ground {

    /**
     * Constructor.
     */
    public HealthFountain() {
        super('H');
    }

    /**
     * Method that provides the allowable actions on the
     * Health Fountain.
     *
     * @param actor     the Actor acting
     * @param location  the current Location
     * @param direction the direction of the Ground from the Actor
     * @return ActionList -- a list of actions that can be performed.
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();
        if (location.containsAnActor()) {
            actions.add(new FountainAction(new HealingWater()));
        }
        return actions;
    }
}

