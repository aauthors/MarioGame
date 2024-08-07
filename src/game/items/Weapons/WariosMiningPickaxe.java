package game.items.Weapons;

import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.items.Marketable;
import game.status.Status;

public class WariosMiningPickaxe extends WeaponItem {

    /**
     * Constructor.
     */
    public WariosMiningPickaxe() {
        super("Wario's Mining Pickaxe", '%', 35, "Picks", 85);
        addCapability(Status.WARIOSMININGPICKAXE);
        this.addCapability(Status.WEAPON);
    }


}
