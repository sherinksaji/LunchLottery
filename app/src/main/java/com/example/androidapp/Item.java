package com.example.androidapp;

public class Item {


    public Item(String itemName, int quantity, String key) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.key = key;
    }
    public Item(){

    }
    private String itemName;
    private int quantity;
    private String key;

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemName='" + this.getItemName() + '\'' +
                ", quantity=" + this.getQuantity() +
                '}';
    }
}
