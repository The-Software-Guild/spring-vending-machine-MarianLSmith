package com.m3.c130db.marian.service;

import com.m3.c130db.marian.dao.Change;
import com.m3.c130db.marian.dao.VendingMachinePersistenceException;
import com.m3.c130db.marian.dto.Item;

import java.math.BigDecimal;
import java.util.List;

public interface VendingMachineServiceLayer {

    void writeAuditEntry(String entry) throws VendingMachinePersistenceException;


    List<Item> listItems() throws VendingMachinePersistenceException;


    Item getItem(String itemId) throws VendingMachinePersistenceException;


    Item updateQuantity(Item item, boolean transactionComplete) throws VendingMachinePersistenceException, VendingMachineQuantityValidationException;

    void editItems(String itemId, Item item) throws VendingMachinePersistenceException;

    String auditEntry(Item item);

    boolean checkChange(Item item, BigDecimal customerChange);

    BigDecimal calculateChange(Item item, BigDecimal insertedChange);

    String changeObj(BigDecimal customerChange,
                     Change... changeList);

}
