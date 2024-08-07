package game.npc;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.PurchaseItemAction;
import game.actions.SpeakAction;
import game.items.PowerStar;
import game.items.SuperMushroom;
import game.items.Weapons.Wrench;
import game.status.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class for the Toad Actor. Allows player to trade
 * items and consumables.
 * <p>
 * This class inherits from the abstract Actor class
 * and implements the Speakable interface.
 */
public class Toad extends Actor implements Speakable {

    /**
     * Constructor for the Toad Class.
     */
    public Toad() {
        // Set hit points to 50 for no reason
        super("Toad", 'O', 500);
    }

    /**
     * Method that returns a list of available actions
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return ActionList a list of available actions that can be performed.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        actions.add(new PurchaseItemAction(new PowerStar(false)));
        actions.add(new PurchaseItemAction(new SuperMushroom(false)));
        Wrench wrench = new Wrench();
        wrench.togglePortability();
        actions.add(new PurchaseItemAction(wrench));
        actions.add(new SpeakAction(this));
        return actions;
    }

    /**
     * Method that returns the Actor (Toad's) playable turn
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
     * Method that gets a random line of dialogue from the Toad.
     *
     * @param caller the initiater of the speech.
     * @return A String value.
     */
    public String getLine(Actor caller) {
        Random rand = new Random();
        List<String> dialogue = availableDialogue(caller);
        return dialogue.get(rand.nextInt(dialogue.size()));
    }

    /**
     * Method that contains all the available dialogue that
     * can be spoken.
     *
     * @param caller The caller.
     * @return A list of Strings.
     */
    public List<String> availableDialogue(Actor caller) {
        List<String> dialogueOptions = new ArrayList<String>();
        dialogueOptions.add("The Princess is depending on you! You are our only hope.");
        dialogueOptions.add("Being imprisoned in these walls can drive a fungus crazy :(");
        boolean wrenchMatch = false;
        for (Item item : caller.getInventory()) {
            if (item.hasCapability(Status.WRENCH)) {
                wrenchMatch = true;
                break;
            }
        }
        if (!wrenchMatch) {
            dialogueOptions.add("You might need a wrench to smash Koopa's hard shells.");
        }
        if (!caller.hasCapability(Status.POWERSTAREFFECT)) {
            dialogueOptions.add("You better get back to finding the Power Stars.");
        }
        return dialogueOptions;
    }
}

