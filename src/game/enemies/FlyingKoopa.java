package game.enemies;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actions.AttackAction;
import game.behaviour.AttackBehaviour;
import game.behaviour.Behaviour;
import game.behaviour.FollowBehaviour;
import game.behaviour.WanderBehaviour;
import game.status.Status;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for the enemy Flying Koopa which can fly allowing
 * it to follow the Player anywhere and wander anywhere.
 */
public class FlyingKoopa extends Actor implements Enemies {

    /**
     * Class attribute that gives the Flying Koopa its behaviours.
     */
    private final Map<Integer, Behaviour> behaviours = new HashMap<>();

    /**
     * Constructor.
     * <p>
     * the name of the Actor
     * the character that will represent the Actor in the display
     * the Actor's starting hit points
     */
    public FlyingKoopa() {
        super("Flying Koopa", 'F', 150);
        this.behaviours.put(30, new WanderBehaviour());
        this.addCapability(Status.DORMANT);
        this.addCapability(Status.FLY); // give this actor the ability to fly.
        registerInstance();
    }

    /**
     * Method that gives the Flying Koopa a set of actions that it can perform.
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return ActionList of actins that the actor (Flying Koopa) can perform.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        // add hostility to player
        this.addCapability(Status.HOSTILE_TO_PLAYER);

        // create new action list
        ActionList actions = new ActionList();

        // it can be attacked by the Hostile opponent
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            this.behaviours.put(0, new AttackBehaviour(otherActor));  // add the ability for the flying koopa to attack the player automatically
            this.behaviours.put(1, new FollowBehaviour(otherActor));  // add the ability for the flying koopa to follow the player automatically.
            actions.add(new AttackAction(this, direction));
        }

        return actions;
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
        // if the Flying Koopa is Unconscious, Remove behaviours and set new DisplayChar
        if (!this.isConscious()) {
            removeBehaviours();
            this.setDisplayChar('D');
            // give Flying Koopa SHELL status so it can then be hit by wrench.
            this.addCapability(Status.SHELL);
            this.addCapability(Status.UNJUMPABLE);  // indicates that Flying Koopa cannot perform jump action.
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
     * Allows any classes that use this interface to reset abilities, attributes, and/or items.
     * HINT: play around with capability, the actual implementation happens in the tick or playTurn method.
     *
     * @param gameMap
     * @return
     */
    @Override
    public void resetInstance(GameMap gameMap) {
        gameMap.removeActor(this);
    }


    /**
     * Method that creates and returns a new IntrinsiceWeapon for FlyingKoopa
     *
     * @return IntrinsicWeapon the default weapon for the Flying Koopa
     */
    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(30, "flying punch =D pow");
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
