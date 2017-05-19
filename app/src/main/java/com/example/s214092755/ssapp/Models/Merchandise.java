package com.example.s214092755.ssapp.Models;

import java.io.Serializable;

/**
 * Created by s214092755 on 2017/05/17.
 */

public class Merchandise extends Product implements Serializable{
    String type,size,colour;
    int onHand;

    public Merchandise(String ID, String name, String type, String manu, String picLink, int onHand, double unitPrice, String type1, String size, String colour, int onHand1) {
        super(ID, name, type, manu, picLink, onHand, unitPrice);
        this.type = type1;
        this.size = size;
        this.colour = colour;
        this.onHand = onHand1;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public int getOnHand() {
        return onHand;
    }

    @Override
    public void setOnHand(int onHand) {
        this.onHand = onHand;
    }
}
