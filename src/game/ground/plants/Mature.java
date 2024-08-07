package game.ground.plants;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Jumpable;
import game.actions.JumpAction;
import game.actions.PowerstarMovementAction;
import game.enemies.FlyingKoopa;
import game.enemies.Koopa;
import game.ground.Dirt;
import game.items.WeaponPart.TreeBark;
import game.status.Status;

import java.util.List;
import java.util.Random;

/**
 * A class used to represent a Mature tree
 */
public class Mature extends Ground implements Tree, Jumpable {

    /**
     * Integer representing the age of the Mature
     */
    private int age;

    /**
     * Random variable used for calculating the chance to
     * spawn a Koopa enemy in the tick method.
     */
    private Random rand = new Random();

    /**
     * Constructor
     */
    public Mature() {
        super('T');
        age = 0;
        this.addCapability(Status.TREE);
        registerClass();
    }

    /**
     * Ground can also experience the joy of time.
     * This method will tick for each turn made within the game, and will perform the Mature's
     * life cycle, including its death, the spawning of actors, and the spawning of new sprouts
     *
     * @param location The location of the Ground
     * @see Ground#tick(Location)
     */
    @Override
    public void tick(Location location) {

        age++;
        if (rand.nextInt(10) < 8) {
            if (rand.nextInt(100) < 5) {
                location.addItem(new TreeBark());
            }

            spawnKoopa(location);
            spawnFlyingKoopa(location);
            spawnSprout(location);

        } else {
            location.setGround(new Dirt());
        }
    }

    /**
     * Method that checks the surrounding ground type
     * and spawns a sprout if able to.
     *
     * @param location the location of the Mature tree.
     */
    private void spawnSprout(Location location) {
        if (age % 5 == 0) {
            List<Exit> exits = location.getExits();
            boolean dirtFound = false;
            boolean dirtExists = false;
            int i;
            for (i = 0; i < exits.size(); i++) {
                if (exits.get(i).getDestination().getGround().hasCapability(Status.DIRT)) {
                    dirtFound = true;
                    break;
                }
            }
            if (dirtFound) {
                while (!dirtExists) {
                    i = rand.nextInt(location.getExits().size());
                    if (exits.get(i).getDestination().getGround().hasCapability(Status.DIRT)) {
                        exits.get(i).getDestination().setGround(new Sprout());
                        dirtExists = true;
                    }
                }
            }
        }
    }

    /**
     * Method that spawns flying koopa enemy
     *
     * @param location the location of the Mature tree
     *                 that is spawning the actor.
     */
    private void spawnFlyingKoopa(Location location) {
        if (rand.nextDouble() < 0.5) {
            if (!location.containsAnActor()) {
                location.addActor(new FlyingKoopa());
            }
        }
    }

    /**
     * Method that spawns Koopa
     *
     * @param location the location of the Mature tree
     *                 that is spawning the actor.
     */
    private void spawnKoopa(Location location) {
        if (rand.nextInt(100) < 15) {
            if (!location.containsAnActor()) {
                location.addActor(new Koopa());
            }
        }
    }

    /**
     * Method that indicates whether a jump is successful
     * or not.
     *
     * @return boolean -- returns a boolean value that indicates
     * the success of a jump action.
     */
    @Override
    public boolean jumpSuccess() {
        Random rand = new Random();
        // success chance is 70% for sprout
        return !(rand.nextDouble() < 0.3);
    }

    /**
     * Method that returns the amount of damage
     * to be received if the jump action fails.
     *
     * @return integer -- 30.
     */
    @Override
    public int jumpDamage() {
        return 30;
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
     * @return a boolean value indicating whether the actor
     * can enter or not.
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
