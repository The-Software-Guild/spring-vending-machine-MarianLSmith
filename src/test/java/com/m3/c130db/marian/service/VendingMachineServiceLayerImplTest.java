package com.m3.c130db.marian.service;

import com.m3.c130db.marian.dao.VendingMachineAuditDao;
import com.m3.c130db.marian.dao.VendingMachineDao;
import com.m3.c130db.marian.dao.VendingMachineDaoFileImpl;
import com.m3.c130db.marian.dto.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class VendingMachineServiceLayerImplTest {

    private VendingMachineServiceLayer service;

    private VendingMachineDao testDao;
    private VendingMachineAuditDao testAuditDao;
    private VendingMachineServiceLayer testService;

    public VendingMachineServiceLayerImplTest() {
        VendingMachineDao dao = new VendingMachineDaoStubImpl();
        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoStubImpl();

        service = new VendingMachineServiceLayerImpl(dao, auditDao);

    }
    @BeforeEach
    public void setUp() throws Exception {
    String testFile = "testinventory.txt";
    testDao = new VendingMachineDaoFileImpl(testFile);
    testService = new VendingMachineServiceLayerImpl(testDao, testAuditDao);
    }

    @Test
    public void testCheckChangeSufficient() throws Exception {
        List<Item> testItemList = testService.listItems();
        BigDecimal testInsertedCash = new BigDecimal ("10");
        boolean transactionComplete = testService.checkChange(testItemList.get(0), testInsertedCash);
        fail("Expected Exception not Thrown.");
        assertEquals(transactionComplete, true, "Cash inserted should be greater than cost of item");
    }


    @Test
    public void testCheckChangeInsufficient() throws Exception {
        List<Item> testItemList = testService.listItems();
        BigDecimal testInsertedCash = new BigDecimal ("0");
        boolean transactionComplete = testService.checkChange(testItemList.get(0), testInsertedCash);
        fail("Expected Exception not Thrown.");
        assertEquals(transactionComplete, false, "Cash inserted should be less than cost of item");
    }

    @Test
    public void testCalculateChange() throws Exception {
        List<Item> testItemList = testService.listItems();
        Item testItem = testItemList.get(0);
        BigDecimal testInsertedCash = new BigDecimal ("10");

        BigDecimal expectedChange = testInsertedCash.subtract(testItem.getCost());
        BigDecimal testCalculatedChange = service.calculateChange(testItem, testInsertedCash);

        fail("Expected Exception not Thrown.");
        assertEquals(testCalculatedChange, expectedChange, "Change returned should equal expected change");
    }
}