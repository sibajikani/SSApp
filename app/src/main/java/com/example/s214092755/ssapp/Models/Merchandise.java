package com.example.s214092755.ssapp.Models;

import java.io.Serializable;

/**
 * Created by s214092755 on 2017/05/17.
 */

public class Merchandise extends Product implements Serializable{
    String type,size,colour;
    int onHand1;

    public Merchandise(String ID, String name, String manu, String picLink, double unitPrice,String colour, String type1, String size,  int onHand1) {
        super(ID, name, "merchandise", manu, picLink, unitPrice);
        this.type = type1;
        this.size = size;
        this.colour = colour;
        this.onHand1 = onHand1;
    }
    public String getType(){
        return "merchandise";
    }

    public String getTypeMerch() {
        return type;
    }


    public void setMerch(String type) {
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

    public int getOnHand1() {
        return onHand1;
    }

    public void setOnHand1(int onHand1) {
        this.onHand1 = onHand1;
    }
}
