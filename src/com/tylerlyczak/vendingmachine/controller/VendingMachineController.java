package com.tylerlyczak.vendingmachine.controller;

import com.tylerlyczak.vendingmachine.dao.VendingMachineException;
import com.tylerlyczak.vendingmachine.service.Change;
import com.tylerlyczak.vendingmachine.service.InsufficientFundsException;
import com.tylerlyczak.vendingmachine.service.NoItemInventoryException;
import com.tylerlyczak.vendingmachine.service.VendingMachineService;
import com.tylerlyczak.vendingmachine.ui.VendingMachineView;

import java.util.Map;

public class VendingMachineController {

    private final VendingMachineView view;
    private final VendingMachineService service;

    public VendingMachineController(VendingMachineView view, VendingMachineService service) {
        this.view = view;
        this.service = service;
    }

    public void run()   {
        boolean keepGoing = true;
        int menuSelection = 0;

        try {
            while (keepGoing)   {

                menuSelection = getMenuSelection();

                switch (menuSelection)  {
                    case 1:
                        vendItem();
                        break;
                    case 2:
                        depositMoney();
                        break;
                    case 3:
                        totalMoney();
                        break;
                    case 4:
                        listItems();
                        break;
                    case 5:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            }
            exitMessage();
        } catch (VendingMachineException | InsufficientFundsException | NoItemInventoryException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private int getMenuSelection()  {return view.printMenuAndGetSelection();}

    private void vendItem() throws VendingMachineException, InsufficientFundsException, NoItemInventoryException {
        view.bannerDisplayItemsAndPick();
        view.bannerDisplayVendingItemList();
        int id = view.displayItemsAndPick(service.getVendingItemList());
        Map<Change, Integer> changeMap = service.vendItem(id);
        view.printChange(changeMap);
    }

    private void depositMoney() {
        view.bannerDisplayDepositMoney();
        double money = view.displayDepositMoney();
        service.depositMoney(money);
    }

    private void totalMoney()   {
        view.bannerDisplayCurrentMoney();
        view.displayCurrentMoney(service.getMoney());
    }

    private void listItems() throws VendingMachineException {
        view.bannerDisplayVendingItemList();
        view.displayVendingItemList(service.getVendingItemList());
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }
}
