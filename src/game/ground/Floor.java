package game.ground;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import game.status.Status;

/**
 * A class that represents the floor inside a building.
 */
public class Floor extends Ground {
    public Floor() {
        super('_');
        this.addCapability(Status.FLOORTYPE); // used to indicate to jump action that it is of type floor to help with jump action things.
    }

    /**
     * Method that checks if an Actor can enter the Ground's
     * location.
     *
     * @param actor the Actor to check
     * @return A boolean value indicating whether the Actor can enter or not.
     */
    public boolean canActorEnter(Actor actor) {
        if (actor.hasCapability(Status.ENTER_FLOOR)) {
            return true;
        } else {
            return false;
        }
    }
}
