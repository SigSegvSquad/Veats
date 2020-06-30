package com.example.veats;

public class MenuData {
    int id;
    String item;
    int price;

    public MenuData(String item, int price, int id) {
        this.item = item;
        this.price = price;
        this.id= id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) { this.price = price; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
