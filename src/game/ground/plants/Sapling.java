package game.ground.plants;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Jumpable;
import game.actions.JumpAction;
import game.actions.PowerstarMovementAction;
import game.items.Coin;
import game.items.WeaponPart.TreeBark;
import game.status.Status;

import java.util.Random;

/**
 * Class for Sapling
 */
public class Sapling extends Ground implements Tree, Jumpable {


    /**
     * Integer representing the age of the Sapling
     */
    private int age;

    /**
     * Random variable for calculating the ability to spawn
     * a new Coin object.
     */
    private Random rand = new Random();

    /**
     * Constructor for the Sapling Class
     */
    public Sapling() {
        super('t');
        age = 0;
        this.addCapability(Status.TREE);
        registerClass();
        // Sapling has MEDIUM terrain
        this.addCapability(Status.HIGH);  // indicates that Sapling is smaller than Mature but taller than sprout
        this.addCapability(Status.SAPLINGTYPE); // use to indicate sapling type to calculate jump success and dmg.
    }


    /**
     * Ground can also experience the joy of time.
     * This method will tick for each turn made within the game, and will perform the Saplings life cycle,
     * including the items it spawns, and its growth into a Mature
     *
     * @param location The location of the Ground
     * @see Ground#tick(Location)
     */
    @Override
    public void tick(Location location) {
        age++;
        if (age != 10) {
            if (rand.nextInt(100) < 3) {
                location.addItem(new TreeBark());
            }

            if (rand.nextInt(10) < 1) {
                location.addItem(new Coin(true, 20));
            }
        } else {
            location.setGround(new Mature());
        }
    }

    /**
     * method that calculates if a jump was successful
     *
     * @return boolean value indicating success of jump action
     */
    @Override
    public boolean jumpSuccess() {
        Random rand = new Random();
        // success chance is 90% for sprout
        return !(rand.nextDouble() < 0.2);
    }

    /**
     * method that returns the amount of damage the actor will
     * receive if jump is failed.
     *
     * @return boolean value.
     */
    @Override
    public int jumpDamage() {
        return 20;
    }

    /**
     * Method that adds new allowable actions and for the player to execute.
     * overrides allowableActions from Ground.
     *
     * @param actor     the Actor acting
     * @param location  the current Location
     * @param direction the direction of the Ground from the Actor
     * @return actions    An ActionList of allowable actions.
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();
        if (!location.containsAnActor() && actor.hasCapability(Status.POWERSTAREFFECT)) {
            actions.add(new PowerstarMovementAction(location, direction));
        } else if (!location.containsAnActor()) {
            actions.add(new JumpAction(actor, direction, this, location));
        }
        return actions;
    }

    /**
     * Method that checks if an actor can enter its location.
     *
     * @param actor the Actor to check
     * @return a boolean value indicating whether the actor can enter or not.
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        if (actor.hasCapability(Status.FLY)) {
            return true;
        } else {
            return false;
        }
    }
}
