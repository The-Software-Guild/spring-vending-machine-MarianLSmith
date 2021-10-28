package com.m3.c130db.marian.dao;

import com.m3.c130db.marian.dto.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineDaoFileImplTest {

    VendingMachineDao testDao;

    public VendingMachineDaoFileImplTest() {
    }


    @BeforeEach
    public void setUp() throws Exception {
        String testFile = "testinventory.txt";
        testDao = new VendingMachineDaoFileImpl(testFile);
    }

    @Test
    public void testGetAllItems() throws Exception {
        List<Item> itemListTest = testDao.listItems();

        assertNotNull(itemListTest, "The list of items must not be null.");
        assertEquals(3, itemListTest.size(), "Should have three items. ");
    }

    @Test
    public void testGetItem() throws Exception{
        Item getMalteasers = testDao.getItem("B3");
        List<Item> itemListTest = testDao.listItems();

        assertNotNull(getMalteasers, "List should retrieve malteasers (not null)");
        assertEquals(itemListTest.get(0), getMalteasers, "should be equal to malteasers as gotten from list");
    }
    @Test
    public void testUpdateQuantity() throws Exception {
        Item validItem = testDao.getItem("B3");
        int preTransQuantity = validItem.getRemainingQuantity();
        int postTransQuantity = preTransQuantity - 1;
        testDao.updateQuantity(validItem, true);
        int getPostTransQuantity = validItem.getRemainingQuantity();

        assertNotNull(postTransQuantity, "Inventory should not be null.");
        assertEquals(postTransQuantity, getPostTransQuantity,
                "Inventory after purchase should decrease by 1.");
    }

}