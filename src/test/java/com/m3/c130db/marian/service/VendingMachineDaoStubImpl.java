package com.m3.c130db.marian.service;

import com.m3.c130db.marian.dao.VendingMachineDao;
import com.m3.c130db.marian.dao.VendingMachinePersistenceException;
import com.m3.c130db.marian.dto.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VendingMachineDaoStubImpl implements VendingMachineDao {

    public Item onlyItem;

    public VendingMachineDaoStubImpl() {
        onlyItem = new Item("T1");
        onlyItem.setName("Walkers");
        onlyItem.setCost(new BigDecimal("0.72"));
        onlyItem.setRemainingQuantity(18);
    }

    public VendingMachineDaoStubImpl(Item testItem) {
        this.onlyItem = testItem;
    }


    @Override
    public List<Item> listItems() throws VendingMachinePersistenceException {
        List<Item> itemList = new ArrayList<>();
        itemList.add(onlyItem);
        return itemList;
    }


    @Override
    public Item getItem(String itemId) throws VendingMachinePersistenceException {
        if (itemId.equals((onlyItem.getItemId()))) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public Item updateQuantity(Item item, boolean transactionComplete) throws VendingMachinePersistenceException {
        String itemId = item.getItemId();
        if (itemId.equals(onlyItem.getItemId())) {
            return onlyItem;
        } else {
            return null;
        }
    }
    @Override
    public Item editItems(String itemId, Item item) throws VendingMachinePersistenceException {
        if (itemId.equals(onlyItem.getItemId())) {
            return onlyItem;
        } else {
            return null;
        }
    }
}
