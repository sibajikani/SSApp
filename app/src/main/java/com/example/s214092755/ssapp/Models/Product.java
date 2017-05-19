package com.example.s214092755.ssapp.Models;

/**
 * Created by s214092755 on 2017/05/17.
 */

public class Product {
    private String ID, name, type, manu,picLink;
    private int onHand;
    private double unitPrice;

    public Product(String ID, String name, String type, String manu, String picLink, int onHand, double unitPrice) {
        this.ID = ID;
        this.name = name;
        this.type = type;
        this.manu = manu;
        this.picLink = picLink;
        this.onHand = onHand;
        this.unitPrice = unitPrice;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManu() {
        return manu;
    }

    public void setManu(String manu) {
        this.manu = manu;
    }

    public String getPicLink() {
        return picLink;
    }

    public void setPicLink(String picLink) {
        this.picLink = picLink;
    }

    public int getOnHand() {
        return onHand;
    }

    public void setOnHand(int onHand) {
        this.onHand = onHand;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
