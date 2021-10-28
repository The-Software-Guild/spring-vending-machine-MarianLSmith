package com.m3.c130db.marian.dao;

public interface VendingMachineAuditDao {

    void writeAuditEntry(String entry) throws VendingMachinePersistenceException;
}
