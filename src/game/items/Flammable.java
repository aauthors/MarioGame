package game.items;

/**
 * Interface for the ability to burn and damage.
 */
public interface Flammable {

    /**
     * Method that returns the value of damage
     * taken from burning.
     *
     * @return an integer value.
     */
    int burnDamage();

    /**
     * Method that returns the verb of the Fire's
     * damage.
     *
     * @return A string.
     */
    String verb();
}
