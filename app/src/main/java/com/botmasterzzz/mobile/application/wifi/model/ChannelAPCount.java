package com.botmasterzzz.mobile.application.wifi.model;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.wifi.band.WiFiChannel;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ChannelAPCount {
    private final WiFiChannel wiFiChannel;
    private final int count;

    public ChannelAPCount(@NonNull WiFiChannel wiFiChannel, int count) {
        this.wiFiChannel = wiFiChannel;
        this.count = count;
    }

    @NonNull
    public WiFiChannel getWiFiChannel() {
        return wiFiChannel;
    }

    int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
