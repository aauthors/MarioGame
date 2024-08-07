package game.reset;

import edu.monash.fit2099.engine.positions.GameMap;


public interface ResettableClass {
  void resetClass(GameMap gameMap);

  default void registerClass(){
    ResetManager.getInstance().appendResetClass(this.getClass(), this);
  }
}
