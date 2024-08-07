package game.actions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import game.items.Wallet;
import game.status.Status;

/**
 * Interface for the TradeAction between actor and Toad.
 */
public interface TradeAction {

    /**
     * Interfaces default method that is concrete and already
     * implemented.
     *
     * @param actor The actor committing the trade.
     * @return The wallet of the Actor.
     */
    default Wallet getActorsWallet(Actor actor) {
        Wallet wallet = null;
        for (Item item : actor.getInventory()) {
            if (item.hasCapability(Status.WALLET)) {
                wallet = (Wallet) item;
                break;
            }
        }
        if (wallet == null) {
            throw new IllegalArgumentException("You don't possess a wallet!");
        }
        return wallet;
    }


}
