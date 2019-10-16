package com.botmasterzzz.mobile.application.wifi.scanner;

import android.annotation.TargetApi;
import android.net.wifi.WifiManager;
import android.os.Build;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.ActivityUtils;
import com.botmasterzzz.mobile.application.util.BuildUtils;

class WiFiSwitch {
    private final WifiManager wifiManager;

    WiFiSwitch(@NonNull WifiManager wifiManager) {
        this.wifiManager = wifiManager;
    }

    boolean setEnabled(boolean enabled) {
        return BuildUtils.isMinVersionQ() ? enableWiFiAndroidQ() : enableWiFiLegacy(enabled);
    }

    @TargetApi(Build.VERSION_CODES.Q)
    private boolean enableWiFiAndroidQ() {
        ActivityUtils.startWiFiSettings();
        return true;
    }

    @SuppressWarnings("deprecation")
    private boolean enableWiFiLegacy(boolean enabled) {
        return wifiManager.setWifiEnabled(enabled);
    }

}
