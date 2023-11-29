package com.uniandes.vynilsmobile.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

public class LocaleUtils {

    public static final String LANG_ES = "es";
    public static void setLocale(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = new Configuration();
        configuration.setLocale(locale);

        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }
}
