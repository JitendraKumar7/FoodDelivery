package com.haris.meal4u.Utility;

import com.haris.meal4u.AppController;

public class SharedPreference {

    public static int getInteger(String key) {
        return AppController
                .getInstance()
                .getPreference()
                .getInt(key, 0);
    }

    public static String getString(String key) {
        return AppController
                .getInstance()
                .getPreference()
                .getString(key, null);
    }

    public static boolean getBoolean(String key) {
        return AppController
                .getInstance()
                .getPreference()
                .getBoolean(key, false);
    }

    public static void clearPreferences(String key) {
        AppController.getInstance()
                .getPreference().edit()
                .remove(key)
                .apply();
    }

    public static void putInteger(String key, int value) {
        AppController.getInstance()
                .getPreference().edit()
                .putInt(key, value)
                .apply();
    }

    public static void putString(String key, String value) {
        AppController.getInstance()
                .getPreference().edit()
                .putString(key, value)
                .apply();
    }

    public static void putBoolean(String key, boolean value) {
        AppController.getInstance()
                .getPreference().edit()
                .putBoolean(key, value)
                .apply();

    }

}

