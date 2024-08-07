package game.enemies;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.WaterDrinker;
import game.actions.AttackAction;
import game.behaviour.AttackBehaviour;
import game.behaviour.Behaviour;
import game.behaviour.FollowBehaviour;
import game.behaviour.WanderBehaviour;
import game.status.Status;

import java.util.HashMap;
import java.util.Map;

/**
 * A hard to kill monster without a wrench.
 * <p>
 * REQ3: Enemies -- Koopa like Goomba should be able to attack
 * and follow the Player actor. Additionally, Koopa when defeated
 * receeds into its shell and becomes dormant. This is displayed
 * on the map as the character 'D'. The Koopa can no longer attack
 * or follow the Player actor. Furthermore, when the Koopa shell
 * is struck it does not break unless the Player has a Wrench.
 * When the shell is destroyed it drops a Magical Item, the Super
 * Mushroom.
 */
public class Koopa extends Actor implements Enemies {
    private final Map<Integer, Behaviour> behaviours = new HashMap<>(); // priority, behaviour

    /**
     * Constructor for Koopa.
     */
    public Koopa() {
        // initialise the koopa through the parent constructor, give it its name and display character.
        super("Koopa", 'K', 100);
        this.behaviours.put(30, new WanderBehaviour()); // add the ability for the koopa to wander around from the beginning of its life.
        this.addCapability(Status.DORMANT); // add this to indicate that it can turn into a shell.
        registerInstance();
    }

    /**
     * Adds allowable actions such as; can be attacked by the Player
     * and also allows the Koopa to automatically attack the Player.
     *
     * @param otherActor the Actor that might perform an action.
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return list of actions
     * @see Status#HOSTILE_TO_ENEMY
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        // Add new behaviours.

        this.addCapability(Status.HOSTILE_TO_PLAYER); // this allows other enemies to see each other and not attack.
        ActionList actions = new ActionList(); // create a new ActionList.

        // It can be attacked by the Hostile opponent
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            this.behaviours.put(0, new AttackBehaviour(otherActor));  // add the ability for the koopa to attack the player automatically
            this.behaviours.put(1, new FollowBehaviour(otherActor));  // add the ability for the koopa to follow the player automatically.
            actions.add(new AttackAction(this, direction));
        }

        return actions;
    }

    /**
     * Allows the Koopa to play its turn and automatically
     * decide what actions to take next.
     * <p>
     * REQ3: Enemies -- Allows the Koopa to make decisions automatically
     * as well as execute these decisions.
     *
     * @see Actor#playTurn(ActionList, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        // if the Koopa is Unconscious, Remove behaviours and set new DisplayChar
        if (!this.isConscious()) {
            removeBehaviours();
            this.setDisplayChar('D');
            // give Koopa SHELL status so it can then be hit by wrench.
            this.addCapability(Status.SHELL);
            this.addCapability(Status.UNJUMPABLE);  // indicates that Koopa cannot perform jump action.
        }

        // loop through the behaviours and implement them
        for (Behaviour behaviour : behaviours.values()) {
            Action action = behaviour.getAction(this, map);
            if (action != null)
                return action;
        }
        return new DoNothingAction();
    }

    /**
     * Resets all instances of Koopa on the GameMap.
     * <p>
     * REQ7: Reset Game -- This allows the Koopa to be
     * reset when the function is called, if the Koopa
     * needs to be reset.
     *
     * @param gameMap The current game map
     */
    @Override
    public void resetInstance(GameMap gameMap) {
        gameMap.removeActor(this);
    }

    /**
     * Class variable for the damage of the intrinsic weapon.
     */
    int damage = getIntrinsicWeapon().damage();

    /**
     * Method that creates a new IntrinsicWeapon for the Koopa actor.
     * <p>
     * Checks if the actor has drank from the fountain and increases
     * the damage of the intrinsic weapon accordingly.
     *
     * @return IntrinsicWeapon, a new default weapon that the actor will use.
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        if (this.hasCapability(Status.POWER_WATER)) {
            this.removeCapability(Status.POWER_WATER);
            this.addCapability(Status.INCREASED_DAMAGE);
            damage = damage + 5;
            return new IntrinsicWeapon(damage, "Punches");

        } else if (this.hasCapability(Status.INCREASED_DAMAGE)) {
            return new IntrinsicWeapon(damage, "Punches");
        } else {
            return new IntrinsicWeapon(30, "Punches");
        }
    }

    /**
     * This method removes three particular behaviours
     * from Koopa when it goes into its Dormant state
     * as well as removing its HOSTILE_TO_PLAYER status.
     */
    public void removeBehaviours() {
        this.behaviours.remove(0);
        this.behaviours.remove(1);
        this.behaviours.remove(30);
        this.removeCapability(Status.HOSTILE_TO_PLAYER);
    }


}
