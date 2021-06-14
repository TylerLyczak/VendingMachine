package com.tylerlyczak.vendingmachine.dao;

import com.tylerlyczak.vendingmachine.dto.VendingItem;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class VendingMachineFileImpl implements VendingMachineDao {

    private final Map<Integer, VendingItem> vendingItemMap = new HashMap<>();
    public static final String VENDING_MACHINE_FILE = "vendingitems.txt";
    public static final String DELIMITER = "::";

    @Override
    public VendingItem addVendingItem(VendingItem item) throws VendingMachineException {
        loadMachine();
        VendingItem newItem = vendingItemMap.put(item.getId(), item);
        writeMachine();
        return newItem;
    }

    @Override
    public VendingItem getVendingItem (int id) throws VendingMachineException {
        loadMachine();
        writeMachine();
        return vendingItemMap.get(id);
    }

    @Override
    public List<VendingItem> getAllVendingItems() throws VendingMachineException {
        loadMachine();
        return new ArrayList<>(vendingItemMap.values());
    }

    @Override
    public VendingItem vendItem(int id) throws VendingMachineException {
        loadMachine();
        VendingItem item = vendingItemMap.get(id);
        item.setNumInventory(item.getNumInventory() - 1);
        writeMachine();
        return item;
    }

    private VendingItem unmarshallVendingItem (String vendingItemStr) {
        /*
            String is in format of:
                id::itemName::cost::numInventory
                    if = 0
                    itemName = 1
                    cost = 2
                    numInventory = 3
         */
        String[] itemTokens = vendingItemStr.split(DELIMITER);
        BigDecimal temp = new BigDecimal(itemTokens[2]);
        temp = temp.setScale(2, RoundingMode.HALF_UP);

        return new VendingItem(
                Integer.parseInt(itemTokens[0]),
                itemTokens[1],
                temp,
                Integer.parseInt(itemTokens[3])
        );
    }

    private void loadMachine() throws VendingMachineException {
        // Declare a scanner before try-catch block
        Scanner scanner;

        // Try to instantiate the scanner, throw error if it occurs
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(VENDING_MACHINE_FILE)));
        } catch (FileNotFoundException e)   {
            throw new VendingMachineException("Could not load file", e);
        }

        String line;
        VendingItem item;

        // Loop through the file
        while (scanner.hasNextLine())   {
            line = scanner.nextLine();
            item = unmarshallVendingItem(line);

            vendingItemMap.put(item.getId(), item);
        }

        // Close the scanner
        scanner.close();
    }

    private String marshallVendingItem (VendingItem item)    {
        /*
            String is in format of :
                id::itemName::cost::numInventory
        */
        return item.getId() + DELIMITER + item.getItemName() + DELIMITER + item.getCost() + DELIMITER + item.getNumInventory();
    }

    private void writeMachine() throws VendingMachineException {
        // Declare the PrintWriter to use after instantiation
        PrintWriter out;

        // Try to instantiate the PrintWrite
        try {
            out = new PrintWriter(new FileWriter(VENDING_MACHINE_FILE));
        } catch (IOException e) {
            throw new VendingMachineException("Could not load file", e);
        }

        List<VendingItem> vendingItems = this.getAllVendingItems();
        // Loop over all the vending items in the current library
        for (VendingItem item : vendingItems)    {
            out.println(marshallVendingItem(item));
            out.flush();
        }
        // Close PrintWriter
        out.close();
    }
}
