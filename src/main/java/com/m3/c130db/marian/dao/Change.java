package com.m3.c130db.marian.dao;

import java.math.BigDecimal;

public enum Change {
    TENPOUND(new BigDecimal("10"), "Ten Pound"),
    FIVEPOUND(new BigDecimal("5"), "Five Pound"),
    TWOPOUND(new BigDecimal("2"), "Two Pound"),
    ONEPOUND(new BigDecimal("1"), "One Pound"),
    FIFTYPENCE(new BigDecimal("0.5"), "Fifty Pence"),
    TWENTYPENCE(new BigDecimal("0.2"), "Twenty Pence"),
    TENPENCE(new BigDecimal("0.1"), "Ten Pence"),
    FIVEPENCE(new BigDecimal("0.05"), "Five Pence"),
    TWOPENCE(new BigDecimal("0.02"), "Two Pence"),
    ONEPENCE(new BigDecimal("0.01"), "One Pence");

    private final BigDecimal value;
    private final String changeName;


    Change(BigDecimal value, String changeName) {
        this.value = value;
        this.changeName = changeName;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getCoinName() {
        return changeName;
    }
}



