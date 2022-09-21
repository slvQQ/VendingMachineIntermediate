package Service;

import DataAccessObject.VendingMachineAuditDao;
import DataAccessObject.VendingMachineDao;
import DataAccessObject.VendingMachinePersistenceException;
import DataTransferObject.Change;
import DataTransferObject.Item;

import java.math.BigDecimal;
import java.util.Map;

public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer{

    private VendingMachineAuditDao auditDao;
    private VendingMachineDao dao;

    public VendingMachineServiceLayerImpl(VendingMachineAuditDao auditDao, VendingMachineDao dao) {
        this.auditDao = auditDao;
        this.dao = dao;
    }

    @Override
    public void checkIfEnoughMoney(Item item, BigDecimal inputMoney) throws InsufficientFundsException {

        if (item.getCost().compareTo(inputMoney)==1) {
            throw new InsufficientFundsException (
                    "ERROR: insufficient funds, you have only input "+ inputMoney);
        }
    }

    @Override
    public Map<String, BigDecimal> getItemsInStockWithPrices () throws VendingMachinePersistenceException{

        Map<String, BigDecimal> itemsInStockWithCosts = dao.getMapOfItemNamesInStockWithPrices();
        return itemsInStockWithCosts;
    }

    @Override
    public Map<BigDecimal, BigDecimal> getChangePerCoin(Item item, BigDecimal money) {
        Map<BigDecimal, BigDecimal> changeDuePerCoin = dao.getChangePerCoin(item, money);
        return changeDuePerCoin;
    }

    @Override
    public Item getItem(String name, BigDecimal inputMoney) throws InsufficientFundsException, NoItemInventoryException, VendingMachinePersistenceException {
        Item wantedItem = dao.getItem(name);

        if (wantedItem == null) {
            throw new NoItemInventoryException (
                    "ERROR: there are no " + name + "'s in the vending machine.");
        }

        checkIfEnoughMoney(wantedItem,inputMoney);

        removeOneItemFromInventory(name);
        return wantedItem;
    }

    public void removeOneItemFromInventory (String name) throws NoItemInventoryException, VendingMachinePersistenceException {

        if (dao.getItemInventory(name)>0) {
            dao.reduceOneItemFromInventory(name);

            auditDao.writeAuditEntry(" One " + name + " removed");
        } else {
            throw new NoItemInventoryException (
                    "ERROR: " + name + " is out of stock.");
        }
    }
}
