package com.rch.etawah.ManagementUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;

import com.rch.etawah.ConnectionUtil.ConnectionBuilder;
import com.rch.etawah.ConstantUtil.Constant;
import com.rch.etawah.DatabaseUtil.DatabaseHandler;
import com.rch.etawah.DatabaseUtil.DatabaseObject;
import com.rch.etawah.DatabaseUtil.QueryFactory;
import com.rch.etawah.DatabaseUtil.QueryRunner;
import com.rch.etawah.ObjectUtil.CursorObject;
import com.rch.etawah.ObjectUtil.FileObject;
import com.rch.etawah.ObjectUtil.PrefObject;
import com.rch.etawah.ObjectUtil.RequestObject;
import com.rch.etawah.R;
import com.rch.etawah.Utility.Utility;

import java.io.File;
import java.util.ArrayList;

public class Management {
    private String TAG = Management.class.getName();
    private Context context;
    private QueryRunner queryRunner;
    private QueryFactory queryFactory;
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    public Management(Context context) {

        Utility.Logger(TAG, "Setting : Working");

        this.context = context;
        queryFactory = new QueryFactory();
        queryRunner = new QueryRunner(context);
        sharedPreferences = context.getSharedPreferences(Utility.getStringFromRes(context, R.string.app_name), Context.MODE_PRIVATE);

    }


    /**
     * <p>It is used to send Request to Server</p>
     *
     * @param requestObject
     */
    public void sendRequestToServer(RequestObject requestObject) {

        String serverUrl = null;
        String requestType = Constant.REQUEST.POST.toString();

        Utility.Logger(TAG, "RequestObject : " + requestObject.toString());

        //region All functionality of giving Server Url
        if (requestObject.getConnectionType() == Constant.CONNECTION_TYPE.UI) {

            if (requestObject.getConnection() == Constant.CONNECTION.HOME
                    || requestObject.getConnection() == Constant.CONNECTION.CONFIGURATION
                    || requestObject.getConnection() == Constant.CONNECTION.NEAREST
                    || requestObject.getConnection() == Constant.CONNECTION.ALL_CATEGORIES
                    || requestObject.getConnection() == Constant.CONNECTION.SEARCH
                    || requestObject.getConnection() == Constant.CONNECTION.RESTAURANT_DETAIL
                    || requestObject.getConnection() == Constant.CONNECTION.PRODUCT_MENU
                    || requestObject.getConnection() == Constant.CONNECTION.REDEEM_COUPON
                    || requestObject.getConnection() == Constant.CONNECTION.PAYMENT_CARDS
                    || requestObject.getConnection() == Constant.CONNECTION.ADD_CARD
                    || requestObject.getConnection() == Constant.CONNECTION.CHECK_OUT
                    || requestObject.getConnection() == Constant.CONNECTION.ALL_FAVOURITES
                    || requestObject.getConnection() == Constant.CONNECTION.ALL_COMMENT
                    || requestObject.getConnection() == Constant.CONNECTION.LOGIN
                    || requestObject.getConnection() == Constant.CONNECTION.SIGN_UP
                    || requestObject.getConnection() == Constant.CONNECTION.SOCIAL_LOGIN
                    || requestObject.getConnection() == Constant.CONNECTION.ORDER_HISTORY
                    || requestObject.getConnection() == Constant.CONNECTION.SPECIFIC_RESTAURANT
                    || requestObject.getConnection() == Constant.CONNECTION.DELIVERY_CHARGES
                    || requestObject.getConnection() == Constant.CONNECTION.FORGOT
                    || requestObject.getConnection() == Constant.CONNECTION.UPDATE) {

                serverUrl = Constant.ServerInformation.REST_API_URL;

            }

        } else if (requestObject.getConnectionType() == Constant.CONNECTION_TYPE.BACKGROUND) {

            if (requestObject.getConnection() == Constant.CONNECTION.ADD_FAVOURITES
                    || requestObject.getConnection() == Constant.CONNECTION.DELETE_FAVOURITES
                    || requestObject.getConnection() == Constant.CONNECTION.ADD_COMMENT
                    || requestObject.getConnection() == Constant.CONNECTION.DELETE_PAYMENT_CARD
                    || requestObject.getConnection() == Constant.CONNECTION.ORDER_RIDER_STATUS
                    || requestObject.getConnection() == Constant.CONNECTION.ADD_RIDER_RATING) {

                serverUrl = Constant.ServerInformation.REST_API_URL;

            }


        }


        //endregion

        //It initialize the Connection to Server

        new ConnectionBuilder.CreateConnection()
                .setRequestObject(requestObject
                        .setContext(context)
                        .setServerUrl(serverUrl)
                        .setRequestType(requestType))
                .buildConnection();


    }


