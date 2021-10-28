package com.m3.c130db.marian.dao;

import com.m3.c130db.marian.dto.Item;
import java.util.List;

public interface VendingMachineDao {

    List<Item> listItems() throws VendingMachinePersistenceException;

    Item getItem(String itemId) throws VendingMachinePersistenceException;


    Item updateQuantity(Item item, boolean transactionComplete) throws VendingMachinePersistenceException;

    Item editItems(String itemId, Item item) throws VendingMachinePersistenceException;

}
