package com.ucas.android.firebaseapp.utils;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

public class MyApplication extends Application {
    public static final String TAG = MyApplication.class.getSimpleName();
    static MyApplication mAppInstance;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppInstance = this;
        AppPreferences preferences = AppPreferences.getInstance(this);
        String language = preferences.getStringPreferences(AppPreferences.SELECTED_LANGUAGE);
        if (TextUtils.isEmpty(language)) {
            language = "ar";
            preferences.setStringPreferences(AppPreferences.SELECTED_LANGUAGE, language);
        }
    }


    public static synchronized MyApplication getInstance() {
        return mAppInstance;
    }

}
