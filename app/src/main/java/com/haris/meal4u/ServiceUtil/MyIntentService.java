package com.haris.meal4u.ServiceUtil;

import android.app.IntentService;
import android.content.Intent;

import com.google.gson.Gson;
import com.haris.meal4u.ConnectionUtil.Connection;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.GlobalDataObject;
import com.haris.meal4u.ObjectUtil.RequestObject;
import com.haris.meal4u.Utility.Utility;

import java.util.ArrayList;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class MyIntentService extends IntentService {
    private String TAG = MyIntentService.class.getName();

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Utility.Logger(TAG, "Setting : Working");

        if (intent != null) {


            //It load specific tags wallpaper at background
            //After loading it would add them into Wallpaper db

            RequestObject requestObject = GlobalDataObject.getRequestObject() != null ? GlobalDataObject.getRequestObject()
                    : (RequestObject) intent.getParcelableExtra(Constant.IntentKey.REQUEST_OBJECT);

            String result = Connection.makeRequest(requestObject.getServerUrl(), requestObject.getJson(), requestObject.getRequestType());


            Utility.Logger(TAG, "JSON = " + requestObject.getJson());

            GlobalDataObject.requestObject = null;

            if (Utility.isEmptyString(result))
                return;

            if (result.equalsIgnoreCase(Constant.ImportantMessages.CONNECTION_ERROR))
                return;

            Gson gson = new Gson();
            Object object = null;
            DataObject dataObject = null;
            ArrayList<Object> objectList = new ArrayList<>();

            /*if (requestObject.getConnection() == Constant.CONNECTION.USER_DATA) {

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
            else if (requestObject.getConnection() == Constant.CONNECTION.ALL_FAVOURITES) {

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
