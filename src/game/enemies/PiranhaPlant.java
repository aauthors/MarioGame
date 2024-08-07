package game.enemies;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actions.AttackAction;
import game.behaviour.AttackBehaviour;
import game.behaviour.Behaviour;
import game.status.Status;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for the enemy PiranhaPlant.
 * <p>
 * PiranhaPlant extends from Actor class and implements the
 * interface Enemies.
 */
public class PiranhaPlant extends Actor implements Enemies {

    /**
     * Class attribute that stores the behaviours of the Piranha plant.
     */
    private final Map<Integer, Behaviour> behaviours = new HashMap<>();

    /**
     * Constructor.
     */
    public PiranhaPlant() {
        super("Piranha Plant", 'Y', 150);
        registerInstance();
        // no behaviours to add here.
    }

    /**
     * Method that tells the Piranha Plant what its next action is.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return Action an action that the Piranha Plant chooses to take.
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        for (Behaviour behaviour : behaviours.values()) {
            Action action = behaviour.getAction(this, map);
            if (action != null)
                return action;
        }
        return new DoNothingAction();
    }

    /**
     * Allows any classes that use this interface to reset abilities, attributes, and/or items.
     * HINT: play around with capability, the actual implementation happens in the tick or playTurn method.
     *
     * @param gameMap The current game map
     */
    @Override
    public void resetInstance(GameMap gameMap) {
        // increase the maxHP and then heal it
        this.increaseMaxHp(50);
        this.heal(getMaxHp());
    }

    /**
     * Method that populates a container for the allowable actions that
     * the actor can perform.
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return ActionList, a list of actions that the actor can perform.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        // here we add the hostility to the player and give it the attack behaviour
        this.addCapability(Status.HOSTILE_TO_PLAYER);

        // create a new action list for its actions
        ActionList actions = new ActionList();

        // allow it to be attacked by the other player
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            this.behaviours.put(0, new AttackBehaviour(otherActor));
            actions.add(new AttackAction(this, direction));
        }

        return actions;
    }


    /**
     * Method that provides the creation of an intrinsic default weapon for the actor.
     *
     * @return an intrinsic weapon.
     */
    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(90, "chomps");
    }

}
