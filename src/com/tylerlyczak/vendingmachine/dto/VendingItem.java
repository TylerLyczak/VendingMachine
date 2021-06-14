package com.tylerlyczak.vendingmachine.dto;

import java.math.BigDecimal;

public class VendingItem {

    private int id;
    private String itemName;
    private BigDecimal cost;
    private int numInventory;

    public VendingItem(int id, String itemName, BigDecimal cost, int numInventory)  {
        this.id = id;
        this.itemName = itemName;
        this.cost = cost;
        this.numInventory = numInventory;
    }

    public int getId()                              {return id;}
    public void setId(int id)                       {this.id = id;}

    public String getItemName()                     {return itemName;}
    public void setItemName(String itemName)        {this.itemName = itemName;}

    public BigDecimal getCost()                     {return cost;}
    public void setCost(BigDecimal cost)            {this.cost = cost;}

    public int getNumInventory()                    {return numInventory;}
    public void setNumInventory(int numInventory)   {this.numInventory = numInventory;}
}
