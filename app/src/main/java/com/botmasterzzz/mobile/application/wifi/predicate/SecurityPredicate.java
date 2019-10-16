package com.botmasterzzz.mobile.application.wifi.predicate;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.wifi.model.Security;
import com.botmasterzzz.mobile.application.wifi.model.WiFiDetail;

import org.apache.commons.collections4.Predicate;

class SecurityPredicate implements Predicate<WiFiDetail> {
    private final Security security;

    SecurityPredicate(@NonNull Security security) {
        this.security = security;
    }

    @Override
    public boolean evaluate(WiFiDetail object) {
        return object.getSecurity().equals(security);
    }
}
