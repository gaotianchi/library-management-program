package com.gaotianchi.database.models;

public class BookStock {
    public String title;
    public int quantity;
    public int costPrice;
    public int salePrice;
    public int saleQuantity;

    public BookStock(String title, int quantity, int costPrice, int salePrice, int saleQuantity) {
        this.title = title;
        this.quantity = quantity;
        this.costPrice = costPrice;
        this.salePrice = salePrice;
        this.saleQuantity = saleQuantity;
    }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getCostPrice() { return costPrice; }
    public void setCostPrice(int costPrice) { this.costPrice = costPrice; }

    public int getSalePrice() { return salePrice; }
    public void setSalePrice(int salePrice) { this.salePrice = salePrice; }

    public int getSaleQuantity() { return saleQuantity; }
    public void setSaleQuantity(int saleQuantity) { this.saleQuantity = saleQuantity; }
}
