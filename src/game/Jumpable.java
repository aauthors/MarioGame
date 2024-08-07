package game;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * an interface that allows jump to happen.
 *
 * REQ2: Jump Up Super Star!
 * --   This interface indicates which types of Ground
 *      the actor can jump to. Additionally, requires
 *      methods for calculating jump success and
 *      fall damage to the Player actor if the jump
 *      is not successful.
 */
public interface Jumpable {
    /**
     * method to return whether a jump was
     * successful or not to a particular type of
     * Jumpable Ground.
     *
     * NOTE: implemented in the Ground classes.
     * @return boolen -- true or false for success of jump action.
     */
    boolean jumpSuccess();

    /**
     * Method that returns an integer representing
     * the amount of damage the Player will recieve if
     * the jump was unsuccessful.
     *
     * @return integer -- a whole number representing the amount
     *                    of hurt damage a player will receive for
     *                    an unsuccessful jump.
     */
    int jumpDamage();
}
