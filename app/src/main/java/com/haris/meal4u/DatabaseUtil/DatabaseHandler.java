package com.haris.meal4u.DatabaseUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.AppController;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;


/**
 * Created by hp on 2/27/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = Utility.getStringFromRes(AppController.getInstance(), R.string.app_name);

    String CREATE_FAVOURITE_TABLE = "CREATE TABLE " + Constant.DatabaseColumn.FAVOURITES_TABLE_NAME + "(" +
            Constant.DatabaseColumn.FAVOURITES_COLUMN_ID + " INTEGER PRIMARY KEY," +
            Constant.DatabaseColumn.FAVOURITES_COLUMN_USER_ID + " TEXT," +
            Constant.DatabaseColumn.FAVOURITES_COLUMN_RESTAURANT_ID + " TEXT " +
            ")";

    String CREATE_CART_TABLE = "CREATE TABLE " + Constant.DatabaseColumn.CART_TABLE_NAME + "(" +
            Constant.DatabaseColumn.CART_COLUMN_ID + " INTEGER PRIMARY KEY," +
            Constant.DatabaseColumn.CART_COLUMN_USER_ID + " TEXT," +
            Constant.DatabaseColumn.CART_COLUMN_RESTAURANT_ID + " TEXT," +
            Constant.DatabaseColumn.CART_COLUMN_RESTAURANT_LATITUDE+ " TEXT," +
            Constant.DatabaseColumn.CART_COLUMN_RESTAURANT_LONGITUDE + " TEXT," +
            Constant.DatabaseColumn.CART_COLUMN_DELIVERY_CHARGES + " TEXT," +
            Constant.DatabaseColumn.CART_COLUMN_MIN_ORDER_PRICE + " TEXT," +
            Constant.DatabaseColumn.CART_COLUMN_DELIVERY_TIME + " TEXT," +
            Constant.DatabaseColumn.CART_COLUMN_PAYMENT_TYPE + " TEXT," +
            Constant.DatabaseColumn.CART_COLUMN_PRODUCT_ID + " TEXT," +
            Constant.DatabaseColumn.CART_COLUMN_PRODUCT_NAME + " TEXT ," +
            Constant.DatabaseColumn.CART_COLUMN_PRODUCT_BASE_PRICE + " TEXT ," +
            Constant.DatabaseColumn.CART_COLUMN_PRODUCT_PRICE + " TEXT ," +
            Constant.DatabaseColumn.CART_COLUMN_CURRENCY_SYMBOL + " TEXT ," +
            Constant.DatabaseColumn.CART_COLUMN_PRODUCT_QUANTITY + " TEXT ," +
            Constant.DatabaseColumn.CART_COLUMN_PRODUCT_ATTRIBUTE + " TEXT ," +
            Constant.DatabaseColumn.CART_COLUMN_SPECIAL_INSTRUCTIONS + " TEXT " +
            ")";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        
        db.execSQL(CREATE_FAVOURITE_TABLE);
        db.execSQL(CREATE_CART_TABLE);
      


    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed

        ///db.execSQL("ALTER TABLE " + Constant.REMINDER_DATA.TABLE_NAME + " CHANGE COLUMN " + Constant.REMINDER_DATA.FAVOURITE + " TEXT '" + Constant.REMINDER_DATA.NOT_FAVOURITE + "'");
        
        db.execSQL("DROP TABLE IF EXISTS " + Constant.DatabaseColumn.FAVOURITES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constant.DatabaseColumn.CART_TABLE_NAME);
    


        // Create tables again
        onCreate(db);
    }

}
