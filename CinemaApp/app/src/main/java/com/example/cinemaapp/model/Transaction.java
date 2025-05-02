package com.example.cinemaapp.model;


public class Transaction {
    private String transactionTime;
    private String site;
    private String itemName;
    private int quantity;

    public Transaction(String transactionTime, String site, String itemName, int quantity) {
        this.transactionTime = transactionTime;
        this.site = site;
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public String getSite() {
        return site;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }
}
