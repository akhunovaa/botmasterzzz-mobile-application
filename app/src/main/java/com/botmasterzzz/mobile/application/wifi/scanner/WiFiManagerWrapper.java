package com.botmasterzzz.mobile.application.wifi.scanner;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.List;

class WiFiManagerWrapper {
    private final WifiManager wifiManager;
    private WiFiSwitch wiFiSwitch;

    WiFiManagerWrapper(@NonNull WifiManager wifiManager) {
        this.wifiManager = wifiManager;
        this.wiFiSwitch = new WiFiSwitch(wifiManager);
    }

    boolean isWifiEnabled() {
        try {
            return wifiManager.isWifiEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    boolean enableWiFi() {
        try {
            return isWifiEnabled() || wiFiSwitch.setEnabled(true);
        } catch (Exception e) {
            return false;
        }
    }

    boolean disableWiFi() {
        try {
            return !isWifiEnabled() || wiFiSwitch.setEnabled(false);
        } catch (Exception e) {
            return false;
        }
    }

    @SuppressWarnings("deprecation")
    boolean startScan() {
        try {
            return wifiManager.startScan();
        } catch (Exception e) {
            return false;
        }
    }

    @NonNull
    List<ScanResult> scanResults() {
        try {
            List<ScanResult> results = wifiManager.getScanResults();
            return results == null ? Collections.emptyList() : results;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    WifiInfo wiFiInfo() {
        try {
            return wifiManager.getConnectionInfo();
        } catch (Exception e) {
            return null;
        }
    }

    void setWiFiSwitch(@NonNull WiFiSwitch wiFiSwitch) {
        this.wiFiSwitch = wiFiSwitch;
    }
}
