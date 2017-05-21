package com.example.s214092755.ssapp.Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.s214092755.ssapp.DatabaseHelper;
import com.example.s214092755.ssapp.Models.Product;
import com.example.s214092755.ssapp.Models.Transaction;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by s214092755 on 2017/05/20.
 */

public class transactionController {
    private DatabaseHelper mDBHelper;
    private Context context;
    private final String TABLE_NAME = "Transaction";

    public transactionController(DatabaseHelper mDBHelper, Context context) {
        this.mDBHelper = mDBHelper;
        this.context = context;
    }

    public void addTransaction(Transaction transaction) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("processed", transaction.getProcessed());
        values.put("quantity", transaction.getQuantity());
        values.put("pID",transaction.getpID());
        //values.put("date", Calendar.DATE);
        values.put("date",getDate());

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    private String getDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(c.getTime());
    }

    public Product getProduct(List<Product> products, String ID){
        for (Product product:products)
        {
            if(product.getID().equals(ID))
                return product;
        }
        return null;
    }

}
