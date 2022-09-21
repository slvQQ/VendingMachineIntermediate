package UI;

import java.math.BigDecimal;
import java.util.Map;

public class VendingMachineView {

    private UserIO io;

    public VendingMachineView (UserIO io) {
        this.io = io;
    }

    public void displayMenuBanner() {
        io.print("--- Menu ---");
    }
    public BigDecimal getMoney() {
        return io.readBigDecimal("Enter the amount of money in dollars before selection");
    }

    public void displayMenu(Map<String, BigDecimal> itemsInStockWithPrices) {
        itemsInStockWithPrices.entrySet().forEach(entry ->{
            System.out.println(entry.getKey() + ": $" + entry.getValue());
        });
    }

    public String getItemSelection() {
        return io.readString("Select an item from the menu above or 'exit' to quit");
    }

    public void displayEnjoyBanner(String name) {
        io.print("Here is your change.");
        io.print("Enjoy your " + name + "!");
    }

    public void displayChangeDuePerCoin(Map<BigDecimal, BigDecimal> changeDuePerCoin) {
        changeDuePerCoin.entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + "c : " + entry.getValue());
        });
    }

    public void displayExitBanner() {
        io.print("See you later!");
    }

    public void displayUnknownCommandBanner() {
        io.print("UNKNOWN COMMAND");
    }

    public void displayErrorMessage (String errorMsg) {
        io.print("--- Error ---");
        io.print(errorMsg);
    }

    public void displaySelectAnotherMsg() {
        io.print("Please select something else.");
    }

}
