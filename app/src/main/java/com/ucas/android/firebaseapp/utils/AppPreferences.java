package com.ucas.android.firebaseapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {

    private static AppPreferences sessionInstance;
    private SharedPreferences prefs;

    public static final String SELECTED_LANGUAGE = "language";

    public static AppPreferences getInstance(Context context) {
        if (sessionInstance == null) {
            sessionInstance = new AppPreferences(context);
        }
        return sessionInstance;
    }

    private AppPreferences(Context context) {
        prefs = context.getSharedPreferences("firebaseapp", Context.MODE_PRIVATE);
    }


    public boolean setStringPreferences(String key, String value) {
        return prefs.edit().putString(key, value).commit();
    }

    public String getStringPreferences(String key) {

        return prefs.getString(key, "");
    }

    public boolean setIntegerPreferences(String key, int value) {
        return prefs.edit().putInt(key, value).commit();
    }

    public int getIntegerPreferences(String key) {

        return prefs.getInt(key, -1);
    }

    public boolean setBooleanPreferences(String key, boolean value) {
        return prefs.edit().putBoolean(key, value).commit();
    }

    public boolean getBooleanPreferences(String key) {

        return prefs.getBoolean(key, false);
    }

    public boolean setLongPreferences(String key, Long value) {
        return prefs.edit().putLong(key, value).commit();
    }

    public Long getLongPreferences(String key) {

        return prefs.getLong(key, -1);
    }


}
