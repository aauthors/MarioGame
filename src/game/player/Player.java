package game.player;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;

import game.actions.ResetAction;
import game.items.Bottle;
import game.items.Wallet;
import game.items.Weapons.WaluigisLuckyBlade;
import game.reset.ResettableInstance;
import game.WaterDrinker;
import game.status.Status;

/**
 * Class representing the Player.
 * <p>
 * Related to:
 * REQ2: Jump Up Super Star!
 * -- Super Mario game needs to have a jump feature. so
 * player can perform a jump action. Jumps to different
 * terrains have different success rates; e.g. Wall - 80%
 * success and 20 fall damage. If Mario consumes a Super
 * Mushroom he evolves to 'M' and is able to jump with
 * 100% success rate.
 * <p>
 * REQ3:
 * REQ4:
 * REQ5:
 */
public class Player extends Actor implements ResettableInstance, WaterDrinker {

    /**
     * A menu that contains a list of possible actions
     * that the player can take in the next turn.
     */
    private final Menu menu = new Menu();

    /**
     * Constructor creates a new Player based off
     * of the players name, display character and the
     * number of hitpoint given.
     *
     * @param name        Name to call the player in the UI
     * @param displayChar Character to represent the player in the UI
     * @param hitPoints   Player's starting number of hitpoints
     */
    public Player(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.addCapability(Status.HOSTILE_TO_ENEMY);
        this.addCapability(Status.ENTER_FLOOR);        // the player can enter the floor.
        this.addItemToInventory(new Wallet(false));
        this.addItemToInventory(new Bottle());
        this.registerInstance();


    }

    /**
     * playTurn is a method that uses a list of possible actions, the player's
     * last action, the current map the player is one and a Display to
     * execute the players next turn and update the players information
     * accordingly.
     * <p>
     * Related to:
     * REQ3: Enemies
     * --	The player can attack and destroy the Koopa's shell successfully
     * and yield a SuperMushroom from the destroyed shell.
     * <p>
     * REQ4: Magical Items
     * --	The player can implement the ability to consume magical items
     * and gain their benefits. (Power Star, Super Mushroom)
     * <p>
     * REQ5: Trading (WRENCH)
     * --	The Player can pick up and/or buy a Wrench from the Toad actor .
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return returns an action that the player has executed.
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

        if (lastAction.getNextAction() != null)
            return lastAction.getNextAction();

        // Handle multi-turn Actions
        System.out.println("Current HP: " + this.printHp());
        System.out.println("Current Balance: " + getWalletBalance(this));
        System.out.println("Inventory: " + this.getInventory());
        System.out.println("Selected Weapon: " + this.getWeapon());


        for (Item item : getInventory()) {
            if (item.hasCapability(Status.WRENCH) && !this.hasCapability(Status.HASWRENCH)) {
                this.addCapability(Status.HASWRENCH);
                break;
            }
        }

        if (!hasCapability(Status.RESET)) {
            actions.add(new ResetAction());
        }
        if (hasCapability(Status.POWERSTAREFFECT)) {
            System.out.println("INVINCIBLE");
        }

        // return/print the console menu
        return menu.showMenu(this, actions, display);
    }

    /**
     * Method that returns the display character for the actor
     * Player.
     *
     * @return character -- the display character of the actor.
     */
    @Override
    public char getDisplayChar() {
        return this.hasCapability(Status.TALL) ? Character.toUpperCase(super.getDisplayChar()) : super.getDisplayChar();
    }

    /**
     * method that resets the instance of this Player.
     *
     * @param gameMap -- the current GameMap that the Player is on.
     * @return boolean -- false to indicate that it has been reset already.
     */
    @Override
    public void resetInstance(GameMap gameMap) {
        this.heal(this.getMaxHp());
        this.removeCapability(Status.POWERSTAREFFECT);
        this.removeCapability(Status.SUPERMUSHROOM);
    }

    /**
     * Method to get the Player's current balance in the
     * wallet.
     *
     * @param actor -- the actor whose balance we are checking.
     * @return integer -- representing the amount left in the Player's
     * wallet.
     */
    public int getWalletBalance(Actor actor) {

        int walletIndex = 0;
        for (int i = 0; i < actor.getInventory().size(); i++) {
            if (actor.getInventory().get(i).hasCapability(Status.WALLET)) {

                walletIndex = i;
                break;
            }
        }
        return ((Wallet) actor.getInventory().get(walletIndex)).getBalance();
    }

    int damage = getIntrinsicWeapon().damage();

    /**
     * Method that creates and returns a new Intrinsic Weapon for
     * the Player.
     * <p>
     * This method also increases the Player's default damage if
     * they have drank the Power Water.
     *
     * @return IntrinsicWeapon a new default weapon.
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        if (this.hasCapability(Status.POWER_WATER)) {
            this.removeCapability(Status.POWER_WATER);
            this.addCapability(Status.INCREASED_DAMAGE);
            damage = damage + 5;
            return new IntrinsicWeapon(damage, "Punches");

        } else if (this.hasCapability(Status.INCREASED_DAMAGE)) {
            return new IntrinsicWeapon(damage, "Punches");
        } else {
            return new IntrinsicWeapon(5, "Punches");
        }
    }


}






