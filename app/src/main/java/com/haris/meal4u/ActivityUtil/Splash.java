package com.haris.meal4u.ActivityUtil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.InterfaceUtil.ConnectionCallback;
import com.haris.meal4u.InterfaceUtil.InternetCallback;
import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.PrefObject;
import com.haris.meal4u.ObjectUtil.RequestObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;


import static io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider.REQUEST_CHECK_SETTINGS;

public class Splash extends AppCompatActivity implements ConnectionCallback, InternetCallback {
    private Management management;
    private PrefObject prefObject;
    private LinearLayout layoutConfigure;
    private String TAG = Splash.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);


        layoutConfigure = findViewById(R.id.layout_configure);
        Utility.Logger(TAG, "Working");
        management = new Management(this);
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveFirstLaunch(true)
                .setRetrieveLogin(true)
                .setRetrieveUserCredential(true));

        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveLogin(true)
                .setRetrieveUserCredential(true));

        if (!prefObject.isLogin()) {
            startActivity(new Intent(getApplicationContext(), OnBoarding.class));
            finish();
            return;
        }

        //Check Permission for Marshmallow version

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED
            ) {

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.ACCESS_NETWORK_STATE
                        , Manifest.permission.CAMERA
                        , Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.ACCESS_COARSE_LOCATION

                }, Constant.RequestCode.PERMISSION_REQUEST_CODE);

            } else {
                checkPreference();
            }

        } else {
            checkPreference();
        }


    }


    /**
     * <p>It is used to check preference</p>
     */
    private void checkPreference() {


        if (Utility.isLocationProviderAvailable(getApplicationContext(), true)) {
            prefObject = management.getPreferences(new PrefObject()
                    .setRetrieveLogin(true)
                    .setRetrieveUserCredential(true));

            if (prefObject.isLogin()) {
                startActivity(new Intent(getApplicationContext(), Base.class));
                finish();
            } else {
                startActivity(new Intent(getApplicationContext(), OnBoarding.class));
                finish();
            }
        } else
            triggerLocationSettingAlert();


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

                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                    Utility.Toaster(this, Utility.getStringFromRes(this, R.string.need_camera_permission), Toast.LENGTH_SHORT);
                    return;
                }


                checkPreference();


            }


        }


    }

    @Override
    public void onSuccess(Object data, RequestObject requestObject) {

        if (data != null && requestObject != null) {

            DataObject dataObject = (DataObject) data;


            //Initialize Admob App ID

            //MobileAds.initialize(this, Constant.Credentials.ADMOB_APP_ID);
            layoutConfigure.setVisibility(View.GONE);


            //Check either it's first launch or not

           /* if (prefObject.isFirstLaunch()) {

                management.savePreferences(new PrefObject()
                        .setFirstLaunch(false)
                        .setSaveFirstLaunch(true));

                startActivity(new Intent(this, FeedTopic.class));
                finish();

            } else {

                if (prefObject.isLogin()) {
                    management.getDataFromDatabase(new DatabaseObject()
                            .setTypeOperation(Constant.TYPE.REWARD)
                            .setDbOperation(Constant.DB.UPDATE)
                            .setDataObject(new DataObject()
                                    .setUserId(prefObject.getUserId())
                                    .setCoin(dataObject.getCoin())));

                }*/

            startActivity(new Intent(this, Base.class));
            finish();

        }


    }


    @Override
    public void onError(String data, RequestObject requestObject) {

        layoutConfigure.setVisibility(View.GONE);
        startActivity(new Intent(this, Base.class));
        finish();


    }

    @Override
    public void onConnectivityFailed() {

        startActivity(new Intent(this, Base.class));
        finish();

    }

    /**
     * <p>It is used to trigger location Setting Alert</p>
     */
    private void triggerLocationSettingAlert() {
        Utility.Logger("Working", "Alert Working");
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(createLocationRequest());
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(Splash.this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Utility.Logger("OnSuccess", locationSettingsResponse.getLocationSettingsStates().isLocationPresent() + " " + locationSettingsResponse.getLocationSettingsStates().isLocationUsable());
                if (locationSettingsResponse.getLocationSettingsStates().isGpsUsable() ||
                        locationSettingsResponse.getLocationSettingsStates().isNetworkLocationUsable() ||
                        locationSettingsResponse.getLocationSettingsStates().isLocationUsable()
                ) {

                    /*management.sendRequestToServer(new RequestObject()
                            .setJson(getJson("31.459660", "73.123389"))
                            .setConnectionType(Constant.CONNECTION_TYPE.UI)
                            .setConnection(Constant.CONNECTION.CONFIGURATION)
                            .setInternetCallback(null)
                            .setConnectionCallback(Splash.this));*/

                    ///if (Utility.isLocationProviderAvailable(getApplicationContext(), true)) {
                    startActivity(new Intent(getApplicationContext(), Base.class));
                    finish();


                }
            }
        });
        task.addOnCompleteListener(Splash.this, new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                Utility.Logger("onComplete", "Success");

                startActivity(new Intent(getApplicationContext(), Base.class));
                finish();

            }
        });
        task.addOnFailureListener(Splash.this, new OnFailureListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException) e).getStatusCode();
                switch (statusCode) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Utility.Logger(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                "location settings ");
                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the
                            // result in onActivityResult().
                            ResolvableApiException rae = (ResolvableApiException) e;
                            rae.startResolutionForResult(Splash.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException sie) {
                            Utility.Logger(TAG, "PendingIntent unable to execute request.");
                        }


                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            ResolvableApiException resolvable = (ResolvableApiException) e;

                            resolvable.startResolutionForResult(Splash.this,
                                    Constant.RequestCode.REQUEST_LOCATION);

                        } catch (IntentSender.SendIntentException sendEx) {
                            // Ignore the error.
                            sendEx.printStackTrace();
                        }

                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        String errorMessage = "Location settings are inadequate, and cannot be " +
                                "fixed here. Fix in Settings.";
                        Utility.Logger(TAG, errorMessage);

                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            ResolvableApiException resolvable = (ResolvableApiException) e;

                            resolvable.startResolutionForResult(Splash.this,
                                    Constant.RequestCode.REQUEST_LOCATION);

                        } catch (IntentSender.SendIntentException sendEx) {
                            // Ignore the error.
                            sendEx.printStackTrace();
                        }

                        break;
                    //Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            }


        });


    }


    /**
     * <p>It is used to create location request</p>
     *
     * @return
     */
    private LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        return mLocationRequest;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.RequestCode.REQUEST_LOCATION) {
            switch (resultCode) {
                case Activity.RESULT_OK: {
                    // All required changes were successfully made
                    Utility.Logger("Permission", "Success");

                    startActivity(new Intent(getApplicationContext(), Base.class));
                    finish();

                    break;
                }
                case Activity.RESULT_CANCELED: {
                    // The userObject was asked to change settings, but chose not to
                    Utility.Logger("Permission", "Failure");
                    //triggerLocationSettingAlert();

                    break;
                }
                default: {
                    break;
                }
            }

        }
    }
}
