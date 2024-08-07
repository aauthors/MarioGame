package game.ground;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.CraftingAction;
import game.items.Weapons.KingBoosScepter;
import game.items.Weapons.WaluigisLuckyBlade;
import game.items.Weapons.WariosMiningPickaxe;

public class Anvil extends Ground {
    /**
     * Constructor.
     */
    public Anvil() {
        super('=');
    }

    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();
        actions.add(new CraftingAction(new KingBoosScepter()));
        actions.add(new CraftingAction(new WaluigisLuckyBlade()));
        actions.add(new CraftingAction(new WariosMiningPickaxe()));
        return actions;
    }

    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }
}
