package com.example.ewallet;

public class Items {
    private String category;
    private int price;

    public Items(String category, int price){
        this.category = category;
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }
}
