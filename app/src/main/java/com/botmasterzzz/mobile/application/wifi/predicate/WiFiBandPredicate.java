package com.botmasterzzz.mobile.application.wifi.predicate;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.wifi.band.WiFiBand;
import com.botmasterzzz.mobile.application.wifi.model.WiFiDetail;

import org.apache.commons.collections4.Predicate;

public class WiFiBandPredicate implements Predicate<WiFiDetail> {
    private final WiFiBand wiFiBand;

    public WiFiBandPredicate(@NonNull WiFiBand wiFiBand) {
        this.wiFiBand = wiFiBand;
    }

    @Override
    public boolean evaluate(WiFiDetail object) {
        return object.getWiFiSignal().getWiFiBand().equals(wiFiBand);
    }

}
