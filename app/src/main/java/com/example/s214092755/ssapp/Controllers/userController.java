package com.example.s214092755.ssapp.Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.s214092755.ssapp.DatabaseHelper;
import com.example.s214092755.ssapp.Models.User;

/**
 * Created by s214092755 on 2017/05/19.
 */

public class userController {

    private DatabaseHelper mDBHelper;
    private Context context;
    private final String TABLE_NAME = "User";

    public userController(DatabaseHelper mDBHelper, Context context) {
        this.mDBHelper = mDBHelper;
        this.context = context;
    }

    public void addUser() {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
    /*    values.put("name", user.getName());
        values.put("surname",user.getSurname());
        values.put("contactNr",user.getContactNr());
        values.put("email", user.getEmail());
        values.put("address",user.getAddress());
        values.put("password", user.getPassword());

        //key is auto-generated
        values.put("recKey",user.getRecKey());*/

        values.put("name", "name");
        values.put("surname","surname");
        values.put("contactNr","contact");
        values.put("email", "email");
        values.put("address","address");
        values.put("password", "pass");

        //key is auto-generated
        values.put("recKey","rec");

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
}
