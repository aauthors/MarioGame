package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.MapsManager;
import game.ground.WarpDestination;
import game.ground.WarpManager;

/**
 * Class for the WarpAction for the teleportation between
 * WarpPipes.
 */
public class WarpAction extends Action {

    /**
     * Class attribute for the source of the warp point
     */
    private WarpDestination warpSource;

    /**
     * Constructor for the WarpAction
     *
     * @param warpSource the souce of the teleportation
     */
    public WarpAction(WarpDestination warpSource) {
        this.warpSource = warpSource;
    }

    /**
     * Method that executes the warp from one location to
     * another and returns a descriptive String on the
     * execution of the action.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return A descriptive formatted String.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        WarpManager warpManager = WarpManager.getInstance();
        WarpDestination warpTarget = warpManager.warpTarget(this.warpSource);
        if (warpTarget.getLocation().containsAnActor()) {
            Actor obstructingActor = warpTarget.getLocation().getActor();
            warpTarget.getMap().removeActor(obstructingActor);
        }
        this.warpSource.getMap().moveActor(actor, warpTarget.getLocation());
        warpManager.setWarpTarget(this.warpSource, warpTarget);
        warpManager.setWarpTarget(warpTarget, this.warpSource);
        return actor + " warped to " + MapsManager.getInstance().getMapName(warpTarget.getMap());
    }

    /**
     * Method that returns a descriptive string for the player.
     *
     * @param actor The actor performing the action.
     * @return A descriptive String.
     */
    @Override
    public String menuDescription(Actor actor) {
        GameMap DestinationMap = WarpManager.getInstance().warpTarget(this.warpSource).getMap();
        String warpDestinationMap = MapsManager.getInstance().getMapName(DestinationMap);
        return "Teleport to " + warpDestinationMap;
    }
}
