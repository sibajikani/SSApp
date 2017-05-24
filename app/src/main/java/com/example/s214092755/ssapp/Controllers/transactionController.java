package com.example.s214092755.ssapp.Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.s214092755.ssapp.DatabaseHelper;
import com.example.s214092755.ssapp.Models.Product;
import com.example.s214092755.ssapp.Models.Transaction;
import com.example.s214092755.ssapp.Models.User;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by s214092755 on 2017/05/20.
 */

public class transactionController {
    private DatabaseHelper mDBHelper;
    private Context context;
    private final String TABLE_NAME = "OrderTransaction";
    private User curUser;
    ArrayList<Transaction> transactions;

    public transactionController(DatabaseHelper mDBHelper, Context context) {
        this.mDBHelper = mDBHelper;
        this.context = context;
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction)
    {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("processed", transaction.getProcessed());
        values.put("quantity", transaction.getQuantity());
        values.put("pID",transaction.getpID());
        values.put("dates",getDate());
        values.put("userID", transaction.getUserID());

        // Inserting Row
        db.insertOrThrow(TABLE_NAME, null, values);
        db.close();
    }
    private String getDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(c.getTime());
    }
    public ArrayList<Transaction> getTransactions(User currentUser)
    {
        //list of transactions for the current user
        ArrayList<Transaction> transactions = new ArrayList<>();

        //current userID
        String curUserID = currentUser.getID();

        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        // array of columns to fetch from the transaction table in database
        String[] columns = {
                "tID",
                "processed",
                "quantity",
                "pID",
                "dates",
                "userID"
        };

        // selection criteria
        String selection = "userID" + " = ?";

        // selection argument
        String[] selectionArgs = {curUserID};

        //Returns the supplements contained in the product table
        Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order

        Cursor cursor2 = null;


        //add returned transactions to transactions list
        if (cursor.moveToFirst())
        {
            do
            {

             //Extract fields
              Integer tID = cursor.getInt(cursor.getColumnIndex("tID"));
                Integer processed = cursor.getInt(cursor.getColumnIndex("processed"));
                Integer quantity = cursor.getInt(cursor.getColumnIndex("quantity"));
                Integer pID = cursor.getInt(cursor.getColumnIndex("pID"));
                String dates = cursor.getString(cursor.getColumnIndex("dates"));
                Integer userID = cursor.getInt(cursor.getColumnIndex("userID"));
                String type = null;

               //extract product type from product table
                // array of columns to fetch from the product table in database
                String[] columns2 = {
                        "type"
                };

                // selection criteria
                String selection2 = "pID" + " = ?";

                // selection argument
                String[] selectionArgs2 = {pID.toString()};

                //Returns the type of the product in the product table
                cursor2 = db.query("Product", //Table to query
                        columns2,    //columns to return
                        selection2,        //columns for the WHERE clause
                        selectionArgs2,        //The values for the WHERE clause
                        null,       //group the rows
                        null,       //filter by row groups
                        null); //The sort order

                if (cursor2.moveToFirst())
                {
                    type = cursor2.getString(cursor2.getColumnIndex("type"));
                }

                assert type != null;
                if (type.equals("Supplement"))
                    type = "supp";
                else
                    type = "merch";

             //create transaction to add to list
                Transaction transaction = new Transaction(pID.toString(),userID.toString(),quantity,processed,type,dates);

             //add transaction to list
             transactions.add(transaction);

            }
            while (cursor.moveToNext());
        }


        cursor.close();

        if (cursor2!=null)
        cursor2.close();



        return transactions;

    }


    public void addToList(){

    }


}
