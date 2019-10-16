package com.botmasterzzz.mobile.application;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import com.botmasterzzz.mobile.application.wifi.band.WiFiChannel;
import com.botmasterzzz.mobile.application.wifi.band.WiFiChannels;

public class Configuration {
    public static final int SIZE_MIN = 1024;
    public static final int SIZE_MAX = 4096;

    private final boolean largeScreen;
    private int size;
    private Pair<WiFiChannel, WiFiChannel> wiFiChannelPair;

    public Configuration(boolean largeScreen) {
        this.largeScreen = largeScreen;
        setSize(SIZE_MAX);
        setWiFiChannelPair(WiFiChannels.UNKNOWN);
    }

    public boolean isLargeScreen() {
        return largeScreen;
    }

    @NonNull
    public Pair<WiFiChannel, WiFiChannel> getWiFiChannelPair() {
        return wiFiChannelPair;
    }

    public void setWiFiChannelPair(@NonNull Pair<WiFiChannel, WiFiChannel> wiFiChannelPair) {
        this.wiFiChannelPair = wiFiChannelPair;
    }

    public boolean isSizeAvailable() {
        return size == SIZE_MAX;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
