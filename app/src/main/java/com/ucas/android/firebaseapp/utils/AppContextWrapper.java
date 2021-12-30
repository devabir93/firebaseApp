package com.ucas.android.firebaseapp.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;

import androidx.annotation.RequiresApi;

import java.util.Locale;

/**
 * Created by Oways on 31/10/2017.
 */

public class AppContextWrapper extends ContextWrapper {
    public AppContextWrapper(Context base) {
        super(base);
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static ContextWrapper wrap(Context context, Locale newLocale) {
        Context newContext;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            newContext = updateResources(context, newLocale);
        } else {
            newContext = updateResourcesLegacy(context, newLocale);
        }

        return new ContextWrapper(newContext);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static Context updateResources(Context context, Locale locale) {
        LocaleList localeList = new LocaleList(locale);
        Configuration configuration = context.getResources().getConfiguration();
        LocaleList.setDefault(localeList);
        configuration.setLocales(localeList);

        return context.createConfigurationContext(configuration);
    }

    private static Context updateResourcesLegacy(Context context, Locale locale) {

        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        configuration.setLayoutDirection(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        return context;
    }
}
