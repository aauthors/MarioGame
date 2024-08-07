package game.ground;

import edu.monash.fit2099.engine.positions.GameMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Class for WarpManger
 * <p>
 * This class manages how warping is conducted and executed.
 */
public class WarpManager {

    /**
     * Class variable for the WarpManager instance.
     */
    private static WarpManager instance;

    /**
     * Class Attribute for the destination of the warp.
     */
    private Map<WarpDestination, WarpDestination> warpAssignedTarget;

    // use tree
    /**
     * Class Attribute
     */
    private Map<GameMap, Map<Class<? extends WarpDestination>, Map<WarpDestination, WarpDestination>>> mapWarpDestinations;

    private WarpManager() {
        this.warpAssignedTarget = new HashMap<>();
        this.mapWarpDestinations = new HashMap<>();
    }

    public static WarpManager getInstance() {
        if (instance == null) {
            instance = new WarpManager();
        }
        return instance;
    }

    public void appendWarpDest(WarpDestination object) {
        if (!this.mapWarpDestinations.containsKey(object.getMap())) {
            this.mapWarpDestinations.put(object.getMap(), new HashMap<>());
        }
        Map<Class<? extends WarpDestination>, Map<WarpDestination, WarpDestination>> classMap = this.mapWarpDestinations.get(object.getMap());
        if (!classMap.containsKey(object.getClass())) {
            classMap.put(object.getClass(), new HashMap<>());
        }
        classMap.get(object.getClass()).put(object, object);
        this.warpAssignedTarget.put(object, null);
    }

    public WarpDestination warpTarget(WarpDestination warp) {
        if (this.warpAssignedTarget.get(warp) == null) {
            Random r = new Random();
            List<GameMap> keysAsArray = new ArrayList<>();
            for (GameMap map : this.mapWarpDestinations.keySet()) {
                if (!map.equals(warp.getMap())) {
                    keysAsArray.add(map);
                }
            }
            GameMap altMap = keysAsArray.get(r.nextInt(keysAsArray.size()));
            Map<WarpDestination, WarpDestination> warpDests = this.mapWarpDestinations.get(altMap).get(warp.getClass());
            List<WarpDestination> targets = new ArrayList<>();
            for (WarpDestination warpZones : warpDests.keySet()) {
                targets.add(warpZones);
            }
            return targets.get(r.nextInt(targets.size()));
        }
        return this.warpAssignedTarget.get(warp);
    }

    public void setWarpTarget(WarpDestination source, WarpDestination target) {
        this.warpAssignedTarget.put(source, target);
    }
}
