package game.ground;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Jumpable;
import game.actions.JumpAction;
import game.actions.PowerstarMovementAction;
import game.status.Status;

import java.util.Random;

/**
 * Class for Wall
 * Wall acts as impassable ground to enemies and can block
 * objects that have been thrown.
 * <p>
 * REQ2: Jump Up Super Star!
 * --	This is related to jump action as the Player can
 * have an action to jump to the wall.
 * <p>
 * REQ4: Magical Items
 * --	This is related to magical items through Status as
 * if it has a power star affect then Wall is able to
 * be destroyed and turns into Dirt.
 */
public class Wall extends Ground implements Jumpable {

    /**
     * Constructor fo Wall class.
     * <p>
     * Takes in no paramters.
     */
    public Wall() {
        super('#');
        this.addCapability(Status.HIGH);    // HIGH ground
        this.addCapability(Status.WALLTYPE);    // use this to indicate that ground type is wall, and help to calculate jump action things.
    }

    /**
     * Method indicates whether an actor can enter
     * the Ground type.
     * <p>
     * Overridden method from Ground
     *
     * @param actor the Actor to check
     * @return A boolean value representing whether an actor can pass on the land.
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        if (actor.hasCapability(Status.FLY)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method returns true for blocking objects that have been
     * thrown.
     *
     * @return boolean -- true, indicates that the Wall can block
     * thrown objects.
     */
    @Override
    public boolean blocksThrownObjects() {
        return true;
    }

    /**
     * method that indicates whether a jump action was successful or not
     * true if the jump was successful, false otherwise.
     *
     * @return boolean -- true if jump was successful, false otherwise.
     */
    @Override
    public boolean jumpSuccess() {
        Random rand = new Random();
        return !(rand.nextDouble() < 0.2);
    }

    /**
     * Method that calculates the amount of damage received if the jump
     * fails.
     *
     * @return integer value of the damage.
     */
    @Override
    public int jumpDamage() {
        return 20;
    }

    /**
     * Method that adds new allowable actions and for the player to execute.
     *
     * @param actor     the Actor acting
     * @param location  the current Location
     * @param direction the direction of the Ground from the Actor
     * @return actions    An ActionList of allowable actions.
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();
        if (!location.containsAnActor() && actor.hasCapability(Status.POWERSTAREFFECT)) {
            actions.add(new PowerstarMovementAction(location, direction));
        } else if (!location.containsAnActor()) {
            actions.add(new JumpAction(actor, direction, this, location));
        }
        return actions;
    }
}
