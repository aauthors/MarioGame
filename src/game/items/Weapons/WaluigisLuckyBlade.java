
package game.items.Weapons;

import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.items.Marketable;
import game.status.Status;

public class WaluigisLuckyBlade extends WeaponItem  {
    /**
     * Constructor.
     *
     */
    private double toadPrice = 3000;
    public WaluigisLuckyBlade() {
        super("Waluigi's Lucky Blade", '/', 55, "Slashes", 95);
        addCapability(Status.WALUIGISLUCKYBLADE);
        this.addCapability(Status.WEAPON);
    }


}
