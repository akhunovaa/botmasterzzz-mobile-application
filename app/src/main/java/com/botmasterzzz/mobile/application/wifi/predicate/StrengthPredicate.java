package com.botmasterzzz.mobile.application.wifi.predicate;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.wifi.model.Strength;
import com.botmasterzzz.mobile.application.wifi.model.WiFiDetail;

import org.apache.commons.collections4.Predicate;

class StrengthPredicate implements Predicate<WiFiDetail> {
    private final Strength strength;

    StrengthPredicate(@NonNull Strength strength) {
        this.strength = strength;
    }

    @Override
    public boolean evaluate(WiFiDetail object) {
        return object.getWiFiSignal().getStrength().equals(strength);
    }

}
