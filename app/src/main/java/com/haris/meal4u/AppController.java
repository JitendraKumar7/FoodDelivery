package com.haris.meal4u;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;

import com.facebook.appevents.AppEventsLogger;
import com.stripe.android.Stripe;

public class AppController extends Application {

    public static Stripe stripe;
    private static AppController mInstance;
    private static SharedPreferences mPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new ThreadHandeling());
        AppEventsLogger.activateApp(this);
        mInstance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static synchronized Stripe getStripe() {
        stripe = new Stripe(getInstance(), "pk_test_id6xW0qcPdM5p1O7kvDyTxTE00ZBy0EGGX");
        return stripe;
    }

    public static synchronized AppController getInstance() {

        return mInstance;
    }

    public synchronized SharedPreferences getPreference() {
        if (mPreferences == null) {
            mPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        }
        return mPreferences;
    }

}

