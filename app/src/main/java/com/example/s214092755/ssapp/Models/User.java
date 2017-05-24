package com.example.s214092755.ssapp.Models;

/**
 * Created by s214092755 on 2017/05/17.
 */

public class User {
    private String ID,name, surname, email, contactNr, address, password, recKey;

    public User(String ID, String name, String surname, String email, String contactNr, String address, String password, String recKey)
    {
        this.ID = ID;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.contactNr = contactNr;
        this.address = address;
        this.password = password;
        this.recKey = recKey;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNr() {
        return contactNr;
    }

    public void setContactNr(String contactNr) {
        this.contactNr = contactNr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRecKey() {
        return recKey;
    }

    public void setRecKey(String recKey) {
        this.recKey = recKey;
    }
}
