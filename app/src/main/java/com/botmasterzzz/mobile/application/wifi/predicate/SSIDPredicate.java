package com.botmasterzzz.mobile.application.wifi.predicate;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.wifi.model.WiFiDetail;

import org.apache.commons.collections4.Predicate;

class SSIDPredicate implements Predicate<WiFiDetail> {
    private final String ssid;

    SSIDPredicate(@NonNull String ssid) {
        this.ssid = ssid;
    }

    @Override
    public boolean evaluate(WiFiDetail object) {
        return object.getSSID().contains(ssid);
    }
}