    /**
     * <p>It is used to retrieve data from Database</p>
     *
     * @param databaseObject
     */
    public ArrayList<Object> getDataFromDatabase(DatabaseObject databaseObject) {
        ArrayList<Object> objectArrayList = new ArrayList<>();
        CursorObject cursorObject = null;
        int lastItemInsertedId;

        Utility.Logger(TAG, databaseObject.toString());

        //Get required Query for Specific Db Operation

        String formattedQuery = queryFactory.getRequiredFormattedQuery(databaseObject);
        Utility.Logger(TAG, "Setting : Working : Query = " + formattedQuery);

        //If Query Empty then return Empty Arraylist

        if (Utility.isEmptyString(formattedQuery))
            return objectArrayList;

        //Initialize Database Handler

        if (databaseObject.getTypeOperation() == Constant.TYPE.FAVOURITES
                || databaseObject.getTypeOperation() == Constant.TYPE.CART) {
            sqLiteOpenHelper = new DatabaseHandler(context);
        }

        //Perform Db Operation on formatted Sql Queries

        if (databaseObject.getDbOperation() == Constant.DB.RETRIEVE) {

            objectArrayList.addAll(queryRunner.getAll(formattedQuery, sqLiteOpenHelper, databaseObject));

        } else if (databaseObject.getDbOperation() == Constant.DB.INSERT) {

            cursorObject = queryRunner.getStatus(formattedQuery, sqLiteOpenHelper);
            if (cursorObject != null) {
                if (cursorObject.getDatabase() != null) {
                    cursorObject.getDatabase().close();
                }

            }

        } else if (databaseObject.getDbOperation() == Constant.DB.DELETE) {

            cursorObject = queryRunner.getStatus(formattedQuery, sqLiteOpenHelper);
            if (cursorObject != null) {
                if (cursorObject.getDatabase() != null)
                    cursorObject.getDatabase().close();
            }

        } else if (databaseObject.getDbOperation() == Constant.DB.DELETE_SPECIFIC_TYPE) {

            cursorObject = queryRunner.getStatus(formattedQuery, sqLiteOpenHelper);
            if (cursorObject != null) {
                if (cursorObject.getDatabase() != null)
                    cursorObject.getDatabase().close();
            }

        } else if (databaseObject.getDbOperation() == Constant.DB.UPDATE) {

            cursorObject = queryRunner.getStatus(formattedQuery, sqLiteOpenHelper);
            if (cursorObject != null) {
                if (cursorObject.getDatabase() != null)
                    cursorObject.getDatabase().close();
            }

        } else if (databaseObject.getDbOperation() == Constant.DB.SPECIFIC_ID) {

            objectArrayList.addAll(queryRunner.getAll(formattedQuery, sqLiteOpenHelper, databaseObject));

        } else if (databaseObject.getDbOperation() == Constant.DB.SPECIFIC_TYPE) {

            objectArrayList.addAll(queryRunner.getAll(formattedQuery, sqLiteOpenHelper, databaseObject));

        } else if (databaseObject.getDbOperation() == Constant.DB.INSERTION_ID) {

            objectArrayList.addAll(queryRunner.getAll(formattedQuery, sqLiteOpenHelper, databaseObject));

        }

        return objectArrayList;
    }


