package game.ground;

import static game.status.Status.GAMBLEABLE;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.SlotMachineAction;

/**
 * Class for the Casino creative requirement idea, enables the
 * Player to experience the joys of gambling.
 */
public class SlotMachine extends Ground {

    /**
     * Class attribute that stores the prices of the slot.
     */
    private int slotPrice;

    /**
     * Constructor for the SlotMachine.
     *
     * @param newSlotPrice the price of the new slot.
     */
    public SlotMachine(int newSlotPrice) {
        super('⛽');
        this.slotPrice = newSlotPrice;
        this.addCapability(GAMBLEABLE);
    }

    /**
     * Method that checks if an actor can enter the
     * Ground's location.
     *
     * @param actor the Actor to check
     * @return a boolean value indicating whether the actor can enter or not.
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }

    /**
     * Method that returns an ActionList containing a list
     * of allowable actions that the Player can perform.
     *
     * @param actor     the Actor acting
     * @param location  the current Location
     * @param direction the direction of the Ground from the Actor
     * @return ActionList, a list of actions the Player can perform.
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();
        actions.add(new SlotMachineAction(slotPrice));
        return actions;
    }
}
