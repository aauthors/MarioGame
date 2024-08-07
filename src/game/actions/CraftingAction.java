package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.status.Status;

public class CraftingAction extends Action {
    Item weapon;
    public CraftingAction(Item weapon){
        this.weapon = weapon;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        boolean hasLeather = false,hasShell = false ,hasBark = false;
        Item leather = null ,shell = null ,bark = null;

        for(Item item : actor.getInventory()){
            if(item.hasCapability(Status.TREEBARK)){
                hasBark = true;
                bark = item;
            }
            else if(item.hasCapability(Status.SHELLFRAGMENT)){
                hasShell = true;
                shell = item;
            }
            else if(item.hasCapability(Status.GOOMBALEATHER)){
                hasLeather = true;
                leather = item;
            }

        }
        if(hasLeather && hasShell && hasBark){
            actor.addItemToInventory(weapon);
            actor.removeItemFromInventory(bark);
            actor.removeItemFromInventory(shell);
            actor.removeItemFromInventory(leather);

        }
        else{
            return actor + " lacks the required items";
        }
        return menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " crafts " + weapon;
    }
}
