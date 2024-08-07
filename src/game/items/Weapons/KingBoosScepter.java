package game.items.Weapons;

import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.items.Marketable;
import game.status.Status;

/**
 * Class for the King Boo's Scepter
 *
 *
 * This class extends the WeaponItem class.
 */
public class KingBoosScepter extends WeaponItem {

    /**
     * Constructor.
     *
     */
    public KingBoosScepter() {
        super("King Boo's Scepter", '|', 45, "Cuts", 75);
        addCapability(Status.KINGBOOSSCEPTER);
        this.addCapability(Status.WEAPON);
    }

}
