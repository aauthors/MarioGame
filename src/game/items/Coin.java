package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.positions.NumberRange;
import game.actions.CoinAction;
import game.reset.ResettableClass;
import game.reset.ResettableInstance;
import game.status.Status;

import java.util.ArrayList;
import java.util.List;

import static game.status.Status.COIN;

/**
 * Class for the Coin item that acts as a currency.
 * <p>
 * This class inherits from the abstract Item class and
 * implements the interface ResettableClass.
 */
public class Coin extends Item implements ResettableClass {

    /**
     * Class attribute of Coin
     * stores the value of the Coin.
     */
    private int value;

    /**
     * Class attribute for Coin
     * store the value for if the coin has been added.
     */
    private boolean actionAdded = false;

    /**
     * Constructor for the Coin class.
     *
     * @param portable boolean value to be passes in indicating whether
     *                 the Coin is portable.
     * @param value    the value of the coin.
     */
    public Coin(boolean portable, int value) {
        super("Coin", '$', portable);
        this.value = value;
        this.addCapability(COIN);
        registerClass();
    }

    /**
     * Method that returns the value of the Coin object.
     *
     * @return integer value of the Coin.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * method that checks if the actor at the location of the
     * Coin object has a Wallet. If the Actor has a Wallet,
     * provide them with the ability to getCoinAction.
     *
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {
        boolean walletExists = false;

        if (currentLocation.containsAnActor()) {
            for (Item item : currentLocation.getActor().getInventory()) {
                if (item.hasCapability(Status.WALLET)) {
                    walletExists = true;
                }
            }
            if (walletExists) {
                if (!(actionAdded)) {
                    this.addAction(getCoinAction(currentLocation.getActor()));
                    actionAdded = true;
                }

            }

        }
    }

    /**
     * Method that allows the actor to get the coin.
     *
     * @param actor the actor acting.
     * @return CoinAction and action concerning the interactions with the
     * Coin object.
     */
    public CoinAction getCoinAction(Actor actor) {
        return new CoinAction(this);
    }

    /**
     * Method that resets the Coins.
     *
     * @param gameMap the map with the coins.
     */
    public void resetClass(GameMap gameMap) {
        NumberRange xRange = gameMap.getXRange();
        NumberRange yRange = gameMap.getYRange();
        for (int y : yRange) {
            for (int x : xRange) {
                Location location = gameMap.at(x, y);
                List<Item> removeItems = new ArrayList<Item>();
                for (Item item : location.getItems()) {
                    if (item.hasCapability(COIN)) {
                        removeItems.add(item);
                    }
                }
                for (Item item : removeItems) {
                    location.removeItem(item);
                }
            }
        }
    }
}

