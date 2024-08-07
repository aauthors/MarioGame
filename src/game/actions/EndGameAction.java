package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Class for ending the game
 */
public class EndGameAction extends Action {

    /**
     * private class varible for the actor.
     */
    private Actor actor;

    /**
     * Constructor
     *
     * @param actor The actor (Player) to remove to end the game.
     */
    public EndGameAction(Actor actor) {
        setEndGameActionActor(actor);
    }

    /**
     * method to set the actor to remove.
     *
     * @param actor the actor to remove.
     */
    private void setEndGameActionActor(Actor actor) {
        this.actor = actor;
    }

    /**
     * Perform the Action.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        map.removeActor(actor);
        return "Congratulations!! You saved the Princess!!";
    }

    /**
     * Returns a descriptive string
     *
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Princess Peach ends the game";
    }
}
