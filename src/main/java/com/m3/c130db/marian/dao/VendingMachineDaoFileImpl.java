package com.m3.c130db.marian.dao;

import com.m3.c130db.marian.dto.Item;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

@Component
public class VendingMachineDaoFileImpl implements VendingMachineDao {

    private final String INVENTORY_FILE;
    public static final String DELIMITER = ":";

    public VendingMachineDaoFileImpl(){
        INVENTORY_FILE = "inventory.txt";
    }

    public VendingMachineDaoFileImpl(String inventoryTextFile){
        INVENTORY_FILE = inventoryTextFile;
    }

    private final Map<String, Item> items = new HashMap<>();

    @Override
    public List<Item> listItems() throws VendingMachinePersistenceException {
        loadInventory();
        List<Item> itemList = new ArrayList<>();
        for (Item item : items.values()){
            if(item.getRemainingQuantity() > 0){
                itemList.add(item);
            }
        }
        return new ArrayList(items.values());
    }

    @Override
    public Item getItem(String itemId) throws VendingMachinePersistenceException{
        loadInventory();
        return items.get(itemId);
    }

    @Override
    public Item updateQuantity(Item item, boolean transactionComplete) throws VendingMachinePersistenceException {
        loadInventory();
        int preTransQuantity = item.getRemainingQuantity();
        int postTransQuantity = preTransQuantity - 1;
        item.setRemainingQuantity(postTransQuantity);
        writeInventory();
        return item;
    }


    @Override
    public Item editItems(String itemId, Item item) throws VendingMachinePersistenceException {
        loadInventory();
        items.replace(itemId, item);
        writeInventory();

        return item;
    }

    private Item unmarshallItem(String itemAsText){
        String[] itemTokens = itemAsText.split(DELIMITER);
        String itemId = itemTokens[0];
        Item itemFromFile = new Item(itemId);
        itemFromFile.setName(itemTokens[1]);
        itemFromFile.setCost(new BigDecimal(itemTokens[2]));
        itemFromFile.setRemainingQuantity(Integer.parseInt(itemTokens[3]));
        return itemFromFile;
    }
    public void loadInventory() throws VendingMachinePersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(INVENTORY_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException(
            "Could not load inventory data into memory.", e);
        }
        String currentLine;
        Item currentItem;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentItem = unmarshallItem(currentLine);
                items.put(currentItem.getItemId(), currentItem);
        }
        scanner.close();
    }

    private String marshallItem(Item anItem){

        String itemAsText = anItem.getItemId() + DELIMITER;

        itemAsText += anItem.getName() + DELIMITER;

        itemAsText += anItem.getCost() + DELIMITER;

        itemAsText += anItem.getRemainingQuantity() + DELIMITER;

        return itemAsText;
    }

    private void writeInventory() throws VendingMachinePersistenceException {

        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(INVENTORY_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException(
                    "Could not save inventory data.", e);
        }

        String itemAsText;
        List<Item> itemList = this.listItems();
        for (Item currentItem: itemList) {
            itemAsText = marshallItem(currentItem);
            out.println(itemAsText);
            out.flush();
        }
        out.close();
    }
}
