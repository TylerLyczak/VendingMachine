package com.tylerlyczak.vendingmachine;

import com.tylerlyczak.vendingmachine.controller.VendingMachineController;
import com.tylerlyczak.vendingmachine.dao.VendingMachineDao;
import com.tylerlyczak.vendingmachine.dao.VendingMachineFileImpl;
import com.tylerlyczak.vendingmachine.service.VendingMachineService;
import com.tylerlyczak.vendingmachine.ui.UserIO;
import com.tylerlyczak.vendingmachine.ui.UserIOConsoleImpl;
import com.tylerlyczak.vendingmachine.ui.VendingMachineView;

public class App {

    public static void main(String[] args) {
        UserIO io = new UserIOConsoleImpl();
        VendingMachineView view = new VendingMachineView(io);
        VendingMachineDao dao = new VendingMachineFileImpl();
        VendingMachineService service = new VendingMachineService(dao);
        VendingMachineController controller = new VendingMachineController(view, service);
        controller.run();
    }
}