    /**
     * <p>It is used to get Preference Data</p>
     *
     * @param prefObject
     * @return
     */
    public PrefObject getPreferences(PrefObject prefObject) {
        PrefObject pref = new PrefObject();

        if (prefObject.isRetrieveTags()) {
            pref.setTags(sharedPreferences.getString(Constant.SharedPref.PREF_TAGS, null));
        }

        if (prefObject.isRetrieveNext()) {
            pref.setNextPage(sharedPreferences.getString(Constant.SharedPref.NEXT_URL, null));
        }

        if (prefObject.isRetrievePosition()) {
            pref.setCurrentPosition(sharedPreferences.getInt(Constant.SharedPref.POSITION, -1));
        }

        if (prefObject.isRetrieveFirstLaunch()) {
            pref.setFirstLaunch(sharedPreferences.getBoolean(Constant.SharedPref.FIRST_LAUNCH, true));
        }

        if (prefObject.isRetrieveLogin()) {
            pref.setLogin(sharedPreferences.getBoolean(Constant.SharedPref.LOGIN, false));
        }

        if (prefObject.isRetrieveUserId()) {
            pref.setUserId(sharedPreferences.getString(Constant.SharedPref.USER_ID, "0"));
        }

        if (prefObject.isRetrieveUserRemember()) {
            pref.setUserRemember(sharedPreferences.getBoolean(Constant.SharedPref.USER_REMEMBER, false));
        }

        if (prefObject.isRetrieveNewsfeed()) {
            pref.setNewsfeedId(sharedPreferences.getString(Constant.SharedPref.NEWS_FEED, "0"));
        }

        if (prefObject.isRetrieveUserCredential()) {

            pref.setUserId(sharedPreferences.getString(Constant.SharedPref.USER_ID, "0"));
            pref.setArtistId(sharedPreferences.getString(Constant.SharedPref.ARTIST_ID, "0"));
            pref.setUserEmail(sharedPreferences.getString(Constant.SharedPref.USER_EMAIL, null));
            pref.setUserPassword(sharedPreferences.getString(Constant.SharedPref.USER_PASSWORD, null));
            pref.setFirstName(sharedPreferences.getString(Constant.SharedPref.USER_FIRST_NAME, null));
            pref.setLastName(sharedPreferences.getString(Constant.SharedPref.USER_LAST_NAME, null));
            pref.setUserPhone(sharedPreferences.getString(Constant.SharedPref.USER_PHONE, null));
            pref.setPictureUrl(sharedPreferences.getString(Constant.SharedPref.USER_PICTURE, null));
            pref.setLoginType(sharedPreferences.getString(Constant.SharedPref.LOGIN_TYPE, null));
            pref.setDescription(sharedPreferences.getString(Constant.SharedPref.BIO_TYPE, null));
            pref.setUid(sharedPreferences.getString(Constant.SharedPref.UID, null));


        }

        if (prefObject.isRetrieveNightMode()) {
            pref.setNightMode(sharedPreferences.getBoolean(Constant.SharedPref.NIGHT_MODE, false));
        }

        if (prefObject.isRetrievePush()) {
            pref.setPush(sharedPreferences.getBoolean(Constant.SharedPref.PUSH_NOTIFICATION, true));
        }

        if (prefObject.isRetrieveDownloadWifi()) {
            pref.setDownloadWifi(sharedPreferences.getBoolean(Constant.SharedPref.DOWNLOAD_WIFI, false));
        }

        if (prefObject.isRetrieveSaveWallpaper()) {
            pref.setDefaultWallpaper(sharedPreferences.getInt(Constant.SharedPref.DEFAULT_PICTURE, 0));
        }

        if (prefObject.isRetrieveParallaxMode()) {
            pref.setParallaxMode(sharedPreferences.getBoolean(Constant.SharedPref.PARALLAX_MODE, Constant.ParallaxConfig.DEFAULT_PARALLAX_MODE));
        }

        if (prefObject.isRetrieveRetrieveMotion()) {
            pref.setMotion(sharedPreferences.getInt(Constant.SharedPref.RANGE, Constant.ParallaxConfig.DEFAULT_RANGE));
        }

        if (prefObject.isRetrieveDelay()) {
            pref.setDelay(sharedPreferences.getInt(Constant.SharedPref.DENY, Constant.ParallaxConfig.DEFAULT_DELAY));
        }

        if (prefObject.isRetrieveScrollingMode()) {
            pref.setScrollingMode(sharedPreferences.getBoolean(Constant.SharedPref.SCROLL, Constant.ParallaxConfig.DEFAULT_SCROLL));
        }

        if (prefObject.isRetrievePowerSavingMode()) {
            pref.setPowerSavingMode(sharedPreferences.getBoolean(Constant.SharedPref.POWER_SAVER, Constant.ParallaxConfig.DEFAULT_POWER_SAVING));
        }

        Utility.Logger(TAG, "getPreference = " + pref.toString());

        return pref;

    }


