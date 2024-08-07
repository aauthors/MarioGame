package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.npc.Speakable;

/**
 * A special action used to speak to another target
 */
public class SpeakAction extends Action {
    Speakable speakingTarget;

    /**
     * Constructor
     *
     * @param target the target being spoken to
     */
    public SpeakAction(Speakable target) {
        this.speakingTarget = target;
    }

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
        return speakingTarget.getLine(actor);
    }

    /**
     * Describe the action in a format suitable for displaying in the menu.
     *
     * @param actor The actor performing the action.
     * @return a string, e.g. "Player picks up the rock"
     * @see Action#menuDescription(Actor)
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Speak to " + speakingTarget;
    }
}


