package com.botmasterzzz.mobile.application.wifi.model;

import androidx.annotation.NonNull;

import org.apache.commons.lang3.builder.CompareToBuilder;

import java.util.Comparator;
import java.util.Locale;

public enum SortBy {
    STRENGTH(new StrengthComparator()),
    SSID(new SSIDComparator()),
    CHANNEL(new ChannelComparator());

    private final Comparator<WiFiDetail> comparator;

    SortBy(@NonNull Comparator<WiFiDetail> comparator) {
        this.comparator = comparator;
    }

    @NonNull
    Comparator<WiFiDetail> comparator() {
        return comparator;
    }

    static class StrengthComparator implements Comparator<WiFiDetail> {
        @Override
        public int compare(WiFiDetail lhs, WiFiDetail rhs) {
            Locale locale = Locale.getDefault();
            return new CompareToBuilder()
                .append(rhs.getWiFiSignal().getLevel(), lhs.getWiFiSignal().getLevel())
                .append(lhs.getSSID().toUpperCase(locale), rhs.getSSID().toUpperCase(locale))
                .append(lhs.getBSSID().toUpperCase(locale), rhs.getBSSID().toUpperCase(locale))
                .toComparison();
        }
    }


    static class SSIDComparator implements Comparator<WiFiDetail> {
        @Override
        public int compare(WiFiDetail lhs, WiFiDetail rhs) {
            Locale locale = Locale.getDefault();
            return new CompareToBuilder()
                .append(lhs.getSSID().toUpperCase(locale), rhs.getSSID().toUpperCase(locale))
                .append(rhs.getWiFiSignal().getLevel(), lhs.getWiFiSignal().getLevel())
                .append(lhs.getBSSID().toUpperCase(locale), rhs.getBSSID().toUpperCase(locale))
                .toComparison();
        }
    }

    static class ChannelComparator implements Comparator<WiFiDetail> {
        @Override
        public int compare(WiFiDetail lhs, WiFiDetail rhs) {
            Locale locale = Locale.getDefault();
            return new CompareToBuilder()
                .append(lhs.getWiFiSignal().getPrimaryWiFiChannel().getChannel(), rhs.getWiFiSignal().getPrimaryWiFiChannel().getChannel())
                .append(rhs.getWiFiSignal().getLevel(), lhs.getWiFiSignal().getLevel())
                .append(lhs.getSSID().toUpperCase(locale), rhs.getSSID().toUpperCase(locale))
                .append(lhs.getBSSID().toUpperCase(locale), rhs.getBSSID().toUpperCase(locale))
                .toComparison();
        }
    }

}
