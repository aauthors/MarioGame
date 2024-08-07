package game;

import game.enemies.Bowser;
import game.enemies.Goomba;
import game.npc.PrincessPeach;
import game.npc.Toad;
import game.npc.Toadette;
import game.player.Player;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;

/**
 * The main class for the Mario World game.
 */
public class Application {

    public static void main(String[] args) {

        World world = new World(new Display());
        MapsManager gameMapsManager = MapsManager.getInstance();

        GameMap overWorld = gameMapsManager.getOverWorld();
        world.addGameMap(overWorld);
        overWorld.at(42, 11).addActor(new Toad());
        overWorld.at(49, 0).addActor(new Toadette());
        overWorld.at(42, 7).addActor(new Goomba());
        Actor mario = new Player("Player", 'm', 100);
        world.addPlayer(mario, overWorld.at(42, 10));

        GameMap lavaWorld = gameMapsManager.getLavaWorld();
        world.addGameMap(lavaWorld);
        lavaWorld.addActor(new Bowser(lavaWorld.at(32, 2)), lavaWorld.at(32, 2));
        lavaWorld.addActor(new PrincessPeach(), lavaWorld.at(32, 1));
        world.run();
    }
}
