package game.items;

/**
 * Interface for Marktable items.
 */
public interface Marketable {

    /**
     * Method that gets the price of a marketable item.
     *
     * @return double value of the item
     */
    double getPrice();

    /**
     * method that sets the price of the marketable item.
     *
     * @param newPrice the new price of the marketable item.
     */
    void setPrice(double newPrice);

}
