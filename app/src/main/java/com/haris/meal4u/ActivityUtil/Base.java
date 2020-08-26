package com.haris.meal4u.ActivityUtil;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.haris.meal4u.CustomUtil.CurvedBottomNavigationView;
import com.haris.meal4u.FragmentUtil.BookingFragment;
import com.haris.meal4u.FragmentUtil.DashboardFragment;
import com.haris.meal4u.FragmentUtil.ListOfOrderFragment;
import com.haris.meal4u.FragmentUtil.SettingFragment;
import com.haris.meal4u.FragmentUtil.WalletFragment;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;

import java.util.ArrayList;

public class Base extends AppCompatActivity implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {
    public static ArrayList<Object> objectArrayList = new ArrayList<>();
    private LinearLayout layoutSearch;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        layoutSearch = findViewById(R.id.layout_search);

        CurvedBottomNavigationView curvedBottomNavigationView = findViewById(R.id.customBottomBar);
        curvedBottomNavigationView.inflateMenu(R.menu.bottom_menu);

        curvedBottomNavigationView.setOnNavigationItemSelectedListener(this);
        layoutSearch.setOnClickListener(this);

        curvedBottomNavigationView.setSelectedItemId(R.id.action_home);

        openFragment(new DashboardFragment());

    }

    public void openFragment(Fragment fragment) {

        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.layout_container, fragment);
            fragmentTransaction.commit();

        }
    }

    @Override
    public void onClick(View v) {
        if (v == layoutSearch) {
            if (!(getSupportFragmentManager().findFragmentById(R.id.layout_container) instanceof DashboardFragment)) {
                openFragment(new DashboardFragment());
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.action_home) {
            openFragment(new Fragment());
            return true;
        }
        //
        else if (menuItem.getItemId() == R.id.action_nearby) {
            openFragment(new ListOfOrderFragment());
            return true;
        }
        //
        else if (menuItem.getItemId() == R.id.action_booking) {
            openFragment(new BookingFragment());
            //openFragment(new ProductCartFragment());
            return true;
        }
        else if (menuItem.getItemId() == R.id.action_wallet) {
            openFragment(new WalletFragment());
            //openFragment(new ProductCartFragment());
            return true;
        }
        //
        else if (menuItem.getItemId() == R.id.action_setting) {
            openFragment(new SettingFragment());
            return true;
        }

        return false;
    }

}
