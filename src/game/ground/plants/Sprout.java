package game.ground.plants;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Jumpable;
import game.actions.JumpAction;
import game.actions.PowerstarMovementAction;
import game.enemies.Goomba;
import game.items.WeaponPart.TreeBark;
import game.status.Status;

import java.util.Random;

/**
 * Class for Sprout, a small plant/tree object.
 * <p>
 * REQ2: Jump Up Super Star!
 * --   Sprout is a jumpable terrain so it implements the
 * Jumpable interface which indicates that it can be
 * jumped on.
 * <p>
 * REQ3: Enemies
 * --   Sprout can spawn an enemy - Goomba.
 */
public class Sprout extends Ground implements Tree, Jumpable {

    /**
     * Integer value representing how old the Sprout is.
     * allows it to evolve to become a Sapling when it reaches
     * the right age.
     */
    private int age;

    /**
     * Random variable that allows us to calculate
     * the randomness of spawning an enemy - Goomba.
     */
    private Random rand = new Random();


    /**
     * Constructor.
     * <p>
     * Creates a Sprout object that can grow into a Sapling.
     */
    public Sprout() {
        super('+');
        age = 0;
        this.addCapability(Status.TREE);
        registerClass();
        // give sprout a height status
        this.addCapability(Status.HIGH);
        this.addCapability(Status.SPROUTTYPE);  // use to indicate sprout type terrain to calculate success of jump and dmg.
    }

    /**
     * Ground can also experience the joy of time.
     * This method will tick for each turn made within the game, and will perform the Sprout's life cycle,
     * including the actors its spawns, and its growth into a Sapling
     *
     * @param location The location of the Ground
     * @see Ground#tick(Location)
     */
    @Override
    public void tick(Location location) {
        age++;
        if (age != 10) {
            spawnGoomba(location);
        } else {
            location.setGround(new Sapling());
        }

    }

    /**
     * Method that spawns the enemy Goomba from the Sprout's
     * location.
     *
     * @param location the location of the Sprout.
     */
    private void spawnGoomba(Location location) {
        if (rand.nextInt(10) < 1) {
            if (!location.containsAnActor()) {
                location.addActor(new Goomba());
            }
        } else {
            location.setGround(new Sapling());
        }

    }

    /**
     * Method that determines whether an Actor can enter
     * the Ground of the Sprout.
     * <p>
     * Overridden method from Ground.
     *
     * @param actor the Actor to check
     * @return boolean -- returns true or false, indicating whether
     * the ground is accessible to the actor or not.
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        if (actor.hasCapability(Status.FLY) || actor.hasCapability(Status.UNJUMPABLE)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * method that indicates if the jump was successful
     *
     * @return boolean value, true if jump was made successfully false otherwise.
     */
    @Override
    public boolean jumpSuccess() {
        Random rand = new Random();
        // success chance is 90% for sprout
        return !(rand.nextDouble() < 0.1);
    }

    /**
     * method that returns the damage taken if a jump was
     * unsuccessful
     *
     * @return integer 10 - the amount of damage the actor will receive
     * if the jump was failed.
     */
    @Override
    public int jumpDamage() {
        return 10;
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

}
