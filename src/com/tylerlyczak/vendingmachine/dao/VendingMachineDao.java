package com.tylerlyczak.vendingmachine.dao;

import com.tylerlyczak.vendingmachine.dto.VendingItem;

import java.util.List;

public interface VendingMachineDao {

    VendingItem addVendingItem(VendingItem item) throws VendingMachineException;

    VendingItem getVendingItem(int id) throws VendingMachineException;

    List<VendingItem> getAllVendingItems() throws VendingMachineException;

    VendingItem vendItem(int id) throws VendingMachineException;
}
