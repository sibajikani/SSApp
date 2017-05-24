package com.example.s214092755.ssapp.Models;

import java.io.Serializable;

/**
 * Created by s214092755 on 2017/05/17.
 */

public class Transaction implements Serializable{
    private String pID,userID;
    private String date;
    private String type;
    private int quantity,processed;

    public Transaction(String pID, String userID, int quantity, int processed, String type, String date)
    {
        this.pID = pID;
        this.userID = userID;
        this.quantity = quantity;
        this.processed = processed;
        this.type = type;
        this.date = date;
    }

    public String getUserID() {
        return userID;
    }

    public String getpID() {
        return pID;
    }

    public String getType() { return type ;}

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
