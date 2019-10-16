package com.botmasterzzz.mobile.application;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.botmasterzzz.mobile.application.settings.Settings;
import com.botmasterzzz.mobile.util.IntentUtils;

import static android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS;
import static android.provider.Settings.Panel.ACTION_WIFI;

public class ActivityUtils {
    private ActivityUtils() {
        throw new IllegalStateException("Utility class");
    }

    static void setActionBarOptions(ActionBar actionBar) {
        if (actionBar == null) {
            return;
        }
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    static void keepScreenOn() {
        MainContext mainContext = MainContext.INSTANCE;
        Settings settings = mainContext.getSettings();
        if (settings == null) {
            return;
        }
        MainActivity mainActivity = mainContext.getMainActivity();
        Window window = mainActivity.getWindow();
        if (settings.isKeepScreenOn()) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    @NonNull
    static Toolbar setupToolbar() {
        MainActivity mainActivity = MainContext.INSTANCE.getMainActivity();
        Toolbar toolbar = mainActivity.findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new WiFiBandToggle(mainActivity));
        mainActivity.setSupportActionBar(toolbar);
        setActionBarOptions(mainActivity.getSupportActionBar());
        return toolbar;
    }

    @TargetApi(Build.VERSION_CODES.Q)
    public static void startWiFiSettings() {
        MainActivity mainActivity = MainContext.INSTANCE.getMainActivity();
        Intent intent = IntentUtils.makeIntent(ACTION_WIFI);
        mainActivity.startActivityForResult(intent, 0);
    }

    static void startLocationSettings() {
        MainActivity mainActivity = MainContext.INSTANCE.getMainActivity();
        Intent intent = IntentUtils.makeIntent(ACTION_LOCATION_SOURCE_SETTINGS);
        mainActivity.startActivity(intent);
    }

    static class WiFiBandToggle implements View.OnClickListener {
        private final MainActivity mainActivity;

        WiFiBandToggle(@NonNull MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onClick(View view) {
            if (mainActivity.getCurrentNavigationMenu().isWiFiBandSwitchable()) {
                MainContext.INSTANCE.getSettings().toggleWiFiBand();
            }
        }
    }

}
