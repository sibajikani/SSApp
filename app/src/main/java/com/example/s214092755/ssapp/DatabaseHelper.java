package com.example.s214092755.ssapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.s214092755.ssapp.Models.Merchandise;
import com.example.s214092755.ssapp.Models.Product;
import com.example.s214092755.ssapp.Models.Supplement;
import com.example.s214092755.ssapp.Models.Transaction;
import com.example.s214092755.ssapp.Models.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "SSDatabase.db";

    // User table name
    private static final String TABLE_USER = "user";
    private static final String TABLE_PRODUCT = "product";
    private static final String TABLE_PRODUCT_MERCH = "product_merch";
    private static final String TABLE_PRODUCT_SUPPS = "product_supps";
    private static final String TABLE_TRANSACTION = "transaction";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_SURNAME = "user_surname";
    private static final String COLUMN_USER_CONTACT = "user_contact";
    private static final String COLUMN_USER_ADDRESS = "user_address";
    private static final String COLUMN_USER_RECKEY = "user_reckey";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    // product table column names
    private static final String COLUMN_PRODUCT_ID = "prod_ID";
    private static final String COLUMN_PRODUCT_NAME = "prod_name";
    private static final String COLUMN_PRODUCT_TYPE = "prod_type";
    private static final String COLUMN_PRODUCT_ON_HAND = "prod_on_hand";
    private static final String COLUMN_PRODUCT_UNIT_PRICE = "prod_unit_price";
    private static final String COLUMN_PRODUCT_MANU = "prod_manu";
    private static final String COLUMN_PRODUCT_PIC_LINK = "prod_pic_link";

    //merchandise table column names
    private static final String COLUMN_PRODUCT_MERCH_ID = "prod_merch_ID";
    private static final String COLUMN_PRODUCT_MERCH_TYPE = "prod_merch_type";
    private static final String COLUMN_PRODUCT_MERCH_SIZE = "prod__merch_size";

    //supplements table column names
    private static final String COLUMN_PRODUCT_SUPPS_ID = "prod_supp_ID";
    private static final String COLUMN_PRODUCT_SUPPS_FLAVOUR = "prod_supp_flavour";
    private static final String COLUMN_PRODUCT_SUPPS_TYPE = "prod_supp_flavour";
    private static final String COLUMN_PRODUCT_SUPPS_SIZE    = "prod_supp_size";

    //transaction table column names
    private static final String COLUMN_TRANSACTION_ID = "trans_ID";
    private static final String COLUMN_TRANSACTION_PROCESSED = "trans_processed";
    private static final String COLUMN_TRANSACTION_QUANTITY = "trans_quantity";
    private static final String COLUMN_TRANSACTION_PRODUCT = "trans_product";
    private static final String COLUMN_TRANSACTION_DATE = "trans_date";


    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    private static String DB_NAME = "SSDatabase.db";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase mDataBase;
    private final Context mContext;
    private boolean mNeedUpdate = false;

    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        this.mContext = context;

        copyDataBase();

        this.getReadableDatabase();
    }

    public void updateDataBase() throws IOException {
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();

            copyDataBase();

            mNeedUpdate = false;
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDataBase() throws SQLException {
        mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }

    //Queries
    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

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
        db.insert("User", null, values);
        db.close();
    }

    //Uses addUser and sends email with recKey to user when registering
    public void registerUser(User user){

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
                "address",
        };
        SQLiteDatabase db = this.getReadableDatabase();
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

        String id = cursor.getString(cursor.getColumnIndex("userID"));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String email1 = cursor.getString(cursor.getColumnIndex("email"));
        String surname = cursor.getString(cursor.getColumnIndex("surname"));
        String contactNr = cursor.getString(cursor.getColumnIndex("contactNr"));
        String address = cursor.getString(cursor.getColumnIndex("address"));

        if(cursorCount > 0)
            return null;

        cursor.close();
        db.close();

        return new User(id,name,surname,email1,contactNr,address,"","");


    }

    //Get all products - supps and merchandise
    /**
     * This method is to fetch all product and return the list of product records
     *
     * @return list
     */
    public List<Product> getAllProducts() {
        // array of columns to fetch
        String[] columns = {
                "productID",
                "name",
                "type",
                "onHand",
                "unitPrice",
                "manufacturer",
                "picLink",
        };

        List<Product> productList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /*
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query("Product", //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("productID"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
                int onH = cursor.getInt(cursor.getColumnIndex("onHand"));
                double unit = Double.parseDouble(cursor.getString(cursor.getColumnIndex("unitPrice")));
                String man = cursor.getString(cursor.getColumnIndex("manufacturer"));
                String link = cursor.getString(cursor.getColumnIndex("picLink"));

                Product product = new Product(id,name,type,man,link,onH,unit);

                // Adding user record to list
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return productList;
    }
    public List<Supplement> getAllSupplements(){
        // array of columns to fetch
        String[] columns = {
                "productID",
                "name",
                "type",
                "onHand",
                "unitPrice",
                "manufacturer",
                "picLink",
        };

        List<Product> productList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /*
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query("Product", //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("productID"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
                int onH = cursor.getInt(cursor.getColumnIndex("onHand"));
                double unit = Double.parseDouble(cursor.getString(cursor.getColumnIndex("unitPrice")));
                String man = cursor.getString(cursor.getColumnIndex("manufacturer"));
                String link = cursor.getString(cursor.getColumnIndex("picLink"));

                Product product = new Product(id,name,type,man,link,onH,unit);

                // Adding user record to list
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return new ArrayList<>();
    }
    public List<Merchandise> getAllMerchandise(){
        return null;
    }

    //Same format as getAllProducts
    //Get all transactions using the userID - transactions of a user
    public List<Transaction> getAllTransactions(String userID){
        return null;
    }

    public void changePassword(){}

    public void changeRecKey(){

    }




    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
    }

    //    *
//     * This method is to create user record
//     *
//     * @param user
//    public void addUser(User user) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//            /*values.put(COLUMN_USER_NAME, user.getName());
//            values.put(COLUMN_USER_EMAIL, user.getEmail());
//            values.put(COLUMN_USER_PASSWORD, user.getPassword());*/
//
//        // Inserting Row
//        db.insert(TABLE_USER, null, values);
//        db.close();
//    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
       /* public List<User> getAllUser() {
            // array of columns to fetch
            String[] columns = {
                    COLUMN_USER_ID,
                    COLUMN_USER_EMAIL,
                    COLUMN_USER_NAME,
                    COLUMN_USER_PASSWORD
            };
            // sorting orders
            String sortOrder =
                    COLUMN_USER_NAME + " ASC";
            List<User> userList = new ArrayList<User>();

            SQLiteDatabase db = this.getReadableDatabase();

            // query the user table
            *//**
     * Here query function is used to fetch records from user table this function works like we use sql query.
     * SQL query equivalent to this query function is
     * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
     *//*
            Cursor cursor = db.query(TABLE_USER, //Table to query
                    columns,    //columns to return
                    null,        //columns for the WHERE clause
                    null,        //The values for the WHERE clause
                    null,       //group the rows
                    null,       //filter by row groups
                    sortOrder); //The sort order


            // Traversing through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    User user = new User();
                    user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                    user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                    user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                    user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                    // Adding user record to list
                    userList.add(user);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();

            // return user list
            return userList;
        }

        *//**
     * This method to update user record
     *
     * @param user
     *//*
        public void updateUser(User user) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_NAME, user.getName());
            values.put(COLUMN_USER_EMAIL, user.getEmail());
            values.put(COLUMN_USER_PASSWORD, user.getPassword());

            // updating row
            db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                    new String[]{String.valueOf(user.getId())});
            db.close();
        }

        *//**
     * This method is to delete user record
     *
     * @param user
     *//*
        public void deleteUser(User user) {
            SQLiteDatabase db = this.getWritableDatabase();
            // delete user record by id
            db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                    new String[]{String.valueOf(user.getId())});
            db.close();
        }

        *//**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     *//*
        public boolean checkUser(String email) {

            // array of columns to fetch
            String[] columns = {
                    COLUMN_USER_ID
            };
            SQLiteDatabase db = this.getReadableDatabase();

            // selection criteria
            String selection = COLUMN_USER_EMAIL + " = ?";

            // selection argument
            String[] selectionArgs = {email};

            // query user table with condition
            *//**
     * Here query function is used to fetch records from user table this function works like we use sql query.
     * SQL query equivalent to this query function is
     * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
     *//*
            Cursor cursor = db.query(TABLE_USER, //Table to query
                    columns,                    //columns to return
                    selection,                  //columns for the WHERE clause
                    selectionArgs,              //The values for the WHERE clause
                    null,                       //group the rows
                    null,                      //filter by row groups
                    null);                      //The sort order
            int cursorCount = cursor.getCount();
            cursor.close();
            db.close();

            if (cursorCount > 0) {
                return true;
            }

            return false;
        }

        *//**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     *//*
        public boolean checkUser(String email, String password) {

            // array of columns to fetch
            String[] columns = {
                    COLUMN_USER_ID
            };
            SQLiteDatabase db = this.getReadableDatabase();
            // selection criteria
            String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

            // selection arguments
            String[] selectionArgs = {email, password};

            // query user table with conditions
            *//**
     * Here query function is used to fetch records from user table this function works like we use sql query.
     * SQL query equivalent to this query function is
     * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
     *//*
            Cursor cursor = db.query(TABLE_USER, //Table to query
                    columns,                    //columns to return
                    selection,                  //columns for the WHERE clause
                    selectionArgs,              //The values for the WHERE clause
                    null,                       //group the rows
                    null,                       //filter by row groups
                    null);                      //The sort order

            int cursorCount = cursor.getCount();

            cursor.close();
            db.close();
            if (cursorCount > 0) {
                return true;
            }

            return false;
        }

    }*/
}