package com.tylerlyczak.vendingmachine.ui;

import com.tylerlyczak.vendingmachine.dto.VendingItem;
import com.tylerlyczak.vendingmachine.service.Change;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class VendingMachineView {

    private final UserIO io;

    public VendingMachineView(UserIO io)    {this.io = io;}

    public int printMenuAndGetSelection()   {
        io.print("\nMain Menu");
        io.print("1. Vend an item");
        io.print("2. Deposit Money");
        io.print("3. Total Money");
        io.print("4. List all items");
        io.print("5. Exit");

        return io.readInt("Please select from the above choices.", 1, 5);
    }


    // List all items
    public void displayVendingItemList(List<VendingItem> vendingItems)   {
        for (VendingItem item : vendingItems)   {
            String str = "====================\n" +
                    "ID: " + item.getId() + "\n" +
                    "Item Name: " + item.getItemName() + "\n" +
                    "Cost: " + item.getCost() + "\n" +
                    "Number in inventory: " + item.getNumInventory() + "\n" +
                    "====================\n";
            io.print(str);
        }
    }

    public void bannerDisplayVendingItemList()  {io.print("=== Display Vending Items ===");}


    // Vend item
    public int displayItemsAndPick(List<VendingItem> vendingItems)  {
        displayVendingItemList(vendingItems);
        return io.readInt("Select an item to vend");
    }

    public void bannerDisplayItemsAndPick() {io.print("=== Vend an Item ===");}

    public void bannerSuccessfulItemsAndPick()  {io.print("Successfully vended item");}

    public void bannerUnsuccessfulItemsAndPick()    {io.print("Unsuccessfully vended item");}

    public void printChange(Map<Change, Integer> changeMap)   {
        io.print("=== Change ===");
        io.print("Quarters: " + changeMap.get(Change.QUARTER));
        io.print("Dimes: " + changeMap.get(Change.DIME));
        io.print("Nickels: " + changeMap.get(Change.NICKEL));
        io.print("Pennies: " + changeMap.get(Change.PENNY));
    }


    // Deposit Money
    public double displayDepositMoney() {
        return io.readDouble("How much money do you want to deposit?");
    }

    public void bannerDisplayDepositMoney() {io.print("=== Deposit Money ===");}


    // Show Money
    public void displayCurrentMoney(BigDecimal money)   {
        io.print("Current money: " + money.toString());
    }

    public void bannerDisplayCurrentMoney() {io.print("=== Current Money ===");}

    // Unknown
    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
}
