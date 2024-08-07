package game.reset;

import edu.monash.fit2099.engine.positions.GameMap;


public interface ResettableInstance {

    /**
     * Allows any classes that use this interface to reset abilities, attributes, and/or items.
     * HINT: play around with capability, the actual implementation happens in the tick or playTurn method.
     * @param gameMap
     * @return
     */
    void resetInstance(GameMap gameMap);

    /**
     * a default interface method that register current instance to the Singleton manager.
     * It allows corresponding class uses to be affected by global reset
     */
    default void registerInstance(){
        ResetManager.getInstance().appendResetInstance(this);
    }
}