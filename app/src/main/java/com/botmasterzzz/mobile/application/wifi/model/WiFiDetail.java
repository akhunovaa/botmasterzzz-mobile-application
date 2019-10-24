package com.botmasterzzz.mobile.application.wifi.model;

import androidx.annotation.NonNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class WiFiDetail implements Comparable<WiFiDetail> {
    public static final WiFiDetail EMPTY = new WiFiDetail(StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, WiFiSignal.EMPTY);
    private static final String SSID_EMPTY = "***";

    private final List<WiFiDetail> children;
    private final String SSID;
    private final String BSSID;
    private final String capabilities;
    private final WiFiSignal wiFiSignal;
    private final WiFiAdditional wiFiAdditional;
    private final Long createdTme;

    public WiFiDetail(@NonNull String SSID, @NonNull String BSSID, @NonNull String capabilities,
                      @NonNull WiFiSignal wiFiSignal, @NonNull WiFiAdditional wiFiAdditional) {
        this.SSID = SSID;
        this.BSSID = BSSID;
        this.capabilities = capabilities;
        this.wiFiSignal = wiFiSignal;
        this.wiFiAdditional = wiFiAdditional;
        this.children = new ArrayList<>();
        this.createdTme = System.currentTimeMillis();
    }

    public WiFiDetail(@NonNull String SSID, @NonNull String BSSID, @NonNull String capabilities, @NonNull WiFiSignal wiFiSignal) {
        this(SSID, BSSID, capabilities, wiFiSignal, WiFiAdditional.EMPTY);
    }

    public WiFiDetail(@NonNull WiFiDetail wiFiDetail, @NonNull WiFiAdditional wiFiAdditional) {
        this(wiFiDetail.SSID, wiFiDetail.BSSID, wiFiDetail.getCapabilities(), wiFiDetail.getWiFiSignal(), wiFiAdditional);
    }

    @NonNull
    public Security getSecurity() {
        return Security.findOne(capabilities);
    }

    @NonNull
    public String getSSID() {
        return isHidden() ? SSID_EMPTY : SSID;
    }

    boolean isHidden() {
        return StringUtils.isBlank(SSID);
    }

    @NonNull
    public String getBSSID() {
        return BSSID;
    }

    @NonNull
    public String getCapabilities() {
        return capabilities;
    }

    @NonNull
    public WiFiSignal getWiFiSignal() {
        return wiFiSignal;
    }

    @NonNull
    public WiFiAdditional getWiFiAdditional() {
        return wiFiAdditional;
    }

    @NonNull
    public List<WiFiDetail> getChildren() {
        return children;
    }

    public boolean noChildren() {
        return !getChildren().isEmpty();
    }

    @NonNull
    public String getTitle() {
        return String.format("%s (%s)", getSSID(), BSSID);
    }

    public void addChild(@NonNull WiFiDetail wiFiDetail) {
        children.add(wiFiDetail);
    }

    public Long getCreatedTme() {
        return createdTme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        WiFiDetail that = (WiFiDetail) o;

        return new EqualsBuilder()
            .append(getSSID(), that.getSSID())
            .append(getBSSID(), that.getBSSID())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(getSSID())
            .append(getBSSID())
            .toHashCode();
    }

    @Override
    public int compareTo(@NonNull WiFiDetail another) {
        return new CompareToBuilder()
            .append(getSSID(), another.getSSID())
            .append(getBSSID(), another.getBSSID())
            .toComparison();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}