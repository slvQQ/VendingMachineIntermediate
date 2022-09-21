package DataAccessObject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import DataTransferObject.Item;

public interface VendingMachineDao {

    int getItemInventory(String name) throws VendingMachinePersistenceException;

    void reduceOneItemFromInventory(String name) throws VendingMachinePersistenceException;

    Item getItem(String name) throws VendingMachinePersistenceException;

    Map<String, BigDecimal> getMapOfItemNamesInStockWithPrices() throws VendingMachinePersistenceException;

    List<Item> getAllItems() throws VendingMachinePersistenceException;

    Map<BigDecimal, BigDecimal> getChangePerCoin(Item item, BigDecimal money);
}
