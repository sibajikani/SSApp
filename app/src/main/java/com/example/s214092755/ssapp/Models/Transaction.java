package com.example.s214092755.ssapp.Models;

import java.io.Serializable;

/**
 * Created by s214092755 on 2017/05/17.
 */

public class Transaction implements Serializable{
    private String pID;
    private String date;
    private int quantity,processed;

    public Transaction(String pID, int quantity, int processed)
    {
        this.pID = pID;
        this.quantity = quantity;
        this.processed = processed;
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