    /**
     * <p>It is used to save Preferences data</p>
     *
     * @param prefObject
     */
    public void savePreferences(PrefObject prefObject) {
        editor = sharedPreferences.edit();

        Utility.Logger(TAG, "Preference = " + prefObject.toString());

        if (prefObject.isSaveTags()) {
            editor.putString(Constant.SharedPref.PREF_TAGS, prefObject.getTags());
        } else if (prefObject.isSaveNext()) {
            editor.putString(Constant.SharedPref.NEXT_URL, prefObject.getNextPage());
        } else if (prefObject.isSavePosition()) {
            editor.putInt(Constant.SharedPref.POSITION, prefObject.getCurrentPosition());
        } else if (prefObject.isSaveFirstLaunch()) {
            editor.putBoolean(Constant.SharedPref.FIRST_LAUNCH, prefObject.isFirstLaunch());
        } else if (prefObject.isSaveLogin()) {
            editor.putBoolean(Constant.SharedPref.LOGIN, prefObject.isLogin());
        } else if (prefObject.isSaveUserId()) {
            editor.putString(Constant.SharedPref.USER_ID, prefObject.getUserId());
        } else if (prefObject.isSaveUserRemember()) {
            editor.putBoolean(Constant.SharedPref.USER_REMEMBER, prefObject.isUserRemember());
        } else if (prefObject.isSaveNewsfeed()) {
            editor.putString(Constant.SharedPref.NEWS_FEED, prefObject.getNewsfeedId());
        } else if (prefObject.isSaveUserCredential()) {

            editor.putString(Constant.SharedPref.USER_ID, prefObject.getUserId());
            editor.putString(Constant.SharedPref.ARTIST_ID, prefObject.getArtistId());
            editor.putString(Constant.SharedPref.USER_EMAIL, prefObject.getUserEmail());
            editor.putString(Constant.SharedPref.USER_PASSWORD, prefObject.getUserPassword());
            editor.putString(Constant.SharedPref.USER_FIRST_NAME, prefObject.getFirstName());
            editor.putString(Constant.SharedPref.USER_LAST_NAME, prefObject.getLastName());
            editor.putString(Constant.SharedPref.USER_PHONE, prefObject.getUserPhone());
            editor.putString(Constant.SharedPref.USER_PICTURE, prefObject.getPictureUrl());
            editor.putString(Constant.SharedPref.LOGIN_TYPE, prefObject.getLoginType());
            editor.putString(Constant.SharedPref.BIO_TYPE, prefObject.getDescription());
            editor.putString(Constant.SharedPref.UID, prefObject.getUid());

        } else if (prefObject.isSaveNightMode()) {
            editor.putBoolean(Constant.SharedPref.NIGHT_MODE, prefObject.isNightMode());
        } else if (prefObject.isSaveDownloadWifi()) {
            editor.putBoolean(Constant.SharedPref.DOWNLOAD_WIFI, prefObject.isDownloadWifi());
        } else if (prefObject.isSavePush()) {
            editor.putBoolean(Constant.SharedPref.PUSH_NOTIFICATION, prefObject.isPush());
        } else if (prefObject.isSaveWallpaper()) {

            editor.putInt(Constant.SharedPref.DEFAULT_PICTURE, prefObject.getDefaultWallpaper());

        } else if (prefObject.isSaveParallaxMode()) {

            editor.putBoolean(Constant.SharedPref.PARALLAX_MODE, prefObject.isParallaxMode());

        } else if (prefObject.isSaveMotion()) {

            editor.putInt(Constant.SharedPref.RANGE, prefObject.getMotion());

        } else if (prefObject.isSaveDelay()) {

            editor.putInt(Constant.SharedPref.DENY, prefObject.getDelay());

        } else if (prefObject.isSaveScrollingMode()) {

            editor.putBoolean(Constant.SharedPref.SCROLL, prefObject.isScrollingMode());

        } else if (prefObject.isSavePowerSavingMode()) {

            editor.putBoolean(Constant.SharedPref.POWER_SAVER, prefObject.isPowerSavingMode());

        }

        editor.commit();

    }


    /**
     * <p>It is used to delete Wallpaper from Internal Memory</p>
     */
    public void deleteWallpaperFromInternalMemory(FileObject fileObject) {

        File directory = new File(context.getFilesDir(), Utility.getStringFromRes(context, R.string.app_name));
        File[] files = directory.listFiles();
        Utility.Logger(TAG, "Size: " + files.length + " File Name = " + fileObject.getFileName());
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().equalsIgnoreCase(fileObject.getFileName())) {
                files[i].delete();
            }
            Utility.Logger(TAG, "FileName:" + files[i].getName());
        }

    }


}
