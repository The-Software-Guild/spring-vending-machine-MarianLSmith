package com.m3.c130db.marian.service;

public class VendingMachineQuantityValidationException extends Exception {

    public VendingMachineQuantityValidationException(String message) {
        super(message);
    }
    public VendingMachineQuantityValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}
