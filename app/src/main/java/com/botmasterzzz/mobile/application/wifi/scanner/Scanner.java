package com.botmasterzzz.mobile.application.wifi.scanner;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.settings.Settings;
import com.botmasterzzz.mobile.application.wifi.model.WiFiData;

import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.IterableUtils;

import java.util.ArrayList;
import java.util.List;

class Scanner implements ScannerService {
    private final List<UpdateNotifier> updateNotifiers;
    private final Settings settings;
    private Transformer transformer;
    private WiFiData wiFiData;
    private WiFiManagerWrapper wiFiManagerWrapper;
    private Cache cache;
    private PeriodicScan periodicScan;

    Scanner(@NonNull WiFiManagerWrapper wiFiManagerWrapper, @NonNull Handler handler, @NonNull Settings settings) {
        this.updateNotifiers = new ArrayList<>();
        this.wiFiManagerWrapper = wiFiManagerWrapper;
        this.settings = settings;
        this.wiFiData = WiFiData.EMPTY;
        this.setTransformer(new Transformer());
        this.setCache(new Cache());
        this.periodicScan = new PeriodicScan(this, handler, settings);
    }

    @Override
    public void update() {
        wiFiManagerWrapper.enableWiFi();
        scanResults();
        wiFiData = transformer.transformToWiFiData(cache.getScanResults(), cache.getWifiInfo());
        IterableUtils.forEach(updateNotifiers, new UpdateClosure());
    }

    @Override
    @NonNull
    public WiFiData getWiFiData() {
        return wiFiData;
    }

    @Override
    public void register(@NonNull UpdateNotifier updateNotifier) {
        updateNotifiers.add(updateNotifier);
    }

    @Override
    public void unregister(@NonNull UpdateNotifier updateNotifier) {
        updateNotifiers.remove(updateNotifier);
    }

    @Override
    public void pause() {
        periodicScan.stop();
    }

    @Override
    public boolean isRunning() {
        return periodicScan.isRunning();
    }

    @Override
    public void resume() {
        periodicScan.start();
    }

    @Override
    public void stop() {
        if (settings.isWiFiOffOnExit()) {
            wiFiManagerWrapper.disableWiFi();
        }
    }

    @NonNull
    PeriodicScan getPeriodicScan() {
        return periodicScan;
    }

    void setPeriodicScan(@NonNull PeriodicScan periodicScan) {
        this.periodicScan = periodicScan;
    }

    void setCache(@NonNull Cache cache) {
        this.cache = cache;
    }

    void setTransformer(@NonNull Transformer transformer) {
        this.transformer = transformer;
    }

    @NonNull
    List<UpdateNotifier> getUpdateNotifiers() {
        return updateNotifiers;
    }

    private void scanResults() {
        try {
            if (wiFiManagerWrapper.startScan()) {
                List<ScanResult> scanResults = wiFiManagerWrapper.scanResults();
                WifiInfo wifiInfo = wiFiManagerWrapper.wiFiInfo();
                cache.add(scanResults, wifiInfo);
            }
        } catch (Exception e) {
            // error
        }
    }

    private class UpdateClosure implements Closure<UpdateNotifier> {
        @Override
        public void execute(UpdateNotifier updateNotifier) {
            updateNotifier.update(wiFiData);
        }
    }
}
