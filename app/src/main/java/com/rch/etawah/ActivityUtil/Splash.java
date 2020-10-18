package com.rch.etawah.ActivityUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.rch.etawah.ConstantUtil.Constant;
import com.rch.etawah.ManagementUtil.Management;
import com.rch.etawah.ObjectUtil.PrefObject;
import com.rch.etawah.R;
import com.rch.etawah.Utility.Utility;


public class Splash extends AppCompatActivity {
    private String TAG = Splash.class.getName();
    private Management management;
    private PrefObject prefObject;


    /**
     * <p>It is used to check preference</p>
     */
    private void checkPreference() {


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


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        Utility.Logger(TAG, "Working");
        management = new Management(this);
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveFirstLaunch(true)
                .setRetrieveLogin(true)
                .setRetrieveUserCredential(true));

        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveLogin(true)
                .setRetrieveUserCredential(true));

        //Check Permission for Marshmallow version
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

            }


        }


        checkPreference();

    }

}
