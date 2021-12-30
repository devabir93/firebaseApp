package com.ucas.android.firebaseapp.base;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.ucas.android.firebaseapp.utils.AppContextWrapper;
import com.ucas.android.firebaseapp.utils.AppPreferences;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {
    public FirebaseAuth mAuth;
    public FirebaseUser currentUser;
    public FirebaseFirestore db;
    public FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        String language = AppPreferences.getInstance(newBase).getStringPreferences(AppPreferences.SELECTED_LANGUAGE);
        super.attachBaseContext(AppContextWrapper.wrap(newBase, new Locale(language)));
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setAppLocale(AppPreferences.getInstance(this).getStringPreferences(AppPreferences.SELECTED_LANGUAGE));
    }
    public void setAppLocale(String localeCode) {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(new Locale(localeCode.toLowerCase()));
        } else {
            config.locale = new Locale(localeCode.toLowerCase());
        }
        resources.updateConfiguration(config, dm);
    }
}
