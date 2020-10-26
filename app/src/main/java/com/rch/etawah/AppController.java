package com.rch.etawah;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;

public class AppController extends MultiDexApplication {

    private static AppController mInstance;
    private static SharedPreferences mPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new ThreadHandeling());
        mInstance = this;
    }

    public synchronized SharedPreferences getPreference() {
        if (mPreferences == null) {
            mPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        }
        return mPreferences;
    }

    public static synchronized AppController getInstance() {

        return mInstance;
    }

}

