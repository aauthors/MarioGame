package game.npc;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.PurchaseItemAction;
import game.items.WeaponPart.GoombaLeather;
import game.items.WeaponPart.ShellFragment;
import game.items.WeaponPart.TreeBark;

/**
 * Class for the Actor Toadette
 * <p>
 * This class inherits from the abstract Actor class.
 */
public class Toadette extends Actor {

    /**
     * Constructor.
     */
    public Toadette() {
        super("Toadette", 'P', 500);
    }

    /**
     * Method that plays the Actor's next turn
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return DoNothingAction an action that does nothing.
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return new DoNothingAction();
    }

    /**
     * Method that contains a list of Actions that an Actor can perform.
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return ActionList a list of Actions.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        actions.add(new PurchaseItemAction(new TreeBark()));
        actions.add(new PurchaseItemAction(new GoombaLeather()));
        actions.add(new PurchaseItemAction(new ShellFragment()));
        return actions;

    }
}
