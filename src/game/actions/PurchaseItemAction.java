package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.Marketable;
import game.items.Wallet;

/**
 * Class for the Purchase Item Action which allows and Actor
 * to purchase an item.
 */
public class PurchaseItemAction extends Action implements TradeAction {

    /**
     * class attribute for a Marketable trade item.
     */
    private Marketable tradeItem;

    /**
     * Constructor for PurchaseItemAction
     *
     * @param item the marketable item that is to be traded.
     */
    public PurchaseItemAction(Marketable item) {
        this.tradeItem = item;
    }

    /**
     * Method that executes the trade action and returns a
     * descriptive strings of the actions executed.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return A descriptive String.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        try {
            Wallet wallet = getActorsWallet(actor);
            wallet.reduceBalance(tradeItem.getPrice());
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
        actor.addItemToInventory((Item) tradeItem);
        return null;
    }


    /**
     * Method that returns a descriptive formatted String.
     *
     * @param actor The actor performing the action.
     * @return A descriptive formatted String.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " buys " + tradeItem + " ($" + tradeItem.getPrice() + ")";
    }
}
