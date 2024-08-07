package game.behaviour;

import game.behaviour.Behaviour;

import java.util.ArrayList;
import java.util.Random;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Class for WanderBehaviour.
 * <p>
 * REQ3: Enemies
 * --	Allows the enemy to automatically move around.
 */
public class WanderBehaviour extends Action implements Behaviour {

    /**
     * Random variable to calculate the location it
     * will move to.
     */
    private final Random random = new Random();

    /**
     * Returns a MoveAction to wander to a random location, if possible.
     * If no movement is possible, returns null.
     *
     * @param actor the Actor enacting the behaviour
     * @param map   the map that actor is currently on
     * @return an Action, or null if no MoveAction is possible
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        ArrayList<Action> actions = new ArrayList<Action>();

        for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) {
                actions.add(exit.getDestination().getMoveAction(actor, "around", exit.getHotKey()));
            }
        }

        if (!actions.isEmpty()) {
            return actions.get(random.nextInt(actions.size()));
        } else {
            return null;
        }

    }

    /**
     * Method that returns the action of the Actor that
     * performed the wander behaviour.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return String -- menu description of the actor.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        return menuDescription(actor);
    }

    /**
     * Method that displays a description that follows from
     * the actor's actions.
     *
     * @param actor The actor performing the action.
     * @return String description of the actors sounds.
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Raagrh...";
    }
}
