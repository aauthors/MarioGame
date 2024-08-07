package game.enemies;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actions.AttackAction;
import game.behaviour.AttackBehaviour;
import game.behaviour.Behaviour;
import game.behaviour.FollowBehaviour;
import game.status.Status;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for enemy Bowser, drops key that can unlock the Princess's
 * Handcuffs and end the game.
 */
public class Bowser extends Actor implements Enemies {

    /**
     * Class attribute that gives Bowser his behaviours .
     */
    private Map<Integer, Behaviour> behaviours = new HashMap<>();

    private Location originLocation;

    /**
     * Constructor.
     */
    public Bowser(Location spawn) {
        super("Bowser", 'B', 50);
        addCapability(Status.HOSTILE_TO_PLAYER); // hostile to the player
        addCapability(Status.BOWSER);
        this.originLocation = spawn;
        registerInstance();
    }

    /**
    * Allows any classes that use this interface to reset abilities, attributes, and/or items.
    * HINT: play around with capability, the actual implementation happens in the tick or playTurn method.
    *
    * @param gameMap
    */
    @Override
    public void resetInstance(GameMap gameMap) {
        if (!this.originLocation.containsAnActor()) {
            gameMap.moveActor(this, this.originLocation);
        }
        this.heal(this.getMaxHp());
        removeBehaviours();
    }


    /**
     * Select and return an action to perform on the current turn.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the Action to be performed
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

        // loop through the behaviours and implement them
        for (Behaviour behaviour : behaviours.values()) {
            Action action = behaviour.getAction(this, map);
            if (action != null)
                return action;
        }
        return new DoNothingAction();
    }

    /**
     * Method that returns a list of actions that Bowser can
     * perform.
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return An ActionList of actions that can be performed by Bowser.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        // add hostility to player
        this.addCapability(Status.HOSTILE_TO_PLAYER);

        // create new action list
        ActionList actions = new ActionList();

        // it can be attacked by the Hostile opponent
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            this.behaviours.put(0, new AttackBehaviour(otherActor)); // Bowser can attack
            this.behaviours.put(1, new FollowBehaviour(otherActor)); // Bowser can follow
            actions.add(new AttackAction(this, direction));   // It can be attacked by Player
        }
        return actions;
    }

    /**
     * Method creates and returns a new intrinsic weapon for Bowser.
     *
     * @return IntrinsicWeapon, Bowser's default weapon.
     */
    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(80, "fire punches");
    }

    /**
     * This method removes three particular behaviours
     * from Koopa when it goes into its Dormant state
     * as well as removing its HOSTILE_TO_PLAYER status.
     */
    public void removeBehaviours() {
        this.behaviours.remove(0);
        this.behaviours.remove(1);
        this.removeCapability(Status.HOSTILE_TO_PLAYER);
    }
}
