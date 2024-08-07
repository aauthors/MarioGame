package game.items;

import edu.monash.fit2099.engine.items.Item;
import game.status.Status;

/**
 * Class for the Key Item that will unlock the Princess's handcuffs.
 * <p>
 * This class inherits from the abstract Item class.
 */
public class Key extends Item {

    /**
     * Constructor for the Key class.
     */
    public Key() {
        super("Key", 'k', true);
        addCapability(Status.KEY);
    }
}
