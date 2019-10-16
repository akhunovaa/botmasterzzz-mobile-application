package com.botmasterzzz.mobile.application.wifi.scanner;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.MainContext;

import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;

class Cache {

    private static final int ADJUST = 10;
    private final Deque<List<ScanResult>> cachedScanResults = new ArrayDeque<>();
    private WifiInfo cachedWifiInfo = null;

    @NonNull
    List<CacheResult> getScanResults() {
        ScanResult current = null;
        int levelTotal = 0;
        int count = 0;
        List<CacheResult> results = new ArrayList<>();
        for (ScanResult scanResult : combineCache()) {
            if (current != null && !scanResult.BSSID.equals(current.BSSID)) {
                CacheResult cacheResult = getCacheResult(current, levelTotal, count);
                results.add(cacheResult);
                count = 0;
                levelTotal = 0;
            }
            current = scanResult;
            count++;
            levelTotal += scanResult.level;
        }
        if (current != null) {
            results.add(getCacheResult(current, levelTotal, count));
        }
        return results;
    }

    WifiInfo getWifiInfo() {
        return cachedWifiInfo;
    }

    @NonNull
    private CacheResult getCacheResult(@NonNull ScanResult current, int level, int count) {
        CacheResult cacheResult;
        if (isSizeAvailable()) {
            cacheResult = new CacheResult(current, level / count);
        } else {
            cacheResult = new CacheResult(current, (level - ADJUST) / count);
        }
        return cacheResult;
    }

    @NonNull
    private List<ScanResult> combineCache() {
        List<ScanResult> scanResults = new ArrayList<>();
        IterableUtils.forEach(cachedScanResults, new CacheClosure(scanResults));
        Collections.sort(scanResults, new ScanResultComparator());
        return scanResults;
    }

    void add(@NonNull List<ScanResult> scanResults, WifiInfo wifiInfo) {
        int cacheSize = getCacheSize();
        while (cachedScanResults.size() >= cacheSize) {
            cachedScanResults.pollLast();
        }
        cachedScanResults.addFirst(scanResults);
        cachedWifiInfo = wifiInfo;
    }

    @NonNull
    List<ScanResult> getFirst() {
        return cachedScanResults.getFirst();
    }

    @NonNull
    List<ScanResult> getLast() {
        return cachedScanResults.getLast();
    }

    int getCacheSize() {
        if (isSizeAvailable()) {
            int scanSpeed = MainContext.INSTANCE.getSettings().getScanSpeed();
            if (scanSpeed < 2) {
                return 4;
            }
            if (scanSpeed < 5) {
                return 3;
            }
            if (scanSpeed < 10) {
                return 2;
            }
        }
        return 1;
    }

    private boolean isSizeAvailable() {
        try {
            return MainContext.INSTANCE.getConfiguration().isSizeAvailable();
        } catch (Exception e) {
            return false;
        }
    }

    private static class ScanResultComparator implements Comparator<ScanResult> {
        @Override
        public int compare(ScanResult lhs, ScanResult rhs) {
            return new CompareToBuilder()
                .append(lhs.BSSID, rhs.BSSID)
                .append(lhs.level, rhs.level)
                .toComparison();
        }
    }

    private class CacheClosure implements Closure<List<ScanResult>> {
        private final List<ScanResult> scanResults;

        private CacheClosure(@NonNull List<ScanResult> scanResults) {
            this.scanResults = scanResults;
        }

        @Override
        public void execute(List<ScanResult> cachedScanResults) {
            scanResults.addAll(cachedScanResults);
        }
    }
}
