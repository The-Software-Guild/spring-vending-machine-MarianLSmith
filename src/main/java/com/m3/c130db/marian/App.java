package com.m3.c130db.marian;

import com.m3.c130db.marian.controller.VendingMachineController;
import com.m3.c130db.marian.dao.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) throws VendingMachinePersistenceException {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.m3.c130db.marian");
        appContext.refresh();

        VendingMachineController controller = appContext.getBean("vendingMachineController", VendingMachineController.class);
        controller.run();
    }
}
