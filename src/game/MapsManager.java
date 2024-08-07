package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import game.ground.Anvil;
import game.ground.Dirt;
import game.ground.Floor;
import game.ground.Lava;
import game.ground.SlotMachine;
import game.ground.Wall;
import game.ground.WarpPipe;
import game.ground.fountains.HealthFountain;
import game.ground.fountains.PowerFountain;
import game.ground.plants.Mature;
import game.ground.plants.Sprout;
import game.items.Coin;
import game.items.PowerStar;
import game.items.SuperMushroom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsManager {

  private static MapsManager instance;

  private Map<AvailMaps, GameMap> gameMaps;

  private Map<GameMap, String> gameMapNames;

  private enum AvailMaps {
    LAVAWORLD,
    OVERWORLD
  }

  private MapsManager() {
    this.gameMaps = new HashMap<>();
    this.gameMapNames = new HashMap<>();
  }

  public static MapsManager getInstance() {
    if (instance == null) {
      instance = new MapsManager();
    }
    return instance;
  }

  public GameMap getLavaWorld() {
    if (!this.gameMaps.containsKey(AvailMaps.LAVAWORLD)) {
      FancyGroundFactory lavaGroundFactory = new FancyGroundFactory(new Dirt(), new Floor(),
          new Wall(), new Lava());
      List<String> lavaMap = Arrays.asList(
          "...........LLLLLLLL..#######################.LLLLLLLL.........",
          "............LLLLLLL..#____#___________#____#..LLLLLLL.........",
          ".............LLLLLL..#____#___________#____#...LLLLLLLLLL.....",
          ".........LLLLLLLL....#____#___________#____#......LLLLLLLL....",
          ".........LLLLLLL.....#____#####___#####____#........LLLLLLL...",
          ".....LLLLLLLLLLL.....#_____________________#.......LLLLLLLL...",
          "......LLLLLLLLLL.....##########___##########...LLLLLLLLLLL....",
          "....LLLLLLLLLLLLLL.........................LLLLLLLLLLLL.......",
          ".......LLLLLLLLLLLLLLLLLL............LLLLLLLLLLLLLLLLLL.......",
          "...........LLLLLLLLLLLLLL..LLLLLLLLLLLLLLLLLLLL...............",
          "...........LLLLLLLLLL.......LLLLLLLLLLLLLLLL..................",
          "...............LLL....LLLLL......LLLLLLL......................",
          "...................LLLLLLLLLLLLL..............................",
          "..............LLLLLLLLLLLLLLLLLLLLLLLLLLL.....................");
      GameMap lavaWorld = new GameMap(lavaGroundFactory, lavaMap);
      lavaWorld.at(3, 3).setGround(new WarpPipe(lavaWorld.at(3, 3)));
      this.gameMaps.put(AvailMaps.LAVAWORLD, lavaWorld);
      this.gameMapNames.put(lavaWorld, "Lava Zone");
    }
    return this.gameMaps.get(AvailMaps.LAVAWORLD);
  }

  public GameMap getOverWorld() {
    if (!this.gameMaps.containsKey(AvailMaps.OVERWORLD)) {
      FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(),
          new Sprout(), new Mature(), new HealthFountain(), new PowerFountain(), new Anvil());
      List<String> map = Arrays.asList(
          "..........................................##_________##.........................",
          "............+............+..................#________#..........................",
          "............................................#_______#...........................",
          "....................T........................##___##.................+..........",
          "...............................................#................................",
          "................................................#...............................",
          ".................+................................#.............................",
          ".................................................##.............................",
          "...................................A...H........##..............................",
          ".........+..............................+#____####.................+............",
          ".......................................+#_____###++.............................",
          ".......................................+#______###..............................",
          "........................................+#_____###..............................",
          "........................+........................##.............+...............",
          "...................................................#............................",
          "....................................................#_______#...................",
          "...................+.................................#_______#..................",
          "......................................................#_______#.................",
          ".......................................................##_=_##..................");
      GameMap overWorld = new GameMap(groundFactory, map);
      overWorld.at(42, 8).addItem(new Coin(true, 20000));
      overWorld.at(43, 8).addItem(new PowerStar(true));
      overWorld.at(41, 8).addItem(new SuperMushroom(true));
      overWorld.at(40, 8).addItem(new PowerStar(true));
      overWorld.at(45, 1).setGround(new SlotMachine(1000));
      overWorld.at(52, 1).setGround(new SlotMachine(100));
      overWorld.at(42, 12).setGround(new WarpPipe(overWorld.at(42, 12)));
      overWorld.at(42, 15).setGround(new WarpPipe(overWorld.at(42, 15)));
      this.gameMaps.put(AvailMaps.OVERWORLD, overWorld);
      this.gameMapNames.put(overWorld, "Over World");
    }
    return this.gameMaps.get(AvailMaps.OVERWORLD);
  }

  public GameMap getActorMap(Actor actor) {
    GameMap actorsGameMap = null;
    for (GameMap entry : this.gameMaps.values()) {
      if (entry.contains(actor)) {
        actorsGameMap = entry;
      }
    }
    if (actorsGameMap != null) {
      return actorsGameMap;
    } else {
      throw new IllegalArgumentException("Actor is not located on any game map");
    }
  }

  public String getMapName(GameMap gameMap){
    if (this.gameMapNames.containsKey(gameMap)){
      return this.gameMapNames.get(gameMap);
    }
    throw new IllegalArgumentException("Game map created outside the scope of the maps manager");
  }

}
