package game.items;

import edu.monash.fit2099.engine.items.Item;
import game.status.Status;

/**
 * Class for the Player's Wallet.
 * <p>
 * This class inherits from the abstract class Item.
 */
public class Wallet extends Item {

    /**
     * Class attribute for the balance the player holds
     * from the value of coins used.
     */
    private int balance;

    /**
     * Constructor for the Wallet class
     *
     * @param portable A boolean value indicating whether the
     *                 Wallet is portable or not.
     */
    public Wallet(boolean portable) {
        super("Wallet", 'w', portable);
        balance = 0;
        addCapability(Status.WALLET);
    }

    /**
     * Method that updates the balance of the Wallet.
     *
     * @param amount the amount to increase by.
     */
    public void increaseBalance(double amount) {
        this.balance += amount;
    }

    /**
     * Method that updates teh balance of teh Wallet.
     *
     * @param amount the amount to decrease by.
     */
    public void reduceBalance(double amount) {
        if (amount <= this.getBalance()) {
            this.balance -= amount;
        } else {
            throw new IllegalArgumentException("You don't have enough coins!");
        }
    }

    /**
     * Method that gets the balance of the Wallet.
     *
     * @return an integer amount
     */
    public int getBalance() {
        return balance;
    }

}
