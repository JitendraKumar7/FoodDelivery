package com.rch.etawah.ServiceUtil;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

import com.google.gson.Gson;
import com.rch.etawah.ConnectionUtil.Connection;
import com.rch.etawah.ConstantUtil.Constant;
import com.rch.etawah.ObjectUtil.DataObject;
import com.rch.etawah.ObjectUtil.GlobalDataObject;
import com.rch.etawah.ObjectUtil.RequestObject;
import com.rch.etawah.Utility.Utility;

import java.util.ArrayList;

public class OreoIntentService extends JobIntentService {
    /**
     * Unique job ID for this service.
     */
    static final int JOB_ID = 1000;
    private String TAG = OreoIntentService.class.getName();

    public OreoIntentService() {
    }

    /**
     * Convenience method for enqueuing work in to this service.
     */
    public static void enqueueWork(Context context, Intent intent) {

        enqueueWork(context, OreoIntentService.class, JOB_ID, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        Utility.Logger(TAG, "Setting : Working");

        if (intent != null) {


            //It load specific tags wallpaper at background
            //After loading it would add them into Wallpaper db

            RequestObject requestObject = GlobalDataObject.getRequestObject() != null ? GlobalDataObject.getRequestObject()
                    : (RequestObject) intent.getParcelableExtra(Constant.IntentKey.REQUEST_OBJECT);

            String result = Connection.makeRequest(requestObject.getServerUrl(), requestObject.getJson(), requestObject.getRequestType());

            GlobalDataObject.requestObject = null;

            Utility.Logger(TAG, "JSON = " + requestObject.getJson());

            if (Utility.isEmptyString(result))
                return;

            if (result.equalsIgnoreCase(Constant.ImportantMessages.CONNECTION_ERROR))
                return;

            Gson gson = new Gson();
            Object object = null;
            DataObject dataObject = null;
            ArrayList<Object> objectList = new ArrayList<>();

          /*  if (requestObject.getConnection() == Constant.CONNECTION.USER_DATA) {

                object = gson.fromJson(result, UserDataJson.class);
                dataObject = DataObject.getWallpaperObject(requestObject, object);

                if (dataObject.getCode().equalsIgnoreCase(Constant.ErrorCodes.success_code)) {

                    String userID = "null";

                    Management management = new Management(this);
                    PrefObject prefObject = management.getPreferences(new PrefObject()
                            .setRetrieveUserId(true).setRetrieveLogin(true));


                    if (prefObject.isLogin()) {
                        userID = prefObject.getUserId();
                    }

                    for (int i = 0; i < dataObject.getHomeList().size(); i++) {

                        DataObject dtObject = (DataObject) dataObject.getHomeList().get(i);
                        management.getDataFromDatabase(new DatabaseObject()
                                .setTypeOperation(Constant.TYPE.FAVOURITES)
                                .setDbOperation(Constant.DB.INSERT)
                                .setDataObject(new DataObject()
                                        .setId(dtObject.getId())
                                        .setUserId(userID)
                                        .setTitle(dtObject.getTitle())
                                        .setPostUrl(dtObject.getPostUrl())
                                        .setCoverUrl(dtObject.getOriginalUrl())
                                        .setArtistName(dtObject.getArtistName())
                                        .setOriginalUrl(dtObject.getOriginalUrl())));

                    }

                    for (int i = 0; i < dataObject.getWallpaperList().size(); i++) {

                        DataObject dtObject = dataObject.getWallpaperList().get(i);
                        management.getDataFromDatabase(new DatabaseObject()
                                .setTypeOperation(Constant.TYPE.FOLLOWING)
                                .setDbOperation(Constant.DB.INSERT)
                                .setDataObject(new DataObject()
                                        .setId(dtObject.getId())
                                        .setUserId(userID)
                                        .setArtistId(dtObject.getArtistId())));

                    }

                }

            }
            if (requestObject.getConnection() == Constant.CONNECTION.ALL_FAVOURITES) {

                object = gson.fromJson(result, FavouriteJson.class);
                dataObject = DataObject.getWallpaperObject(requestObject, object);

                if (dataObject.getCode().equalsIgnoreCase(Constant.ErrorCodes.success_code)) {

                    String userID = "null";

                    Management management = new Management(this);
                    PrefObject prefObject = management.getPreferences(new PrefObject()
                            .setRetrieveUserId(true).setRetrieveLogin(true));


                    if (prefObject.isLogin()) {
                        userID = prefObject.getUserId();
                    }

                    for (int i = 0; i < dataObject.getWallpaperList().size(); i++) {

                        DataObject dtObject = dataObject.getWallpaperList().get(i);
                        management.getDataFromDatabase(new DatabaseObject()
                                .setTypeOperation(Constant.TYPE.FAVOURITES)
                                .setDbOperation(Constant.DB.INSERT)
                                .setDataObject(new DataObject()
                                        .setId(dtObject.getId())
                                        .setUserId(userID)
                                        .setTitle(dtObject.getTitle())
                                        .setPostUrl(dtObject.getPostUrl())
                                        .setCoverUrl(dtObject.getOriginalUrl())
                                        .setArtistName(dtObject.getArtistName())
                                        .setOriginalUrl(dtObject.getOriginalUrl())));

                    }


                }

            }
            else if (requestObject.getConnection() == Constant.CONNECTION.ALL_SUBCRIPTION) {

                object = gson.fromJson(result, SubscriptionJson.class);
                dataObject = DataObject.getWallpaperObject(requestObject, object);

                if (dataObject.getCode().equalsIgnoreCase(Constant.ErrorCodes.success_code)) {

                    String userID = "null";

                    Management management = new Management(this);
                    PrefObject prefObject = management.getPreferences(new PrefObject()
                            .setRetrieveUserId(true).setRetrieveLogin(true));


                    if (prefObject.isLogin()) {
                        userID = prefObject.getUserId();
                    }

                    for (int i = 0; i < dataObject.getWallpaperList().size(); i++) {

                        DataObject dtObject = dataObject.getWallpaperList().get(i);
                        management.getDataFromDatabase(new DatabaseObject()
                                .setTypeOperation(Constant.TYPE.FOLLOWING)
                                .setDbOperation(Constant.DB.INSERT)
                                .setDataObject(new DataObject()
                                        .setId(dtObject.getId())
                                        .setUserId(userID)
                                        .setArtistId(dtObject.getArtistId())));

                    }


                }

            }
            else if (requestObject.getConnection() == Constant.CONNECTION.ALL_CATEGORIES) {

                object = gson.fromJson(result, CategoriesJson.class);
                dataObject = DataObject.getWallpaperObject(requestObject, object);

                if (dataObject.getCode().equalsIgnoreCase(Constant.ErrorCodes.success_code)) {

                    HomeObject homeObject = new HomeObject()
                            .setData_type(Constant.DATA_TYPE.CATEGORIES)
                            .setDataObjectArrayList(dataObject.getWallpaperList());
                    Constant.homeObject = homeObject;

                }
            }*/

            if (dataObject == null)
                return;


        }

    }


}
