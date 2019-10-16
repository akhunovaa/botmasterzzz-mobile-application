package com.botmasterzzz.mobile.application.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import androidx.annotation.NonNull;

import java.util.Locale;

public class ConfigurationUtils {
    private ConfigurationUtils() {
        throw new IllegalStateException("Utility class");
    }

    @NonNull
    public static Context createContext(@NonNull Context context, @NonNull Locale newLocale) {
        return
            BuildUtils.isMinVersionN()
                ? createContextAndroidN(context, newLocale)
                : createContextLegacy(context, newLocale);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @NonNull
    private static Context createContextAndroidN(@NonNull Context context, @NonNull Locale newLocale) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(newLocale);
        return context.createConfigurationContext(configuration);
    }

    @NonNull
    @SuppressWarnings("deprecation")
    private static Context createContextLegacy(@NonNull Context context, @NonNull Locale newLocale) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = newLocale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }

}
