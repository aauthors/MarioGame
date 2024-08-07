package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.Wallet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Class for the Slot Machine Action, extends the class Actions
 * and implements the interface TradeAction.
 */
public class SlotMachineAction extends Action implements TradeAction {

    /**
     * class attribute that holds the slotPrice.
     */
    private int slotPrice;

    /**
     * Constructor for the SlotMachineAction
     *
     * @param newSlotPrice Takes in the price of the slot.
     */
    public SlotMachineAction(int newSlotPrice) {
        this.slotPrice = newSlotPrice;
    }

    /**
     * Method that executes the slot machine gambling action and
     * returns a formatted descriptive String.
     * <p>
     * This method utilises a try-catch statement to ensure that the
     * wallet can be retrieved and the balance reduced.
     * Then the slot machine is "rolled" and the icons are checked
     * and compared to see if the result is a win or a loss.
     * <p>
     * The winnings are then calculated if won and a formatted string is
     * returned, else a lose message is returned.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a formatted descriptive String.
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        Wallet wallet;
        try {
            wallet = getActorsWallet(actor);
            wallet.reduceBalance(this.slotPrice);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

        List<SlotElement> slotBar = new ArrayList<>();
        int slotBarLength = 3;
        boolean payLine = true;
        for (int i = 0; i < slotBarLength; i++) {
            slotBar.add(generateElement());
            if (!slotBar.get(0).equals(slotBar.get(i))) {
                payLine = false;
            }
        }

        String stringSlotBar = slotBar.stream().map(SlotElement -> SlotElement.symbol).collect(
                Collectors.joining("    "));
        String boarder = new String(new char[stringSlotBar.length() - 1]).replace("\0", "-");
        String outputMessage;

        if (payLine) {
            int winnings = this.slotPrice * slotBar.get(0).value;
            wallet.increaseBalance(winnings);
            outputMessage = "All symbols match! " + actor + " wins " + winnings;
            stringSlotBar = "\u001b[43m" + stringSlotBar + "\u001b[0m";
        } else {
            outputMessage = actor + " loses " + this.slotPrice;
        }
        return String.format("""
                +--%s--+
                |  %s  |
                +--%s--+
                %s""", boarder, stringSlotBar, boarder, outputMessage);
    }

    /**
     * method that returns a formatted string for the menu description
     *
     * @param actor The actor performing the action.
     * @return A formatted descriptive string for the action.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " gambles $" + this.slotPrice + " with the slot machine";
    }

    /**
     * A nested static class for the element of each slot.
     */
    private static class SlotElement {
        String symbol;
        int occurrences;
        int value;

        SlotElement(String symbol, int occurrences, int value) {
            this.symbol = symbol;
            this.occurrences = occurrences;
            this.value = value;
        }

        boolean equals(SlotElement element) {
            return element.symbol.equals(this.symbol);
        }
    }

    /**
     * Method that generates an element for each slot.
     *
     * @return a Slot Element for the slot machine.
     */
    private SlotElement generateElement() {
        List<SlotElement> elements = Arrays.asList(new SlotElement("🍄", 10, 4),
                new SlotElement("🐢", 4, 8), new SlotElement("🌟", 3, 25),
                new SlotElement("🐉", 2, 160), new SlotElement("👸", 1, 420));

        int totalWeight = 0;
        for (SlotElement e : elements) {
            totalWeight += e.occurrences;
        }

        int randomOccur = new Random().nextInt(totalWeight) + 1;
        int indx = 0;
        while (indx < elements.size() && randomOccur > elements.get(indx).occurrences) {
            randomOccur -= elements.get(indx).occurrences;
            indx++;
        }
        return elements.get(indx);
    }
}



