package game.ground.plants;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.positions.NumberRange;
import game.ground.Dirt;
import game.reset.ResetManager;
import game.reset.ResettableClass;
import game.reset.ResettableInstance;
import game.status.Status;

import java.util.Random;

/**
 * Interface for the Tree, used to indicate Grounds of type Tree.
 */
public interface Tree extends ResettableClass {

    /**
     * Default method used to reset the tree types of Ground.
     *
     * @param gameMap the map the Ground is on.
     */
    default void resetClass(GameMap gameMap) {
        NumberRange xRange = gameMap.getXRange();
        NumberRange yRange = gameMap.getYRange();
        Random rand = new Random();
        for (int y : yRange) {
            for (int x : xRange) {
                Location location = gameMap.at(x, y);
                if (location.getGround().hasCapability(Status.TREE) && rand.nextBoolean()) {
                    location.setGround(new Dirt());
                }
            }
        }
    }
}
