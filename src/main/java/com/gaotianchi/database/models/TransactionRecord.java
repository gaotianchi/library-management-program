package com.gaotianchi.database.models;

public class TransactionRecord {
    private String title;
    private String timestamp;
    private int quantity;
    private int profit;

    public TransactionRecord(String title, String timestamp, int quantity, int profit) {
        this.title = title;
        this.timestamp = timestamp;
        this.quantity = quantity;
        this.profit = profit;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }
}
