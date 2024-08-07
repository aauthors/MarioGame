package game.enemies;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.positions.GameMap;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.WaterDrinker;
import game.actions.AttackAction;
import game.behaviour.AttackBehaviour;
import game.behaviour.FollowBehaviour;
import game.behaviour.WanderBehaviour;
import game.behaviour.Behaviour;
import game.status.Status;

import javax.swing.plaf.synth.SynthTableUI;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * A little fungus guy.
 * <p>
 * Starts ith 20hp and attacks with a kick
 * that deals 10 damage with a 50% hitrate.
 * It has a 10% chance to be removed from the
 * map via suicide.
 * <p>
 * REQ3: Enemies -- This actor acts as a basic enemy towards
 * the Player actor. Goomba are ficitonal mushroom-like
 * species from Nintendo's Mario franchise also known as
 * "Kuribou" which loosely translates to "chestnut person".
 * The Goomba is able to follow and attack the player automatically
 * as well as wander around without user input.
 */
public class Goomba extends Actor implements Enemies {

    /**
     * behaviours -- a collection of behaviours stored in
     * a Map with key of Integer and value of Behaviour.
     * <p>
     * Is the collection of all behaviours the actor can
     * take.
     */
    private final Map<Integer, Behaviour> behaviours = new HashMap<>(); // priority, behaviour

    /**
     * Constructor.
     * <p>
     * Adds a WanderBehaviour to the behaviours collection.
     * Adds a capability that makes it hostile to the Player
     * and also another capability that indicates the actor
     * (Goomba) cannot perform a jump action. REQ2: Jump Up
     * Super Star: Enemies should not be able to jump.
     */
    public Goomba() {
        super("Goomba", 'g', 20);
        this.behaviours.put(10, new WanderBehaviour());
        // give Status of HOSTILE_TO_PLAYER so that other enemies can see this.
        this.addCapability(Status.HOSTILE_TO_PLAYER);
        this.addCapability(Status.UNJUMPABLE);    // indicates that Goomba cannot perform jump action.
        this.addCapability(Status.GOOMBA);
        registerInstance();
    }

    /**
     * Allows the Goomba to follow and attack the Player.
     * <p>
     * REQ3: Enemies -- Goomba should be able to follow and
     * attack the player automatically. The player should be
     * able to have an attack option towards the Goomba.
     *
     * @param otherActor the Actor that might perform an action.
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return list of actions
     * @see Status#HOSTILE_TO_ENEMY
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        // add behaviours to follow and attack the Player.

        ActionList actions = new ActionList();
        // It can attack be attacked only by the HOSTILE opponent and it can attack the player.
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            this.behaviours.put(1, new FollowBehaviour(otherActor));
            this.behaviours.put(0, new AttackBehaviour(otherActor));
            actions.add(new AttackAction(this, direction));
        }
        return actions;
    }

    /**
     * This method returns an action that the Goomba will execute.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return action     the action that the Goomba will perform next.
     * @see Actor#playTurn(ActionList, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        // implement calculating suicide chance
        // need to use Random
        Random rand = new Random();
        double double_random = rand.nextDouble();

        if (double_random <= 0.1) {
            // Remove the actor from the map
            map.removeActor(this);
            System.out.println("Goomba has committed suicide");
        } else {
            for (Behaviour Behaviour : behaviours.values()) {
                Action action = Behaviour.getAction(this, map);
                if (action != null)
                    return action;
            }
        }
        return new DoNothingAction();
    }

    /**
     * Damage of the intrinsic weapon the Goomba is using
     */
    int damage = getIntrinsicWeapon().damage();

    /**
     * Method that creates and returns a new IntrinsicWeapon for the actor
     * <p>
     * This method checks if the actor has drank from the fountain
     * and increases its damage accordingly.
     *
     * @return IntrinsicWeapon a new weapon that is the default for the actor.
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        if (this.hasCapability(Status.POWER_WATER)) {
            this.removeCapability(Status.POWER_WATER);
            this.addCapability(Status.INCREASED_DAMAGE);
            damage = damage + 5;
            return new IntrinsicWeapon(damage, "Kicks");

        } else if (this.hasCapability(Status.INCREASED_DAMAGE)) {
            return new IntrinsicWeapon(damage, "Kicks");
        } else {
            return new IntrinsicWeapon(10, "Kicks");
        }
    }

    /**
     * This method removes the actor from the map when
     * resetting the game.
     *
     * @param gameMap the current GameMap the Goomba actor is
     *                location on.
     */
    @Override
    public void resetInstance(GameMap gameMap) {
        gameMap.removeActor(this);
    }


}
