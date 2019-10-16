package com.botmasterzzz.mobile.application.wifi;

import androidx.annotation.NonNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class WiFiAdditional {
    public static final WiFiAdditional EMPTY = new WiFiAdditional(StringUtils.EMPTY);

    private final String vendorName;
    private final WiFiConnection wiFiConnection;

    public WiFiAdditional(@NonNull String vendorName, @NonNull WiFiConnection wiFiConnection) {
        this.vendorName = vendorName;
        this.wiFiConnection = wiFiConnection;
    }

    public WiFiAdditional(@NonNull String vendorName) {
        this(vendorName, WiFiConnection.EMPTY);
    }

    @NonNull
    public String getVendorName() {
        return vendorName;
    }

    @NonNull
    public WiFiConnection getWiFiConnection() {
        return wiFiConnection;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}