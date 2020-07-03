package com.haris.meal4u.DatabaseUtil;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.ObjectUtil.CursorObject;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.Utility.Utility;

import java.util.ArrayList;

public class QueryRunner {
    private Context context;
    private String TAG = QueryRunner.class.getName();

    /**
     * <p>It contain methods for executing the SQL Database Queries & get Required Result</p>
     */
    public QueryRunner(Context context) {
        this.context = context;
        Utility.Logger(QueryRunner.class.getName(), "Setting : Working");
    }


    /**
     * <p>It execute Query and return data in the form of list by adding into arraylist</p>
     *
     * @param query            SQL Query which we want to execute
     * @param sqLiteOpenHelper SQLiteOpenHelper instance
     * @return arraylist which contain all of required data from Database
     */
    public ArrayList<Object> getAll(String query, SQLiteOpenHelper sqLiteOpenHelper, DatabaseObject databaseObject) {

        CursorObject cursorObject = getCursor(query, sqLiteOpenHelper);
        Cursor cursor = cursorObject.getCursor();
        ArrayList<Object> objectArrayList = new ArrayList<>();
        Utility.Logger(QueryRunner.class.getName(), "Size of Cursor : " + cursor.getCount());

        if (cursor.moveToFirst()) {

            do {

                if (databaseObject.getTypeOperation() == Constant.TYPE.FAVOURITES) {


                    objectArrayList.add(new DataObject()
                            .setFavourite_id(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.FAVOURITES_COLUMN_ID)))
                            .setObject_id(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.FAVOURITES_COLUMN_RESTAURANT_ID)))
                            .setUser_id(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.FAVOURITES_COLUMN_USER_ID)))
                    );


                }else if (databaseObject.getTypeOperation() == Constant.TYPE.CART) {


                    objectArrayList.add(new DataObject()
                            .setCart_id(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.CART_COLUMN_ID)))
                            .setUser_id(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.CART_COLUMN_USER_ID)))
                            .setObject_id(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.CART_COLUMN_RESTAURANT_ID)))
                            .setObject_latitude(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.CART_COLUMN_RESTAURANT_LATITUDE)))
                            .setObject_longitude(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.CART_COLUMN_RESTAURANT_LONGITUDE)))
                            .setObject_delivery_charges(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.CART_COLUMN_DELIVERY_CHARGES)))
                            .setObject_min_order_price(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.CART_COLUMN_MIN_ORDER_PRICE)))
                            .setObject_min_delivery_time(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.CART_COLUMN_DELIVERY_TIME)))
                            .setPaymentType(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.CART_COLUMN_PAYMENT_TYPE)))
                            .setPost_id(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.CART_COLUMN_PRODUCT_ID)))
                            .setPost_name(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.CART_COLUMN_PRODUCT_NAME)))
                            .setPost_quantity(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.CART_COLUMN_PRODUCT_QUANTITY)))
                            .setPost_price(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.CART_COLUMN_PRODUCT_PRICE)))
                            .setBasePrice(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.CART_COLUMN_PRODUCT_BASE_PRICE)))
                            .setObject_currency_symbol(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.CART_COLUMN_CURRENCY_SYMBOL)))
                            .setPost_attribute_id(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.CART_COLUMN_PRODUCT_ATTRIBUTE)))
                            .setSpecial_instructions(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.CART_COLUMN_SPECIAL_INSTRUCTIONS)))

                    );


                }

            } while (cursor.moveToNext());

        }

        cursor.close();
        cursorObject.getDatabase().close();

        return objectArrayList;
    }


    /**
     * <p>Execute query and give true/false on Success or Failing</p>
     *
     * @param query            SQL Query which we want to execute
     * @param sqLiteOpenHelper SQLiteOpenHelper instance
     * @return true if perform successfully
     */
    public CursorObject getStatus(String query, SQLiteOpenHelper sqLiteOpenHelper) {
        Utility.Logger(QueryRunner.class.getName(), "Setting : " + query);
        CursorObject cursorObject = getCursor(query, sqLiteOpenHelper);
        Cursor cursor = cursorObject.getCursor();
        long lastInsertedId;
        try {

            cursor.moveToFirst();

            cursor.close();

        } catch (SQLiteConstraintException e) {

            cursorObject.setCursor(null).setDatabase(null);
        } catch (UnsupportedOperationException uoe) {

            cursorObject.setCursor(null).setDatabase(null);
        }

        return cursorObject;
    }


    /**
     * <p>Execute query and give last inserted Row Id</p>
     *
     * @param query            SQL Query which we want to execute
     * @param sqLiteOpenHelper SQLiteOpenHelper instance
     * @return long      last inserted id
     */
    public long getLastInsertID(String query, SQLiteOpenHelper sqLiteOpenHelper) {
        Utility.Logger("Last Inserted", query);
        long lastInsertedId;
        CursorObject cursorObject = getCursor(query, sqLiteOpenHelper);
        Cursor cursor = cursorObject.getCursor();
        cursor.moveToFirst();
        lastInsertedId = cursor.getLong(0);
        Utility.Logger("Before", String.valueOf(lastInsertedId));
        cursor.close();
        cursorObject.getDatabase().close();
        Utility.Logger("After", String.valueOf(lastInsertedId));
        return lastInsertedId;
    }


    /**
     * <p>It run the SQL Query and return data in Cursor</p>
     *
     * @param query            SQL Query which we want to run
     * @param sqLiteOpenHelper Instance of SQLiteOpenHelper
     * @return cursor which contain data fetch from Database
     */
    private CursorObject getCursor(String query, SQLiteOpenHelper sqLiteOpenHelper) {
        CursorObject cursorObject = new CursorObject();

        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursorObject.setCursor(cursor).setDatabase(db);


        return cursorObject;
    }


}
