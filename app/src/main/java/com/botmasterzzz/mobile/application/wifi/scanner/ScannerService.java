package com.botmasterzzz.mobile.application.wifi.scanner;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.wifi.model.WiFiData;

public interface ScannerService {
    void update();

    @NonNull
    WiFiData getWiFiData();

    void register(@NonNull UpdateNotifier updateNotifier);

    void unregister(@NonNull UpdateNotifier updateNotifier);

    void pause();

    boolean isRunning();

    void resume();

    void stop();
}
