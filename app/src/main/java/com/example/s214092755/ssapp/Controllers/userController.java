package com.example.s214092755.ssapp.Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

    public void addUser(User user) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("surname",user.getSurname());
        values.put("contactNr",user.getContactNr());
        values.put("email", user.getEmail());
        values.put("address",user.getAddress());
        values.put("password", user.getPassword());

        //key is auto-generated
        values.put("recKey",user.getRecKey());


        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public boolean checkUserExists(String email){
        // array of columns to fetch
        String[] columns = {
                "userID"
        };
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        // selection criteria
        String selection = "email" + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query("User", //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        return cursorCount > 0;

    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public User checkLogin(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                "userID",
                "email",
                "name",
                "surname",
                "contactNr",
                "address"
        };
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        // selection criteria
        String selection = "email" + " = ?" + " AND " + "password" + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        Cursor cursor = db.query("User", //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();
        if (cursorCount > 0) {
            cursor.moveToFirst();
            int id = cursor.getInt(cursor.getColumnIndex("userID"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String email1 = cursor.getString(cursor.getColumnIndex("email"));
            String surname = cursor.getString(cursor.getColumnIndex("surname"));
            String contactNr = cursor.getString(cursor.getColumnIndex("contactNr"));
            String address = cursor.getString(cursor.getColumnIndex("address"));
            return new User(Integer.toString(id),name,surname,email1,contactNr,address,"","");
        }

        cursor.close();
        db.close();

        return null;
    }

    public User checkRecKey(String recKey){
        // array of columns to fetch
        String[] columns = {
                "userID",
                "email",
                "name",
                "surname",
                "contactNr",
                "address"
        };
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        // selection criteria
        String selection = "recKey" + " = ?";

        // selection argument
        String[] selectionArgs = {recKey};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query("User", //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();

        User user = null;

        if (cursorCount > 0) {
            cursor.moveToFirst();
            int id = cursor.getInt(cursor.getColumnIndex("userID"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String email1 = cursor.getString(cursor.getColumnIndex("email"));
            String surname = cursor.getString(cursor.getColumnIndex("surname"));
            String contactNr = cursor.getString(cursor.getColumnIndex("contactNr"));
            String address = cursor.getString(cursor.getColumnIndex("address"));
            user = new User(Integer.toString(id),name,surname,email1,contactNr,address,"","");
        }
        cursor.close();
        db.close();
        return user;
    }
    public void changePassword(String password,String userID){
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("password", password);
        String where = "userID" + " = ?";
        String[] args = {userID};
        db.update(TABLE_NAME,values,where,args);
        db.close();
    }



}
