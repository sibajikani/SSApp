package com.example.s214092755.ssapp.Models;

import java.io.Serializable;

/**
 * Created by s214092755 on 2017/05/17.
 */

public class Supplement extends Product implements Serializable {
    String flavour, typeSup,size;
    int onHandSup;

    public Supplement(String ID, String name, String manu, String picLink, double unitPrice, String flavour, String typeSup, String size, int onHandSup)
    {
        super(ID, name, "supplement", manu, picLink,unitPrice);
        this.flavour = flavour;
        this.typeSup = typeSup;
        this.size = size;
        this.onHandSup = onHandSup;
    }

    public String getType(){
        return "supplement";
    }

    public String getFlavour() {
        return flavour;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    public String getTypeSup() {
        return typeSup;
    }

    public void setTypeSup(String typeSup) {
        this.typeSup = typeSup;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getOnHandSup() {
        return onHandSup;
    }

    public void setOnHandSup(int onHandSup) {
        this.onHandSup = onHandSup;
    }
}
