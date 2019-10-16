package com.botmasterzzz.mobile.application.wifi.scanner;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.MainActivity;
import com.botmasterzzz.mobile.application.settings.Settings;

public class ScannerServiceFactory {
    private ScannerServiceFactory() {
        throw new IllegalStateException("Factory class");
    }

    @NonNull
    public static ScannerService makeScannerService(
        @NonNull MainActivity mainActivity,
        @NonNull Handler handler,
        @NonNull Settings settings) {

        Context context = mainActivity.getApplicationContext();
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WiFiManagerWrapper wiFiManagerWrapper = new WiFiManagerWrapper(wifiManager);

        return new Scanner(wiFiManagerWrapper, handler, settings);
    }
}
