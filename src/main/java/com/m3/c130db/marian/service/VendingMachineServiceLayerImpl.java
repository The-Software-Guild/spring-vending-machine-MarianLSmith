package com.m3.c130db.marian.service;

import com.m3.c130db.marian.dao.Change;
import com.m3.c130db.marian.dao.VendingMachineAuditDao;
import com.m3.c130db.marian.dao.VendingMachineDao;
import com.m3.c130db.marian.dao.VendingMachinePersistenceException;
import com.m3.c130db.marian.dto.Item;
import com.m3.c130db.marian.ui.VendingMachineView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {

    VendingMachineDao dao;
    private final VendingMachineAuditDao auditDao;

@Autowired
    public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    @Override
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException {
        auditDao.writeAuditEntry(entry);
    }
    @Override
    public List<Item> listItems() throws VendingMachinePersistenceException {
        return dao.listItems();
    }


    @Override
    public Item getItem(String itemId) throws VendingMachinePersistenceException {
        return dao.getItem(itemId);
    }

    @Override
    public Item updateQuantity(Item item, boolean transactionComplete) throws VendingMachinePersistenceException {
        return dao.updateQuantity(item, transactionComplete);
    }

    @Override
    public void editItems(String itemId, Item item) throws VendingMachinePersistenceException {
        dao.editItems(itemId, item);
    }

    public boolean checkChange(Item item, BigDecimal customerChange){
        BigDecimal itemCost = item.getCost();
        boolean transactionComplete = itemCost.compareTo(customerChange) <= 0;
        return transactionComplete;
    }

    public BigDecimal calculateChange(Item item, BigDecimal insertedChange){
        BigDecimal customerChange = insertedChange.subtract(item.getCost());
        return customerChange;
    }

    public String changeObj (BigDecimal customerChange,
                                 Change ... changeList) {
        String changeString = "";
        for (Change change : changeList) {
            BigDecimal nChange = customerChange.divide(change.getValue(), 0, RoundingMode.DOWN);
            customerChange = customerChange.remainder(change.getValue());
            changeString = changeString + "\n" + change.getCoinName() + " = " + nChange;
        }
        return changeString;
    }

    public String auditEntry(Item item){
       String entry = "Item " + item.getItemId() + " " + item.getName()
                + " Chosen. " + "Item cost: " + item.getCost() + ". Remaining Quantity: " + item.getRemainingQuantity();
       return entry;
    }

}

