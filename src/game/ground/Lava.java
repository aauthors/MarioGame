package game.ground;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.status.Status;

/**
 * Class for Lava, extends from the class Ground.
 */
public class Lava extends Ground {

    /**
     * Class attribute for the damage the lava
     * applies to those that walk on it.
     */
    private final int lavaDamage = 15;

    /**
     * Constructor for the Lava class.
     */
    public Lava() {
        super('L');
    }

    /**
     * Method that applies damage to Actors that walk
     * across it based off the world's time.
     *
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        if (location.containsAnActor()&& !location.getActor().hasCapability(Status.POWERSTAREFFECT)) {
            location.getActor().hurt(lavaDamage);
        }
    }
}
