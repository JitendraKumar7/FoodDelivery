package com.rch.etawah.ActivityUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.rch.etawah.ConstantUtil.Constant;
import com.rch.etawah.InterfaceUtil.ConnectionCallback;
import com.rch.etawah.ManagementUtil.Management;
import com.rch.etawah.ObjectUtil.DataObject;
import com.rch.etawah.ObjectUtil.PrefObject;
import com.rch.etawah.ObjectUtil.RequestObject;
import com.rch.etawah.R;
import com.rch.etawah.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DeeplinkViewer extends AppCompatActivity implements ConnectionCallback {

    private String TAG = DeeplinkViewer.class.getName();
    private String postId = "0";
    private String postType;
    private Management management;
    private Constant.CONNECTION connection;
    private Constant.CONNECTION_TYPE connectionType;
    private String functionality;
    private boolean isSendRequest = false;
    private String postName;
    private String userId;
    private String json;
    private PrefObject prefObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deeplink_viewer);

        getIntentData(); //Retrieve Intent Data
        initUI(); //Initialize UI

    }


    /**
     * <p> It is used to retrieve
     * Intent Data</p>
     */
    private void getIntentData() {

        Intent mainIntent = getIntent();

        if (mainIntent != null && mainIntent.getData() != null
                && (mainIntent.getData().getScheme().equals("http"))) {

            Uri data = mainIntent.getData();
            postId = data.getQueryParameter("post_id");
            postType = data.getQueryParameter("post_type");
            postName = data.getQueryParameter("post_name");
            userId = data.getQueryParameter("user_id");

            Utility.Logger(TAG, "Manual = " + data.getQueryParameter("post_id") + " Link = " +
                    data.toString());
            List<String> pathSegments = data.getPathSegments();

        } else {

            postId = mainIntent.getStringExtra(Constant.IntentKey.ARTIST_ID);
        }

    }


    /**
     * <p> It is used to retrieve
     * Intent Data</p>
     */
    private void initUI() {

        Utility.Logger(TAG, "Working");
        management = new Management(getApplicationContext());
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveParallaxMode(true));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
            ) {

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.ACCESS_NETWORK_STATE
                        , Manifest.permission.CAMERA

                }, Constant.RequestCode.PERMISSION_REQUEST_CODE);

            } else {

                checkPreference();

            }

        } else {


            checkPreference();

        }


    }


    /**
     * <p> It is used to convert Object
     * into Json</p>
     *
     * @param
     * @return
     */
    private String getJson(String functionality, String postID) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", functionality);
            jsonObject.accumulate("restaurant_id", postID);
            jsonObject.accumulate("latitude", "31.459660");
            jsonObject.accumulate("longitude", "73.123389");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
    }


    /**
     * <p> It is used to convert
     * Object into Json</p>
     *
     * @param
     * @return
     */
    private String getCoinJson(String functionality, String coinType, String userID, String coin) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", functionality);
            jsonObject.accumulate("coinType", coinType);
            jsonObject.accumulate("user_id", userID);
            jsonObject.accumulate("coin", coin);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger("JSON", json);

        return json;
    }


    @Override
    public void onSuccess(Object data, RequestObject requestObject) {
        if (data != null && requestObject != null) {

            DataObject dtObject = (DataObject) data;
            if (dtObject.getObjectArrayList().size() <= 0)
                return;

            DataObject dataObject = dtObject.getObjectArrayList().get(0);
            if (requestObject.getConnection() == Constant.CONNECTION.SPECIFIC_RESTAURANT) {

                Intent intent = new Intent(this, RestaurantDetail.class);
                intent.putExtra(Constant.IntentKey.RESTAURANT_DETAIL, dataObject);
                intent.putExtra(Constant.IntentKey.BACK_ACTION, true);
                startActivity(intent);
                finish();

            }
        }
    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        startActivity(new Intent(getApplicationContext(), Base.class));
        finish();
    }


    /**
     * <p>It is used to check preference</p>
     */
    private void checkPreference() {


        if (postType.equalsIgnoreCase(Constant.PostType.POST_TYPE)) {

            functionality = "specific_restaurant";
            connection = Constant.CONNECTION.SPECIFIC_RESTAURANT;
            json = getJson(functionality, postId);
            connectionType = Constant.CONNECTION_TYPE.UI;
            isSendRequest = true;

        }


        //Send request to Server

        management.sendRequestToServer(new RequestObject()
                .setContext(getApplicationContext())
                .setJson(json)
                .setConnection(connection)
                .setConnectionType(connectionType)
                .setConnectionCallback(this));


        if (postType.equalsIgnoreCase(Constant.PostType.REFER_USER)) {
            startActivity(new Intent(getApplicationContext(), Base.class));
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == Constant.RequestCode.PERMISSION_REQUEST_CODE) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    Utility.Toaster(this, Utility.getStringFromRes(this, R.string.external_storage_permission), Toast.LENGTH_SHORT);
                    return;
                }

                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    Utility.Toaster(this, Utility.getStringFromRes(this, R.string.external_storage_permission), Toast.LENGTH_SHORT);
                    return;
                }

                if (checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_DENIED) {
                    Utility.Toaster(this, Utility.getStringFromRes(this, R.string.read_phone_state_permission), Toast.LENGTH_SHORT);
                    return;
                }

                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    Utility.Toaster(this, Utility.getStringFromRes(this, R.string.need_camera_permission), Toast.LENGTH_SHORT);
                    return;
                }


                checkPreference();


            }


        }


    }


}
