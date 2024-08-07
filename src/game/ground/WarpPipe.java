package game.ground;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Jumpable;
import game.actions.JumpAction;
import game.actions.WarpAction;
import game.enemies.PiranhaPlant;
import game.reset.ResettableInstance;

/**
 * Class for the WarpPipe
 * <p>
 * This class extends from the class WarpDestination and implements
 * the interfaces Jumpable and ResettableInstance.
 * <p>
 * This class will enable the Player to teleport between two
 * preset destinations.
 */
public class WarpPipe extends WarpDestination implements Jumpable, ResettableInstance {

    /**
     * Class attribute for determining if the WarpPipe
     * can spawn an enemy Actor PiranhaPlant.
     */
    private boolean spawnPiranha = true;

    /**
     * Constructor for the WarpPipe class
     *
     * @param location the location of the WarpPipe.
     */
    public WarpPipe(Location location) {
        super('C', location);
        registerInstance();
    }

    /**
     * Method that creates and returns and ActionList
     * of the Actor's available actions.
     *
     * @param actor     the Actor acting
     * @param location  the current Location
     * @param direction the direction of the Ground from the Actor
     * @return An ActionList which contains Actions the the Actor can perform.
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();
        if (!location.containsAnActor()) {
            actions.add(new JumpAction(actor, direction, this, location));
        } else if (location.getActor().equals(actor)) {
            actions.add(new WarpAction(this));
        }
        return actions;
    }

    /**
     * Method that utilises the World's time to check if
     * it can spawn an enemy Actor, PiranhaPlant.
     *
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        if (spawnPiranha && !location.containsAnActor()) {
            location.addActor(new PiranhaPlant());
            spawnPiranha = false;
        }
    }

    /**
     * Method that determines the success of a JumpAction.
     *
     * @return a boolean value indicating whether the jump was
     * successful or not.
     */
    @Override
    public boolean jumpSuccess() {
        return true;
    }

    /**
     * Method that returns the amount of jump damage received when
     * jumping to the WarpPipe.
     *
     * @return 0 -- an integer value representing no damage taken.
     */
    @Override
    public int jumpDamage() {
        return 0;
    }

    /**
     * Method that determines whether and Actor
     * can enter the Ground's location or not.
     *
     * @param actor the Actor to check
     * @return A boolean value indicating whether an actor can enter or not.
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }

    /**
     * method that resets the instance
     *
     * @param gameMap the map the WarpPipe is on.
     */
    @Override
    public void resetInstance(GameMap gameMap) {
        this.spawnPiranha = true;
    }
}
