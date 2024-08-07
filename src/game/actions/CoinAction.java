package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;

import game.items.Coin;
import game.items.Wallet;
import game.status.Status;

/**
 * Special Action for Storing a Coin in a Wallet
 */
public class CoinAction extends Action {
    /**
     * The coin item being stored
     */
    private Coin coin;

    /**
     * Constructor.
     *
     * @param coin the coin being picked up
     */
    public CoinAction(Coin coin) {
        this.coin = coin;
    }

    /**
     * This will check if the actor has a wallet to store a coin, if so, its balance will be
     * updated with the value of the coin stored
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a suitable description to display in the UI
     * @see Action#execute(Actor, GameMap)
     */
    @Override
    public String execute(Actor actor, GameMap map) {


        int walletIndex = 0;
        for (int i = 0; i < actor.getInventory().size(); i++) {
            if (actor.getInventory().get(i).hasCapability(Status.WALLET)) {

                walletIndex = i;
                break;
            }
        }
        ((Wallet) actor.getInventory().get(walletIndex)).increaseBalance(coin.getValue());
        actor.removeItemFromInventory(coin);
        map.locationOf(actor).removeItem(coin);

        return menuDescription(actor);
    }


    /**
     * Describe the action in a format suitable for displaying in the menu.
     *
     * @see Action#menuDescription(Actor)
     * @param actor The actor performing the action.
     * @return a string, e.g. "Player picks up the rock"
     */

    /**
     * Method that retuns a description of the action performed to
     * the menu for the player to see.
     *
     * @param actor The actor performing the action.
     * @return a formatted String.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " Stores the " + coin + " (" + coin.getValue() + ")";
    }
}


