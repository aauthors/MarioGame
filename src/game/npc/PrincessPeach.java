package game.npc;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.EndGameAction;
import game.status.Status;

public class PrincessPeach extends Actor {

    /**
     * Constructor.
     */
    public PrincessPeach() {
        super("Princess Peach", 'P', 1000);
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
        return new DoNothingAction();
    }

    /**
     * Method that returns the actors available actions.
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return ActionList a list of actions that the Actor can perform.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        // create new ActionList for Princess
        ActionList actions = new ActionList();

        // check if the other player has the key
        boolean playerHasKey = checkPlayerHasKey(otherActor);
        if (playerHasKey) {
            // add end game action for other player.
            actions.add(new EndGameAction(otherActor));
            System.out.println("Player can now choose to end the game!");
        }

        return actions;
    }

    /**
     * Method that checks the player has a Key so they
     * can end the game.
     *
     * @param otherActor the player.
     * @return Boolean a true or false value.
     */
    private boolean checkPlayerHasKey(Actor otherActor) {
        for (Item item : otherActor.getInventory()) {
            if (item.hasCapability(Status.KEY)) {
                return true;
            }
        }
        return false;
    }
}
