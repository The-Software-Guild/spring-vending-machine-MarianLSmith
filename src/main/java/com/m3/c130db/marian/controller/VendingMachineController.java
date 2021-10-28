package com.m3.c130db.marian.controller;

import com.m3.c130db.marian.dao.Change;
import com.m3.c130db.marian.dao.VendingMachinePersistenceException;
import com.m3.c130db.marian.dto.Item;
import com.m3.c130db.marian.service.VendingMachineQuantityValidationException;
import com.m3.c130db.marian.service.VendingMachineServiceLayer;
import com.m3.c130db.marian.ui.VendingMachineView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class VendingMachineController {
    private final VendingMachineView view;
    private final VendingMachineServiceLayer service;

    @Autowired
    public VendingMachineController(VendingMachineView view, VendingMachineServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    public void run() throws VendingMachinePersistenceException {
        boolean keepGoing = true;
        int menuSelection = 0;
        while (keepGoing) {

            menuSelection = getMenuSelection();

            switch (menuSelection) {
                case 1:
                    listItems();
                    break;
                case 2:
                    try {
                        purchaseItem();
                    } catch (VendingMachineQuantityValidationException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    keepGoing = false;
                    break;
                default:
                    unknownCommand();
            }
        }
        exitMessage();
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }


    private void listItems() {
        List<Item> itemList = null;
        try {
            itemList = service.listItems();
        } catch (VendingMachinePersistenceException e) {
            e.printStackTrace();
        }

        view.displayItemList(itemList);
    }


    private void purchaseItem() throws VendingMachinePersistenceException, VendingMachineQuantityValidationException {
        String itemId = view.getItemIdChoice();
        Item item = service.getItem(itemId);
        int itemQuantity = item.getRemainingQuantity();
        while (itemQuantity <= 0) {
            view.displayIncorrectItemId();
            itemId = view.getItemIdChoice();
            itemQuantity = item.getRemainingQuantity();
        }

        boolean transactionComplete;
        BigDecimal insertedChange;
        do {
            insertedChange = new BigDecimal(view.getCash());
            transactionComplete = service.checkChange(item, insertedChange);
        } while (!transactionComplete);

        BigDecimal customerChange = service.calculateChange(item, insertedChange);
        view.transactionComplete(item);
        service.updateQuantity(item, transactionComplete);
        service.editItems(itemId, item);
        String entry = service.auditEntry(item);

        String changeString = service.changeObj(customerChange, Change.TENPOUND, Change.FIVEPOUND, Change.TWOPOUND,
                Change.ONEPOUND, Change.FIFTYPENCE, Change.TWENTYPENCE, Change.TENPENCE,
                Change.FIVEPENCE, Change.TWOPENCE, Change.ONEPENCE);

        view.displayChangeBreakdown(changeString);
        service.writeAuditEntry(entry);
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }
}
