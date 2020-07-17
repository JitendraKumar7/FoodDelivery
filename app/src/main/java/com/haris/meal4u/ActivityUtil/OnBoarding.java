package com.haris.meal4u.ActivityUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.haris.meal4u.AdapterUtil.FeaturePager;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.CustomUtil.ExtensiblePageIndicator;
import com.haris.meal4u.FragmentUtil.OnBoardFragment;
import com.haris.meal4u.ObjectUtil.OnBoardObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;

import java.util.ArrayList;

public class OnBoarding extends AppCompatActivity implements View.OnClickListener{
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private TextView txtSignUp;
    private TextView txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_on_boarding);

        txtLogin = findViewById(R.id.txt_login);
        txtSignUp = findViewById(R.id.txt_sign_up);


        fragmentArrayList.add(OnBoardFragment.getFragmentInstance(new OnBoardObject(R.drawable.ph_restaurant
                , Utility.getStringFromRes(this, R.string.title_01)
                , Utility.getStringFromRes(this, R.string.board_01))));

        fragmentArrayList.add(OnBoardFragment.getFragmentInstance(new OnBoardObject(R.drawable.ph_pizza
                , Utility.getStringFromRes(this, R.string.title_02)
                , Utility.getStringFromRes(this, R.string.board_02))));

        fragmentArrayList.add(OnBoardFragment.getFragmentInstance(new OnBoardObject(R.drawable.ph_delivery
                , Utility.getStringFromRes(this, R.string.title_03)
                , Utility.getStringFromRes(this, R.string.board_03))));

        //Initialize Pager & its indicator as well as connect it with its adapter

        ExtensiblePageIndicator flexibleIndicator = (ExtensiblePageIndicator) findViewById(R.id.flexibleIndicator);
        ViewPager viewPagerBoarding = (ViewPager) findViewById(R.id.view_pager_boarding);

        FeaturePager featurePager = new FeaturePager(getSupportFragmentManager(), fragmentArrayList);
        viewPagerBoarding.setAdapter(featurePager);

        flexibleIndicator.initViewPager(viewPagerBoarding);


        txtLogin.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);

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

            }
        }
    }

    @Override
    public void onClick(View v) {

        if (v == txtLogin) {
            startActivity(new Intent(this, Login.class));
            finish();
        }
        if (v == txtSignUp) {
            startActivity(new Intent(this, SignUp.class));
            finish();
        }

    }

}
