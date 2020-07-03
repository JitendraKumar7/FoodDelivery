package com.haris.meal4u.DatabaseUtil;

import android.database.DatabaseUtils;

import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.InterfaceUtil.SqlQueries;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.Utility.Utility;

public class QueryFactory extends DbQuery {


    public QueryFactory() {
        Utility.Logger(QueryFactory.class.getName(), "Setting : Working");
    }


    /**
     * <p>It is used to get Required Formmatted for getting required data</p>
     *
     * @param databaseObject
     * @return
     */
    public String getRequiredFormattedQuery(DatabaseObject databaseObject) {

        SqlQueries sqlQueries = null;
        String formattedQuery = null;
        DataObject dataObject = null;

        if (databaseObject.getDataObject() == null)
            return "null";
        else
            dataObject = databaseObject.getDataObject();

        if (databaseObject.getTypeOperation() == Constant.TYPE.FAVOURITES) {

            sqlQueries = getFavouriteQueries();

            if (databaseObject.getDbOperation() == Constant.DB.RETRIEVE) {

                formattedQuery = sqlQueries.retrieving();

            } else if (databaseObject.getDbOperation() == Constant.DB.INSERT) {

                formattedQuery = String.format(sqlQueries.insert()
                        , sqlString(dataObject.getObject_id())
                        , sqlString(dataObject.getUser_id()));

            } else if (databaseObject.getDbOperation() == Constant.DB.DELETE) {

                formattedQuery = String.format(sqlQueries.delete()
                        , sqlString(dataObject.getFavourite_id()));

            } else if (databaseObject.getDbOperation() == Constant.DB.SPECIFIC_ID) {

                formattedQuery = String.format(sqlQueries.retrieveSpecificTags()
                        , sqlString(dataObject.getUser_id()));


            } else if (databaseObject.getDbOperation() == Constant.DB.UPDATE) {



            } else if (databaseObject.getDbOperation() == Constant.DB.SPECIFIC_TYPE) {

                formattedQuery = String.format(sqlQueries.retrieveSpecificType()
                        , sqlString(dataObject.getObject_id())
                        , sqlString(dataObject.getUser_id()));

            } else if (databaseObject.getDbOperation() == Constant.DB.DELETE_SPECIFIC_TYPE) {

                formattedQuery = String.format(sqlQueries.deleteSpecificType()
                        , sqlString(dataObject.getObject_id())
                        , sqlString(dataObject.getUser_id()));

            }

        }
        else if (databaseObject.getTypeOperation() == Constant.TYPE.CART) {

            sqlQueries = getCartQueries();

            if (databaseObject.getDbOperation() == Constant.DB.RETRIEVE) {

                formattedQuery = sqlQueries.retrieving();

            }else if (databaseObject.getDbOperation() == Constant.DB.UPDATE) {

                formattedQuery = String.format(sqlQueries.update()
                        , sqlString(dataObject.getPost_quantity())
                        , sqlString(dataObject.getPost_price())
                        , sqlString(dataObject.getCart_id()));

            } else if (databaseObject.getDbOperation() == Constant.DB.INSERT) {

                formattedQuery = String.format(sqlQueries.insert()
                        , sqlString(dataObject.getUser_id())
                        , sqlString(dataObject.getObject_id())
                        , sqlString(dataObject.getObject_latitude())
                        , sqlString(dataObject.getObject_longitude())
                        , sqlString(dataObject.getObject_delivery_charges())
                        , sqlString(dataObject.getObject_min_order_price())
                        , sqlString(dataObject.getObject_min_delivery_time())
                        , sqlString(dataObject.getPaymentType())
                        , sqlString(dataObject.getPost_id())
                        , sqlString(dataObject.getPost_name())
                        , sqlString(dataObject.getPost_quantity())
                        , sqlString(dataObject.getBasePrice())
                        , sqlString(dataObject.getPost_price())
                        , sqlString(dataObject.getObject_currency_symbol())
                        , sqlString(dataObject.getPost_attribute_id())
                        , sqlString(dataObject.getSpecial_instructions()));

            } else if (databaseObject.getDbOperation() == Constant.DB.DELETE) {

                formattedQuery = String.format(sqlQueries.delete()
                        , sqlString(dataObject.getCart_id()));

            } else if (databaseObject.getDbOperation() == Constant.DB.SPECIFIC_TYPE) {

                formattedQuery = String.format(sqlQueries.retrieveSpecificType()
                        , sqlString(dataObject.getUser_id()));

            } else if (databaseObject.getDbOperation() == Constant.DB.DELETE_SPECIFIC_TYPE) {

                formattedQuery = String.format(sqlQueries.deleteSpecificType()
                        , sqlString(dataObject.getUser_id()));

            } else if (databaseObject.getDbOperation() == Constant.DB.SPECIFIC_ID) {

                formattedQuery = String.format(sqlQueries.retrieveSpecificTags()
                        , sqlString(dataObject.getObject_id()));

            }

        }


        return formattedQuery;
    }


    /**
     * <p>It is used to convert String into
     * Database friendly String </p>
     *
     * @param data
     * @return
     */
    private String sqlString(String data) {
        String sql = null;

        if (Utility.isEmptyString(data))
            sql = "null";
        else {
            sql = DatabaseUtils.sqlEscapeString(data);
        }

        return sql;

    }

}
