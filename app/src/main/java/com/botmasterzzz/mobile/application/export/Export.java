package com.botmasterzzz.mobile.application.export;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.wifi.model.WiFiDetail;
import com.botmasterzzz.mobile.application.wifi.model.WiFiSignal;

import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.IterableUtils;

import java.util.List;
import java.util.Locale;

public class Export {
    private final List<WiFiDetail> wiFiDetails;
    private final String timestamp;

    public Export(@NonNull List<WiFiDetail> wiFiDetails, @NonNull String timestamp) {
        this.wiFiDetails = wiFiDetails;
        this.timestamp = timestamp;
    }

    @NonNull
    public String getData() {
        final StringBuilder result = new StringBuilder();
        result.append(
            String.format(Locale.ENGLISH,
                "Time Stamp|SSID|BSSID|Strength|Primary Channel|Primary Frequency|Center Channel|Center Frequency|Width (Range)|Distance|802.11mc|Security%n"));
        IterableUtils.forEach(wiFiDetails, new WiFiDetailClosure(timestamp, result));
        return result.toString();
    }

    private class WiFiDetailClosure implements Closure<WiFiDetail> {
        private final StringBuilder result;
        private final String timestamp;

        private WiFiDetailClosure(String timestamp, @NonNull StringBuilder result) {
            this.result = result;
            this.timestamp = timestamp;
        }

        @Override
        public void execute(WiFiDetail wiFiDetail) {
            WiFiSignal wiFiSignal = wiFiDetail.getWiFiSignal();
            result.append(String.format(Locale.ENGLISH, "%s|%s|%s|%ddBm|%d|%d%s|%d|%d%s|%d%s (%d - %d)|%s|%s|%s%n",
                timestamp,
                wiFiDetail.getSSID(),
                wiFiDetail.getBSSID(),
                wiFiSignal.getLevel(),
                wiFiSignal.getPrimaryWiFiChannel().getChannel(),
                wiFiSignal.getPrimaryFrequency(),
                WiFiSignal.FREQUENCY_UNITS,
                wiFiSignal.getCenterWiFiChannel().getChannel(),
                wiFiSignal.getCenterFrequency(),
                WiFiSignal.FREQUENCY_UNITS,
                wiFiSignal.getWiFiWidth().getFrequencyWidth(),
                WiFiSignal.FREQUENCY_UNITS,
                wiFiSignal.getFrequencyStart(),
                wiFiSignal.getFrequencyEnd(),
                wiFiSignal.getDistance(),
                wiFiSignal.is80211mc(),
                wiFiDetail.getCapabilities()));
        }
    }

}
