package com.botmasterzzz.mobile.application.wifi;

import androidx.annotation.NonNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class WiFiConnection {
    public static final int LINK_SPEED_INVALID = -1;
    public static final WiFiConnection EMPTY = new WiFiConnection(StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, LINK_SPEED_INVALID);

    private final String SSID;
    private final String BSSID;
    private final String ipAddress;
    private final int linkSpeed;

    public WiFiConnection(@NonNull String SSID, @NonNull String BSSID, @NonNull String ipAddress, int linkSpeed) {
        this.SSID = SSID;
        this.BSSID = BSSID;
        this.ipAddress = ipAddress;
        this.linkSpeed = linkSpeed;
    }

    @NonNull
    public String getSSID() {
        return SSID;
    }

    @NonNull
    public String getBSSID() {
        return BSSID;
    }

    @NonNull
    public String getIpAddress() {
        return ipAddress;
    }

    public int getLinkSpeed() {
        return linkSpeed;
    }

    public boolean isConnected() {
        return !EMPTY.equals(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        WiFiConnection that = (WiFiConnection) o;

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
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
