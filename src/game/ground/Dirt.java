package game.ground;

import edu.monash.fit2099.engine.positions.Ground;
import game.status.Status;

/**
 * A class that represents bare dirt. Extends from
 * the class Ground.
 */
public class Dirt extends Ground {

    /**
     * Constructor for the Ground type Dirt.
     */
    public Dirt() {
        super('.');
        this.addCapability(Status.DIRT);
    }
}
