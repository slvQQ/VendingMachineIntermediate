package DataAccessObject;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import DataTransferObject.Change;
import DataTransferObject.Item;

public class VendingMachineDaoFileImpl implements VendingMachineDao{

    private Map<String, Item> items = new HashMap<>();
    public static final String DELIMITER = "::";
    private final String VENDING_MACHINE_FILE;

    public VendingMachineDaoFileImpl() {
        VENDING_MACHINE_FILE = "VendingMachine.txt";
    }
    public VendingMachineDaoFileImpl(String testFile) {
        VENDING_MACHINE_FILE = testFile;
    }


    @Override
    public int getItemInventory(String name) throws VendingMachinePersistenceException {
        loadItem();
        return items.get(name).getInventory();
    }


    @Override
    public void reduceOneItemFromInventory(String name) throws VendingMachinePersistenceException {
        loadItem();
        int prevInventory = items.get(name).getInventory();
        items.get(name).setInventory(prevInventory-1);
        writeItem();
    }


    @Override
    public Item getItem(String name) throws VendingMachinePersistenceException {
        loadItem();
        return items.get(name);
    }


    @Override
    public Map<String, BigDecimal> getMapOfItemNamesInStockWithPrices() throws VendingMachinePersistenceException{
        loadItem();
        Map<String, BigDecimal> itemsInStockWithCosts = items.entrySet()
                .stream()
                .filter(map -> map.getValue().getInventory() > 0)
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue().getCost()));

        return itemsInStockWithCosts;

    }


    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        loadItem();
        return new ArrayList(items.values());
    }


    @Override
    public Map<BigDecimal, BigDecimal> getChangePerCoin(Item item, BigDecimal money) {
        BigDecimal itemCost = item.getCost();
        Map<BigDecimal, BigDecimal> changeDuePerCoin = Change.changeDuePerCoin(itemCost, money);
        return changeDuePerCoin;
    }


    private String marshallItem (Item anItem) {
        String itemAsText = anItem.getName() + DELIMITER;
        itemAsText += anItem.getCost() + DELIMITER;
        itemAsText += anItem.getInventory();
        return itemAsText;
    }


    private void writeItem() throws VendingMachinePersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(VENDING_MACHINE_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException("Could not save student data.", e);
        }
        String itemAsText;
        List <Item> itemList = this.getAllItems();
        for (Item currentItem : itemList) {
            itemAsText = marshallItem(currentItem);
            out.println(itemAsText);
            out.flush();
        }
        out.close();
    }


    private Item unmarshallItem (String itemAsText){

        String [] itemTokens = itemAsText.split("::");
        String name = itemTokens[0];
        Item itemFromFile = new Item(name);
        BigDecimal bigDecimal = new BigDecimal(itemTokens[1]);
        itemFromFile.setCost(bigDecimal);
        itemFromFile.setInventory(Integer.parseInt(itemTokens[2]));
        return itemFromFile;
    }


    private void loadItem() throws VendingMachinePersistenceException {
        Scanner s;

        try {

            s = new Scanner(
                    new BufferedReader(
                            new FileReader(VENDING_MACHINE_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException(
                    "Could not load item data into memory.", e);
        }
        String currentLine;
        Item currentItem;

        while (s.hasNextLine()) {
            currentLine = s.nextLine();
            currentItem = unmarshallItem(currentLine);
            items.put(currentItem.getName(), currentItem);
        }
        s.close();
    }
}
