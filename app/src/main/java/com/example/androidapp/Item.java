package com.example.androidapp;

public class Item {


    public Item(String itemName, int quantity, String telegramHandle) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.telegramHandle=telegramHandle;
    }
    public Item(){

    }
    private String itemName;
    private int quantity;


    public String getTelegramHandle() {
        return telegramHandle;
    }

    public void setTelegramHandle(String telegramHandle) {
        this.telegramHandle = telegramHandle;
    }

    private String telegramHandle;

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }


    @Override
    public String toString() {
        return "Item{" +
                "itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", telegramHandle='" + telegramHandle + '\'' +
                '}';
    }
}
