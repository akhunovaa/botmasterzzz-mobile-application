package com.botmasterzzz.mobile.application.wifi.model;

import androidx.annotation.NonNull;

import org.apache.commons.lang3.builder.CompareToBuilder;

import java.util.Comparator;
import java.util.Locale;

public enum GroupBy {
    NONE(new None(), new None()),
    SSID(new SSIDSortOrder(), new SSIDGroupBy()),
    CHANNEL(new ChannelSortOrder(), new ChannelGroupBy());

    private final Comparator<WiFiDetail> sortOrderComparator;
    private final Comparator<WiFiDetail> groupByComparator;

    GroupBy(@NonNull Comparator<WiFiDetail> sortOrderComparator, @NonNull Comparator<WiFiDetail> groupByComparator) {
        this.sortOrderComparator = sortOrderComparator;
        this.groupByComparator = groupByComparator;
    }

    @NonNull
    Comparator<WiFiDetail> sortOrderComparator() {
        return sortOrderComparator;
    }

    @NonNull
    Comparator<WiFiDetail> groupByComparator() {
        return groupByComparator;
    }

    static class None implements Comparator<WiFiDetail> {
        @Override
        public int compare(WiFiDetail lhs, WiFiDetail rhs) {
            return lhs.equals(rhs) ? 0 : 1;
        }
    }

    static class SSIDSortOrder implements Comparator<WiFiDetail> {
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

    static class SSIDGroupBy implements Comparator<WiFiDetail> {
        @Override
        public int compare(WiFiDetail lhs, WiFiDetail rhs) {
            Locale locale = Locale.getDefault();
            return new CompareToBuilder()
                .append(lhs.getSSID().toUpperCase(locale), rhs.getSSID().toUpperCase(locale))
                .toComparison();
        }
    }

    static class ChannelSortOrder implements Comparator<WiFiDetail> {
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

    static class ChannelGroupBy implements Comparator<WiFiDetail> {
        @Override
        public int compare(WiFiDetail lhs, WiFiDetail rhs) {
            return new CompareToBuilder()
                .append(lhs.getWiFiSignal().getPrimaryWiFiChannel().getChannel(), rhs.getWiFiSignal().getPrimaryWiFiChannel().getChannel())
                .toComparison();
        }
    }

}
