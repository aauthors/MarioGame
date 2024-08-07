package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Jumpable;
import game.status.Status;

/**
 * Class for JumpAction.
 * Allows the player to jump.
 * <p>
 * REQ2: Jump Up Super Star!
 * --   This class is related to the Player's ability to perform
 * a jump action if the terrain it is jumping to allows a
 * jump action to be performed.
 *
 * @author aaut0001 - Alexander Authors
 * @version 1.0.0
 */
public class JumpAction extends Action {

    /**
     * The actor performing the jump
     */
    protected Actor actor;

    /**
     * the direction of the movement.
     */
    protected String direction;

    /**
     * A Ground type that is indicated to be Jump capabale
     * through the interface.
     * <p>
     * It allows control over success and damage received from jump.
     */
    private final Jumpable terrain;

    /**
     * The location of the destination the actor will end at.
     */
    Location moveToLocation;

    /**
     * Constructor for JumpAction
     * <p>
     * This constructor takes in the actor that is performing the jump,
     * the direction of the jump, the type of terrain it is jumping to
     * and the location it will end up at.
     *
     * @param actor          the actor performing the jump
     * @param direction      the direction of the jump
     * @param terrain        the type of terrain it is jumping to
     * @param moveToLocation the location of the destination of the jump.
     */
    public JumpAction(Actor actor, String direction, Jumpable terrain, Location moveToLocation) {
        this.terrain = terrain;
        this.actor = actor;
        this.direction = direction;
        this.moveToLocation = moveToLocation;
    }

    /**
     * Perform the Action.
     * <p>
     * this method takes in the actor which will perform the
     * action and the GameMap that the actor is currently on.
     * It then returns a short descriptive string of the actions
     * that the actor took.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result = actor + " ";
        /*
          If a player has a super Mushroom consumed, the jump will
          always succeed
         */
        if (actor.hasCapability(Status.SUPERMUSHROOM)) {
            map.moveActor(actor, moveToLocation);
            result += "jumps to " + this.direction;
        } else {
            // check if the jump to the terrain was successful
            if (!this.terrain.jumpSuccess()) {
                // jump was not successful
                // apply damage to the actor and do not move the actor
                actor.hurt(this.terrain.jumpDamage());
                if (!(actor.isConscious())) {
                    map.removeActor(actor);
                }
                result += "failed to jump";
                result += System.lineSeparator() + "Damage received: " + this.terrain.jumpDamage();
            } else {
                // jump was successful
                // move the actor to new location
                map.moveActor(actor, moveToLocation);
                result += "jumps to " + this.direction;
            }
        }

        return result;
    }

    /**
     * Returns a descriptive string of the actor's movement.
     *
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " jumps to " + this.direction;
    }
}
