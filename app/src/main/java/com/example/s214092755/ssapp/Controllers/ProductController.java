package com.example.s214092755.ssapp.Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.s214092755.ssapp.DatabaseHelper;
import com.example.s214092755.ssapp.MainActivity;
import com.example.s214092755.ssapp.Models.Merchandise;
import com.example.s214092755.ssapp.Models.Product;
import com.example.s214092755.ssapp.Models.Supplement;

import org.w3c.dom.DOMErrorHandler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by s214092755 on 2017/05/20.
 */

public class ProductController {
    private DatabaseHelper mDBHelper;
    private Context context;
    private final String TABLE_NAME = "Product";
    private Map<Integer,ArrayList<Merchandise>> Merchandise;
    private Map<Integer,ArrayList<Supplement>> Supplements;

    //list of sorted supplements
    ArrayList<Map.Entry<Integer,ArrayList<Supplement>>> suplist = new ArrayList<Map.Entry<Integer,ArrayList<Supplement>>>();

    //list of sorted merchandise
    ArrayList<Map.Entry<Integer,ArrayList<Merchandise>>> merchlist = new ArrayList<Map.Entry<Integer,ArrayList<Merchandise>>>();

    public ProductController(DatabaseHelper mDBHelper, Context context) {
        this.mDBHelper = mDBHelper;
        this.context = context;
        Merchandise = new HashMap<>();
        Supplements = new HashMap<>();
    }
    public void changeQuantity(int onHand,String productID)
    {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("onHand", onHand);
        String where = "productID" + " = ?";
        String[] args = {productID};
        db.update(TABLE_NAME,values,where,args);
        db.close();
    }
    public Map<Integer,ArrayList<Supplement>> getAllSupplements()
    {

        // array of columns to fetch from the product table in database
        String[] columns = {
                "pID",
                "name",
                "type",
                "manufacturer"
        };


        //List to store the supplements in mapped form

        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        // selection criteria
        String selection = "type" + " = ?";

        // selection argument
        String[] selectionArgs = {"Supplement"};

        //Returns the supplements contained in the product table
        Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order


        // array of columns to fetch from the Product_Item table in database
        String[] Prod_Item_columns = {
                "itemID",
                "description",
                "onHand",
                "unitPrice",
                "picLink",
                "size",
                "flavour",
                "colour",
                "item_pID"
        };

        //Get all product items from Product_Item table
        Cursor cursor2 = db.query("Product_Item", //Table to query
                Prod_Item_columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order

        //Create supplement mapping for each supplement in Product table (different flavour mapping)

        //check each supplement
        if (cursor.moveToFirst())
        {
            do
            {
                //extract pID, name, type, manufacturer fields from current supplement being checked for different flavours
                Integer pID = cursor.getInt(cursor.getColumnIndex("pID"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String manufacturer = cursor.getString(cursor.getColumnIndex("manufacturer"));

                //check for different flavours for each supplement
                if (cursor2.moveToFirst())
                {
                    do
                    {
                        //extract item_pID field from current Product_Item item
                        Integer item_pID= cursor2.getInt(cursor2.getColumnIndex("item_pID"));

                        //check for corresponding values of pID and item_pID
                        if (pID == item_pID)
                        {
                            //extract all applicable fields from current Product_Item item
                            String typeSup = cursor2.getString(cursor2.getColumnIndex("description"));
                            Integer onHand = cursor2.getInt(cursor2.getColumnIndex("onHand"));
                            Double unitPrice = cursor2.getDouble(cursor2.getColumnIndex("unitPrice"));
                            String picLink = cursor2.getString(cursor2.getColumnIndex("picLink"));
                            String size = cursor2.getString(cursor2.getColumnIndex("size"));
                            String flavour = cursor2.getString(cursor2.getColumnIndex("flavour"));

                            //determine if the supplement has been added
                            boolean added = false;

                            //add the fields to new Supplement
                            Supplement supp = new Supplement(pID.toString(),name,manufacturer,picLink,unitPrice,flavour,typeSup,size,onHand);

                            //create new supplement mapping
                            //first check for existent pID mapping else add new mapping
                            for (Map.Entry<Integer, ArrayList<Supplement>> entry : Supplements.entrySet())
                            {
                                //existent mapping for that supplement, so add new flavour
                                if (entry.getKey()== pID)
                                {
                                    //Add supplement with different flavour for this particular mapping
                                    //add to list within map
                                    entry.getValue().add(supp);
                                    added = true;
                                    break;
                                }
                            }

                            //no mapping found
                            if (!added)
                            {
                                //create new supp list for mapping
                                ArrayList<Supplement> mappedSupps = new ArrayList<>();
                                //add current supp to mappedSupps
                                mappedSupps.add(supp);
                                //create mapping
                                Supplements.put(pID,mappedSupps);
                            }
                        }
                    }
                    while (cursor2.moveToNext());
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        cursor2.close();
        db.close();

        // return user list
        return Supplements;
    }

    public Map<Integer,ArrayList<Merchandise>> getAllMerchandise()
    {

        // array of columns to fetch from the product table in database
        String[] columns = {
                "pID",
                "name",
                "type",
                "manufacturer"
        };

        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        // selection criteria
        String selection = "type" + " = ?";

        // selection argument
        String[] selectionArgs = {"Merchandise"};

        //Returns the merchandise contained in the product table
        Cursor cursor = db.query("Product", //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order


        // array of columns to fetch from the Product_Item table in database
        String[] Prod_Item_columns = {
                "itemID",
                "description",
                "onHand",
                "unitPrice",
                "picLink",
                "size",
                "flavour",
                "colour",
                "item_pID"
        };

        //Get all product items from Product_Item table
        Cursor cursor2 = db.query("Product_Item", //Table to query
                Prod_Item_columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order

        //Create merchandise mapping for each merchandise item in Product table (different colour mapping)

        //check each merchandise item
        if (cursor.moveToFirst())
        {
            do
            {
                //extract pID, name, type, manufacturer fields from current merchandise item being checked for different colours
                Integer pID = cursor.getInt(cursor.getColumnIndex("pID"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String manufacturer = cursor.getString(cursor.getColumnIndex("manufacturer"));

                //check for different colours for each merchandise item
                if (cursor2.moveToFirst())
                {
                    do

                    {
                        //extract item_pID field from current Product_Item item
                        Integer item_pID= cursor2.getInt(cursor2.getColumnIndex("item_pID"));

                        //check for corresponding values of pID and item_pID
                        if (pID == item_pID)
                        {
                            //extract all applicable fields from current Product_Item item
                            String typeMerch = cursor2.getString(cursor2.getColumnIndex("description"));
                            Integer onHand = cursor2.getInt(cursor2.getColumnIndex("onHand"));
                            Double unitPrice = cursor2.getDouble(cursor2.getColumnIndex("unitPrice"));
                            String picLink = cursor2.getString(cursor2.getColumnIndex("picLink"));
                            String size = cursor2.getString(cursor2.getColumnIndex("size"));
                            String colour = cursor2.getString(cursor2.getColumnIndex("colour"));

                            //determine if the merchandise item has been added
                            boolean added = false;

                            //add the fields to new Merchandise item
                            Merchandise merch = new Merchandise(pID.toString(),name,manufacturer,picLink,unitPrice,colour,typeMerch,size,onHand);

                            //create new merchandise mapping
                            //first check for existent pID mapping else add new mapping
                            for (Map.Entry<Integer, ArrayList<Merchandise>> entry : Merchandise.entrySet())
                            {
                                //existent mapping for that merchandise, so add new colour
                                if (entry.getKey()== pID)
                                {
                                    //Add merchandise item with different colour for this particular mapping
                                    //add to list within map
                                    entry.getValue().add(merch);
                                    added = true;
                                    break;

                                }

                            }

                            //no mapping found
                            if (!added)
                            {
                                //create new merch list for mapping
                                ArrayList<Merchandise> mappedMerch = new ArrayList<>();

                                //add current merch to mappedMerch
                                mappedMerch.add(merch);

                                //create mapping
                                Merchandise.put(pID,mappedMerch);
                            }
                        }
                    }
                    while (cursor2.moveToNext());
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        cursor2.close();
        db.close();

        // return user list
        return Merchandise;
    }
    public void setSupList(MainActivity main)
    {
        ArrayList<Supplement> Supps = new ArrayList<>();
        for (Map.Entry<Integer,ArrayList<Supplement>> entry : Supplements.entrySet())
        {
            Supps.addAll(entry.getValue());
        }


        main.setSupplements(Supps);

         //Sort mapped Supplements
        suplist = new ArrayList<Map.Entry<Integer,ArrayList<Supplement>>>(Supplements.entrySet());
        Collections.sort(suplist, new Comparator<Map.Entry<Integer, ArrayList<Supplement>>>()
        {
            @Override
            public int compare(Map.Entry<Integer, ArrayList<Supplement>> o1, Map.Entry<Integer, ArrayList<Supplement>> o2) {
                return o1.getValue().get(0).getName().compareTo(o2.getValue().get(0).getName());
            }
        });

        Supplements.clear();

        for (Map.Entry<Integer, ArrayList<Supplement>> entry : suplist)
        {
           Supplements.put(entry.getKey(),entry.getValue());
        }

    }

    public ArrayList<Map.Entry<Integer,ArrayList<Supplement>>> getSuplist() {return suplist;}
    public ArrayList<Map.Entry<Integer,ArrayList<Merchandise>>> getMerchlist() {return merchlist;}


    public void setMerchList(MainActivity main)
    {
        ArrayList<Merchandise> Merch = new ArrayList<>();
        for (Map.Entry<Integer,ArrayList<Merchandise>> entry : Merchandise.entrySet())
        {
            Merch.addAll(entry.getValue());
        }


        main.setMerchandise(Merch);

        //Sort mapped Merchandise
        merchlist = new ArrayList<Map.Entry<Integer,ArrayList<Merchandise>>>(Merchandise.entrySet());
        Collections.sort(merchlist, new Comparator<Map.Entry<Integer, ArrayList<Merchandise>>>()
        {
            @Override
            public int compare(Map.Entry<Integer, ArrayList<Merchandise>> o1, Map.Entry<Integer, ArrayList<Merchandise>> o2)
            {
                return o1.getValue().get(0).getName().compareTo(o2.getValue().get(0).getName());
            }
        });

        Merchandise.clear();

        for (Map.Entry<Integer, ArrayList<Merchandise>> entry : merchlist)
        {
            Merchandise.put(entry.getKey(),entry.getValue());
        }
    }

    private void SortSupps(ArrayList<Supplement> Supplements )
    {
        for (int x = 0 ; x< Supplements.size() - 2; x++)
        {
            for (int y = x+1; y< Supplements.size() - 1 ; y++)
            {

                Supplement first = Supplements.get(x);
                Supplement second = Supplements.get(y);

                if (first.getName().compareTo(second.getName()) > 0)
                {
                    //Swop
                    Supplement temp = first;
                    first = second;
                    second = temp;

                }
                Supplements.set(x,first);
                Supplements.set(y,second);

            }
        }

    }

    private void SortMerch(ArrayList<Merchandise> Merchandise )
    {
        for (int x = 0 ; x< Merchandise.size() - 2; x++)
        {
            for (int y = x+1; y< Merchandise.size() - 1 ; y++)
            {

                Merchandise first = Merchandise.get(x);
                Merchandise second = Merchandise.get(y);

                if (first.getName().compareTo(second.getName()) > 0)
                {
                    //Swop
                    Merchandise temp = first;
                    first = second;
                    second = temp;

                }
                Merchandise.set(x,first);
                Merchandise.set(y,second);

            }
        }

    }



}
