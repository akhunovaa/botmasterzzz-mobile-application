package com.botmasterzzz.mobile.application.wifi.scanner;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.util.BuildUtils;
import com.botmasterzzz.mobile.application.wifi.band.WiFiWidth;
import com.botmasterzzz.mobile.application.wifi.model.WiFiConnection;
import com.botmasterzzz.mobile.application.wifi.model.WiFiData;
import com.botmasterzzz.mobile.application.wifi.model.WiFiDetail;
import com.botmasterzzz.mobile.application.wifi.model.WiFiSignal;
import com.botmasterzzz.mobile.application.wifi.model.WiFiUtils;
import com.botmasterzzz.mobile.util.EnumUtils;

import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

class Transformer {

    @NonNull
    WiFiConnection transformWifiInfo(WifiInfo wifiInfo) {
        if (wifiInfo == null || wifiInfo.getNetworkId() == -1) {
            return WiFiConnection.EMPTY;
        }
        return new WiFiConnection(
            WiFiUtils.convertSSID(wifiInfo.getSSID()),
            wifiInfo.getBSSID(),
            WiFiUtils.convertIpAddress(wifiInfo.getIpAddress()),
            wifiInfo.getLinkSpeed());
    }

    @NonNull
    List<WiFiDetail> transformCacheResults(List<CacheResult> cacheResults) {
        return new ArrayList<>(CollectionUtils.collect(cacheResults, new ToWiFiDetail()));
    }

    @NonNull
    WiFiWidth getWiFiWidth(@NonNull ScanResult scanResult) {
        try {
            return EnumUtils.find(WiFiWidth.class, getFieldValue(scanResult, Fields.channelWidth), WiFiWidth.MHZ_20);
        } catch (Exception e) {
            return WiFiWidth.MHZ_20;
        }
    }

    int getCenterFrequency(@NonNull ScanResult scanResult, @NonNull WiFiWidth wiFiWidth) {
        try {
            int centerFrequency = getFieldValue(scanResult, Fields.centerFreq0);
            if (centerFrequency == 0) {
                centerFrequency = scanResult.frequency;
            } else if (isExtensionFrequency(scanResult, wiFiWidth, centerFrequency)) {
                centerFrequency = (centerFrequency + scanResult.frequency) / 2;
            }
            return centerFrequency;
        } catch (Exception e) {
            return scanResult.frequency;
        }
    }

    boolean isExtensionFrequency(@NonNull ScanResult scanResult, @NonNull WiFiWidth wiFiWidth, int centerFrequency) {
        return WiFiWidth.MHZ_40.equals(wiFiWidth) && Math.abs(scanResult.frequency - centerFrequency) >= WiFiWidth.MHZ_40.getFrequencyWidthHalf();
    }

    int getFieldValue(@NonNull ScanResult scanResult, @NonNull Fields field) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = scanResult.getClass().getDeclaredField(field.name());
        return (int) declaredField.get(scanResult);
    }

    private boolean is80211mc(@NonNull ScanResult scanResult) {
        return BuildUtils.isMinVersionM() && scanResult.is80211mcResponder();
    }

    @NonNull
    WiFiData transformToWiFiData(List<CacheResult> cacheResults, WifiInfo wifiInfo) {
        List<WiFiDetail> wiFiDetails = transformCacheResults(cacheResults);
        WiFiConnection wiFiConnection = transformWifiInfo(wifiInfo);
        return new WiFiData(wiFiDetails, wiFiConnection);
    }

    enum Fields {
        centerFreq0,
        //        centerFreq1,
        channelWidth
    }

    private class ToWiFiDetail implements org.apache.commons.collections4.Transformer<CacheResult, WiFiDetail> {
        @Override
        public WiFiDetail transform(CacheResult input) {
            ScanResult scanResult = input.getScanResult();
            WiFiWidth wiFiWidth = getWiFiWidth(scanResult);
            int centerFrequency = getCenterFrequency(scanResult, wiFiWidth);
            WiFiSignal wiFiSignal = new WiFiSignal(scanResult.frequency, centerFrequency, wiFiWidth, input.getLevelAverage(), is80211mc(scanResult));
            return new WiFiDetail(scanResult.SSID, scanResult.BSSID, scanResult.capabilities, wiFiSignal);
        }
    }

}
