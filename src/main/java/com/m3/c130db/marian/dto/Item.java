package com.m3.c130db.marian.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Item {
    private final String itemId;
    private String name;
    private BigDecimal cost;
    private int remainingQuantity;

    public Item(String itemId) {
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return remainingQuantity == item.remainingQuantity && itemId.equals(item.itemId) && name.equals(item.name) && cost.equals(item.cost);
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId = " + itemId + '\'' +
                ", name = " + name + '\'' +
                ", cost = " + cost +
                ", remainingQuantity = " + remainingQuantity +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, name, cost, remainingQuantity);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(int remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }
}
