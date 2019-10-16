package com.botmasterzzz.mobile.application.wifi.channelgraph;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import com.botmasterzzz.mobile.application.wifi.band.WiFiChannel;
import com.botmasterzzz.mobile.application.wifi.model.WiFiDetail;

import org.apache.commons.collections4.Predicate;

class InRangePredicate implements Predicate<WiFiDetail> {
    private final Pair<WiFiChannel, WiFiChannel> wiFiChannelPair;

    InRangePredicate(@NonNull Pair<WiFiChannel, WiFiChannel> wiFiChannelPair) {
        this.wiFiChannelPair = wiFiChannelPair;
    }

    @Override
    public boolean evaluate(WiFiDetail object) {
        int frequency = object.getWiFiSignal().getCenterFrequency();
        return frequency >= wiFiChannelPair.first.getFrequency() && frequency <= wiFiChannelPair.second.getFrequency();
    }
}
