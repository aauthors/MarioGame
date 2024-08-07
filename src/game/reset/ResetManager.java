package game.reset;

import edu.monash.fit2099.engine.positions.GameMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A global Singleton manager that does soft-reset on the instances.
 * HINT: refer to Bootcamp Week 5 about static factory method.
 * A3: Think about how will you improve this implementation in the future assessment.
 * What could be the drawbacks of this implementation?
 */
public class ResetManager {
    /**
     * A list of resettable instances (any classes that implements Resettable,
     * such as Player implements Resettable will be stored in here)
     */
    private List<ResettableInstance> resettableInstanceInstances;
    private Map<Class<? extends ResettableClass>, ResettableClass> classResetMap;

    /**
     * A singleton reset manager instance
     */
    private static ResetManager instance;

    /**
     * Get the singleton instance of reset manager
     *
     * @return ResetManager singleton instance
     */
    public static ResetManager getInstance() {
        if (instance == null) {
            instance = new ResetManager();
        }
        return instance;
    }

    /**
     * Constructor
     */
    private ResetManager() {
        this.resettableInstanceInstances = new ArrayList<>();
        this.classResetMap = new HashMap<>();
    }

    /**
     * Reset the game by traversing through all the list
     * By doing this way, it will avoid using `instanceof` all over the place.
     */
    public void run(GameMap map) {
        while (!resettableInstanceInstances.isEmpty()) {
            ResettableInstance object = resettableInstanceInstances.get(0);
            object.resetInstance(map);
            cleanUpInstance(object);
        }

        Iterator<Map.Entry<Class<? extends ResettableClass>, ResettableClass>> entries = classResetMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Class<? extends ResettableClass>, ResettableClass> entry = entries.next();
            entry.getValue().resetClass(map);
            entries.remove();
        }
    }

    /**
     * Add the Resettable instance to the list
     */
    public void appendResetInstance(ResettableInstance resetInstance) {
        this.resettableInstanceInstances.add(resetInstance);
    }

    /**
     * @param resetClass
     * @param resetInstance
     */
    public void appendResetClass(Class<? extends ResettableClass> resetClass, ResettableClass resetInstance) {
        this.classResetMap.put(resetClass, resetInstance);
    }


    /**
     * Remove a Resettable instance from the list
     *
     * @param resettableInstance resettable object
     */
    public void cleanUpInstance(ResettableInstance resettableInstance) {
        this.resettableInstanceInstances.remove(resettableInstance);
    }

    /**
     * @param resettableClassKey
     */
    public void cleanUpClass(Class<? extends ResettableClass> resettableClassKey) {
        this.classResetMap.remove(resettableClassKey);
    }
}
