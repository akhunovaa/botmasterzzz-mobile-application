package com.botmasterzzz.mobile.application.wifi.scanner;

import android.net.wifi.ScanResult;

import androidx.annotation.NonNull;

class CacheResult {
    private final ScanResult scanResult;
    private final int levelAverage;

    CacheResult(@NonNull ScanResult scanResult, int levelAverage) {
        this.scanResult = scanResult;
        this.levelAverage = levelAverage;
    }

    @NonNull
    ScanResult getScanResult() {
        return scanResult;
    }

    int getLevelAverage() {
        return levelAverage;
    }
}
