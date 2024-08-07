package game.behaviour;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.AttackAction;

/**
 * This is a class for implementing automatic attack
 * behaviour for enemies.
 */
public class AttackBehaviour implements Behaviour {
    /**
     * the target to receive the attack.
     */
    private final Actor target;

    /**
     * Constructor for the AttackBehaviour
     *
     * @param subject -- the subject target of the attack behaviour.
     */
    public AttackBehaviour(Actor subject) {
        this.target = subject;
    }

    /**
     * Returns a list of actions that can be performed
     * by the actor.
     *
     * @param actor the Actor acting
     * @param map   the GameMap containing the Actor
     * @return null or a new AttackAction
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        // if map has both target and the actor ->
        if (!map.contains(target) || !map.contains(actor))
            return null;

        // define the current location
        Location here = map.locationOf(actor);
        // define the location of the target
        Location there = map.locationOf(target);

        // loop through the possible exits
        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            // If the player is in a possible exit, can attack it.
            if (destination == there) {
                return new AttackAction(this.target, exit.getName());
            }
        }
        return null;
    }
}
