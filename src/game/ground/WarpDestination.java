package game.ground;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Abstract class for WarpDestination.
 * <p>
 * This class inherits from the abstract Ground class
 * and allws the Player to teleport between two locations.
 */
abstract public class WarpDestination extends Ground {

    /**
     * Class attribute that stores the location of the warp
     */
    private Location location;

    /**
     * Constructor.
     *
     * @param displayChar character to display for this type of terrain
     */
    public WarpDestination(char displayChar, Location location) {
        super(displayChar);
        this.location = location;
        WarpManager.getInstance().appendWarpDest(this);
    }

    /**
     * Getter for location
     *
     * @return Location a location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Getter for GameMap
     *
     * @return GameMap the map of the game
     */
    public GameMap getMap() {
        return location.map();
    }
}
