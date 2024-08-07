package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.Consumable;
import game.items.PowerStar;
import game.items.SuperMushroom;
import game.player.Player;
import game.status.Status;

/**
 * A special action used for when the actor consumes a special item
 */
public class ConsumableAction extends Action {

    private final Consumable item;

    /**
     * Constructor
     *
     * @param item: the item being consumed
     */
    public ConsumableAction(Consumable item) {

        this.item = item;
    }

    /**
     * This will identify the type of item picked up, and will added
     * the correct Capability respective to the item, and will then remove it from
     * the map
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a suitable description to display in the UI
     * @see Action#execute(Actor, GameMap)
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        item.consumptionEffect(map, actor);

        return menuDescription(actor);
    }

    /**
     * Describe the action in a format suitable for displaying in the menu.
     *
     * @param actor The actor performing the action.
     * @return a string, e.g. "Player picks up the rock"
     * @see Action#menuDescription(Actor)
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " consumes " + item;
    }


}

