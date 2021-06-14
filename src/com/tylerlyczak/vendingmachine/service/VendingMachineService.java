package com.tylerlyczak.vendingmachine.service;

import com.tylerlyczak.vendingmachine.dao.VendingMachineDao;
import com.tylerlyczak.vendingmachine.dao.VendingMachineException;
import com.tylerlyczak.vendingmachine.dto.VendingItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VendingMachineService {

    private final VendingMachineDao dao;
    private BigDecimal money;

    public VendingMachineService(VendingMachineDao dao) {
        this.dao = dao;
        money = new BigDecimal("0.00");
        money = money.setScale(2, RoundingMode.HALF_UP);
    }

    public List<VendingItem> getVendingItemList()  throws VendingMachineException {
        return this.dao.getAllVendingItems();
    }

    private Map<Change, Integer> calculateChange(BigDecimal result) {
        Map<Change, Integer> returnMap = new HashMap<>();
        returnMap.put(Change.QUARTER, 0);
        returnMap.put(Change.DIME, 0);
        returnMap.put(Change.NICKEL, 0);
        returnMap.put(Change.PENNY, 0);

        // Calculate the amount of quarters
        while (result.divide(BigDecimal.valueOf(0.25), RoundingMode.HALF_UP).compareTo(BigDecimal.valueOf(0.25)) > 0)   {
            int num = returnMap.get(Change.QUARTER);
            num++;
            returnMap.remove(Change.QUARTER);
            returnMap.put(Change.QUARTER, num);
            result = result.subtract(BigDecimal.valueOf(0.25));
        }
        // Calculate the amount of dimes
        while (result.divide(BigDecimal.valueOf(0.10), RoundingMode.HALF_UP).compareTo(BigDecimal.valueOf(0.10)) > 0)   {
            int num = returnMap.get(Change.DIME);
            num++;
            returnMap.remove(Change.DIME);
            returnMap.put(Change.DIME, num);
            result = result.subtract(BigDecimal.valueOf(0.10));
        }
        // Calculate the amount of nickels
        while (result.divide(BigDecimal.valueOf(0.05), RoundingMode.HALF_UP).compareTo(BigDecimal.valueOf(0.05)) > 0)   {
            int num = returnMap.get(Change.NICKEL);
            num++;
            returnMap.remove(Change.NICKEL);
            returnMap.put(Change.NICKEL, num);
            result = result.subtract(BigDecimal.valueOf(0.05));
        }
        // Calculate the amount of pennies
        while (result.divide(BigDecimal.valueOf(0.01), RoundingMode.HALF_UP).compareTo(BigDecimal.valueOf(0.01)) >= 0)   {
            int num = returnMap.get(Change.PENNY);
            num++;
            returnMap.remove(Change.PENNY);
            returnMap.put(Change.PENNY, num);
            result = result.subtract(BigDecimal.valueOf(0.01));
        }


        return returnMap;
    }

    public Map<Change, Integer> vendItem(int id) throws VendingMachineException, InsufficientFundsException, NoItemInventoryException{
        VendingItem item = dao.getVendingItem(id);

        if (item.getNumInventory() == 0)    {
            throw new NoItemInventoryException("No items left");
        }

        if (money.compareTo(item.getCost()) < 0)  {
            throw new InsufficientFundsException("Not enough funds, please add more");
        }
        VendingItem vendingItem = this.dao.vendItem(id);
        BigDecimal returnMoney = this.money.subtract(vendingItem.getCost());
        this.money = new BigDecimal("0.00");
        this.money = this.money.setScale(2, RoundingMode.HALF_UP);

        return calculateChange(returnMoney);
    }

    public void depositMoney(double money) {
        BigDecimal newMoney = new BigDecimal(money);
        newMoney = newMoney.setScale(2, RoundingMode.HALF_UP);
        this.money = this.money.add(newMoney);
    }

    public BigDecimal getMoney()    {return money;}
}
