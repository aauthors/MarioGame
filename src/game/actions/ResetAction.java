package game.actions;

import static game.status.Status.RESET;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.MapsManager;
import game.reset.ResetManager;

/**
 * A special action used to reset the game
 */
public class ResetAction extends Action {

    /**
     * Add the item to the actor's inventory.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a suitable description to display in the UI
     * @see Action#execute(Actor, GameMap)
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (!actor.hasCapability(RESET)) {
            actor.addCapability(RESET);
            ResetManager.getInstance().run(map.locationOf(actor).map());
            return menuDescription(actor);
        } else {
            throw new IllegalArgumentException("Invalid caller! Actor has already reset the game.");
        }
    }

    /**
     * Describe the action in a format suitable for displaying in the menu.
     *
     * @param actor The actor performing the action.
     * @return a string, e.g. "Pl7ayer picks up the rock"
     * @see Action#menuDescription(Actor)
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " resets the game";
    }

    /**
     * Use to assign the action to a particular hotkey
     *
     * @return a char
     * @see Action#hotkey()
     */
    @Override
    public String hotkey() {
        return "r";
    }
}
