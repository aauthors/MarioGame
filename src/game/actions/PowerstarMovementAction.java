package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Jumpable;
import game.ground.Dirt;
import game.items.Coin;

public class PowerstarMovementAction extends Action {
    /**
     * Target location
     */
    protected Location moveToLocation;
    /**
     * One of the 8-d navigation
     */
    protected String direction;
    /**
     * Or the command key
     */

    /**
     * Constructor for PowerstarMovementAction
     *
     * @param moveToLocation the location being moved to.
     * @param direction      the direction of the movement.
     */
    public PowerstarMovementAction(Location moveToLocation, String direction) {
        this.moveToLocation = moveToLocation;
        this.direction = direction;
    }

    /**
     * Allow the Actor to be moved and completely destroy the ground in its path
     * <p>
     * Overrides Action.execute()
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of the Action suitable for the menu
     * @see Action#execute(Actor, GameMap)
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        moveToLocation.setGround(new Dirt());
        moveToLocation.addItem(new Coin(true, 5));
        map.moveActor(actor, moveToLocation);
        return menuDescription(actor);
    }

    /**
     * Method that returns a descriptive String.
     *
     * @param actor The actor performing the action.
     * @return a descriptive String for the player to read in the menu.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " moves " + direction + " and destroys EVERYTHING IN ITS PATH";
    }
}
