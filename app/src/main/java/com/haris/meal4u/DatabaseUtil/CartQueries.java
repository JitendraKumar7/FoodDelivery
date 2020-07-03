package com.haris.meal4u.DatabaseUtil;

import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.InterfaceUtil.SqlQueries;
import com.haris.meal4u.Utility.Utility;

public class CartQueries implements SqlQueries {


    public CartQueries() {
        Utility.Logger(CartQueries.class.getName(), "Setting : Working");
    }

    @Override
    public String retrieving() {
        return "SELECT * FROM " + Constant.DatabaseColumn.CART_TABLE_NAME;
    }

    @Override
    public String update() {
        return "UPDATE " + Constant.DatabaseColumn.CART_TABLE_NAME + " SET "
                + Constant.DatabaseColumn.CART_COLUMN_PRODUCT_QUANTITY + "=" + "%s ,"
                + Constant.DatabaseColumn.CART_COLUMN_PRODUCT_PRICE + "=" + "%s"
                + " WHERE " + Constant.DatabaseColumn.CART_COLUMN_ID + "=" + "%s";
    }

    @Override
    public String insert() {
        return "INSERT INTO " + Constant.DatabaseColumn.CART_TABLE_NAME
                + "(" + Constant.DatabaseColumn.CART_COLUMN_USER_ID + ","
                + Constant.DatabaseColumn.CART_COLUMN_RESTAURANT_ID + ","
                + Constant.DatabaseColumn.CART_COLUMN_RESTAURANT_LATITUDE + ","
                + Constant.DatabaseColumn.CART_COLUMN_RESTAURANT_LONGITUDE + ","
                + Constant.DatabaseColumn.CART_COLUMN_DELIVERY_CHARGES + ","
                + Constant.DatabaseColumn.CART_COLUMN_MIN_ORDER_PRICE + ","
                + Constant.DatabaseColumn.CART_COLUMN_DELIVERY_TIME + ","
                + Constant.DatabaseColumn.CART_COLUMN_PAYMENT_TYPE + ","
                + Constant.DatabaseColumn.CART_COLUMN_PRODUCT_ID + ","
                + Constant.DatabaseColumn.CART_COLUMN_PRODUCT_NAME + ","
                + Constant.DatabaseColumn.CART_COLUMN_PRODUCT_QUANTITY + ","
                + Constant.DatabaseColumn.CART_COLUMN_PRODUCT_BASE_PRICE + ","
                + Constant.DatabaseColumn.CART_COLUMN_PRODUCT_PRICE + ","
                + Constant.DatabaseColumn.CART_COLUMN_CURRENCY_SYMBOL + ","
                + Constant.DatabaseColumn.CART_COLUMN_PRODUCT_ATTRIBUTE + ","
                + Constant.DatabaseColumn.CART_COLUMN_SPECIAL_INSTRUCTIONS + ") " +
                "VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)";
    }

    @Override
    public String delete() {
        return "DELETE FROM " + Constant.DatabaseColumn.CART_TABLE_NAME + " WHERE " +
                Constant.DatabaseColumn.CART_COLUMN_ID + " = " + "%s  ";
    }

    @Override
    public String retrieveSpecificType() {
        return "SELECT * FROM " + Constant.DatabaseColumn.CART_TABLE_NAME + " WHERE "
                + Constant.DatabaseColumn.CART_COLUMN_USER_ID + " =%s ";
    }


    @Override
    public String retrieveSpecificTags() {
        return "SELECT * FROM " + Constant.DatabaseColumn.CART_TABLE_NAME + " WHERE "
                + Constant.DatabaseColumn.CART_COLUMN_RESTAURANT_ID + " NOT IN (%s)";

    }

    @Override
    public String deleteSpecificType() {
        return "DELETE FROM " + Constant.DatabaseColumn.CART_TABLE_NAME + " WHERE "
                + Constant.DatabaseColumn.CART_COLUMN_USER_ID + " = %s";
    }

    @Override
    public String findLastInsertId() {
        return "Select last_insert_rowid()";
    }
}
