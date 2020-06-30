package com.example.veats;

public class CartData {
    String itemName;
    int price;
    int quantity;

    public CartData(String itemName, int price, int quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String item) {
        this.itemName = itemName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(String item) {
        this.quantity = quantity;
    }

}
