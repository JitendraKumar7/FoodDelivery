package com.haris.meal4u.ActivityUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.CustomUtil.CurvedBottomNavigationView;
import com.haris.meal4u.FragmentUtil.DashboardFragment;
import com.haris.meal4u.FragmentUtil.Favourites;
import com.haris.meal4u.FragmentUtil.Setting;
import com.haris.meal4u.InterfaceUtil.ConnectionCallback;
import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.PrefObject;
import com.haris.meal4u.ObjectUtil.RequestObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;
import com.ixidev.gdpr.GDPRChecker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Base extends AppCompatActivity implements View.OnClickListener, ConnectionCallback, BottomNavigationView.OnNavigationItemSelectedListener {
    private PrefObject prefObject;
    private Management management;
    public static ArrayList<Object> objectArrayList = new ArrayList<>();
    private String TAG = Base.class.getName();
    private LinearLayout layoutSearch;
    private CurvedBottomNavigationView curvedBottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);


        initUI(); //Initialize UI

    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        management = new Management(this);

        layoutSearch = findViewById(R.id.layout_search);
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveLogin(true)
                .setRetrieveUserCredential(true));

        //Init bottom Navigation

        curvedBottomNavigationView = findViewById(R.id.customBottomBar);
        curvedBottomNavigationView.inflateMenu(R.menu.bottom_menu);

        Utility.Logger(TAG, "Generating Hash Key....");
        //Utility.printHashKeyForFacebook(this);  //For getting Facebook Hash Key

        sendServerRequest();  //Send Request to Server


        curvedBottomNavigationView.setOnNavigationItemSelectedListener(this);
        layoutSearch.setOnClickListener(this);

        curvedBottomNavigationView.setSelectedItemId(R.id.action_home);

    }


    /**
     * <p>It is used to open Fragment</p>
     *
     * @param fragment
     */
    public void openFragment(Fragment fragment) {

        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.layout_container, fragment);
            fragmentTransaction.commit();

        }
    }


    /**
     * <p>It is used to send Server Request</p>
     */
    private void sendServerRequest() {

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson())
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.ALL_CATEGORIES)
                .setConnectionCallback(this));


    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */
    private String getJson() {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "all_cuisines");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger("JSON", json);

        return json;
    }


    @Override
    public void onClick(View v) {
        if (v == layoutSearch) {
            startActivity(new Intent(this, Search.class));
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.action_home) {
            openFragment(new DashboardFragment());
            return true;
        }
        //
        else if (menuItem.getItemId() == R.id.action_nearby) {
            openFragment(new Fragment());
            return true;
        }
        //
        else if (menuItem.getItemId() == R.id.action_favourite) {

            prefObject = management.getPreferences(new PrefObject()
                    .setRetrieveLogin(true)
                    .setRetrieveUserCredential(true));

            if (!prefObject.isLogin()) {
                startActivity(new Intent(this, OnBoarding.class));
                return false;
            }

            openFragment(new Favourites());
            return true;
        }
        //
        else if (menuItem.getItemId() == R.id.action_setting) {
            openFragment(new Setting());
            return true;
        }

        return false;
    }


    @Override
    public void onSuccess(Object data, RequestObject requestObject) {
        if (data != null && requestObject != null) {

            DataObject dataObject = (DataObject) data;
            objectArrayList.clear();
            objectArrayList.addAll(dataObject.getObjectArrayList());


        }
    }

    @Override
    public void onError(String data, RequestObject requestObject) {

    }


    @Override
    public void onBackPressed() {
        ///super.onBackPressed();
    }
}
