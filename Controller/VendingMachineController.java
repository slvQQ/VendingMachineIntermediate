package Controller;

import DataAccessObject.VendingMachinePersistenceException;
import DataTransferObject.Item;
import Service.InsufficientFundsException;
import Service.NoItemInventoryException;
import Service.VendingMachineServiceLayer;
import UI.UserIO;
import UI.UserIOConsoleImpl;
import UI.VendingMachineView;

import java.math.BigDecimal;
import java.util.Map;

public class VendingMachineController {
    private UserIO io = (UserIO) new UserIOConsoleImpl();
    private VendingMachineView view;
    private VendingMachineServiceLayer service;


    public VendingMachineController(VendingMachineView view, VendingMachineServiceLayer service) {
        this.view = view;
        this.service = service;
    }


    public void run() {
        boolean keepGoing = true;
        String itemSelection = "";
        BigDecimal inputMoney;
        view.displayMenuBanner();
        try {
            getMenu();
        } catch (VendingMachinePersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
        inputMoney = getMoney();
        while (keepGoing) {
            try {

                itemSelection = getItemSelection();


                if (itemSelection.equalsIgnoreCase("EXIT")) {
                    break;
                }
                getItem(itemSelection, inputMoney);
                keepGoing = false;

            } catch (InsufficientFundsException | NoItemInventoryException | VendingMachinePersistenceException e) {
                view.displayErrorMessage(e.getMessage());
                view.displaySelectAnotherMsg();
            }
        }
        exitMessage();

    }


    private void getMenu() throws VendingMachinePersistenceException {
        Map<String, BigDecimal> itemsInStockWithPrices = service.getItemsInStockWithPrices();
        view.displayMenu(itemsInStockWithPrices);
    }


    private BigDecimal getMoney() {
        return view.getMoney();
    }


    private String getItemSelection(){
        return view.getItemSelection();
    }


    private void exitMessage() {
        view.displayExitBanner();
    }

    private void getItem(String name, BigDecimal money) throws InsufficientFundsException, NoItemInventoryException, VendingMachinePersistenceException {
        Item wantedItem = service.getItem(name, money);
        Map<BigDecimal, BigDecimal> changeDuePerCoin = service.getChangePerCoin(wantedItem, money);
        view.displayChangeDuePerCoin(changeDuePerCoin);
        view.displayEnjoyBanner(name);
    }
}
