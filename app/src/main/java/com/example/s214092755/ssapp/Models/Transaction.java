package com.example.s214092755.ssapp.Models;

/**
 * Created by s214092755 on 2017/05/17.
 */

public class Transaction {
    String tID,pID, date;
    int quantity,processed;

    public Transaction(String tID, String pID, String date, int quantity, int processed) {
        this.tID = tID;
        this.pID = pID;
        this.date = date;
        this.quantity = quantity;
        this.processed = processed;
    }

    public String gettID() {
        return tID;
    }

    public void settID(String tID) {
        this.tID = tID;
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProcessed() {
        return processed;
    }

    public void setProcessed(int processed) {
        this.processed = processed;
    }
}
