package com.botmasterzzz.mobile.application.wifi.band;

import androidx.annotation.NonNull;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

class WiFiChannelCountryGHZ5 {
    private final SortedSet<Integer> channels;
    private final Map<String, SortedSet<Integer>> channelsToExclude;

    WiFiChannelCountryGHZ5() {
        SortedSet<Integer> channelsSet1 = new TreeSet<>(Arrays.asList(36, 40, 44, 48, 52, 56, 60, 64));
        SortedSet<Integer> channelsSet2 = new TreeSet<>(Arrays.asList(100, 104, 108, 112, 116, 120, 124, 128, 132, 136, 140, 144));
        SortedSet<Integer> channelsSet3 = new TreeSet<>(Arrays.asList(149, 153, 157, 161, 165));

        SortedSet<Integer> channelsToExcludeCanada = new TreeSet<>(Arrays.asList(120, 124, 128));
        SortedSet<Integer> channelsToExcludeIsrael = new TreeSet<>(channelsSet2);
        channelsToExcludeIsrael.addAll(channelsSet3);

        channelsToExclude = new HashMap<>();
        channelsToExclude.put("AU", channelsToExcludeCanada);   // Australia
        channelsToExclude.put("CA", channelsToExcludeCanada);   // Canada
        channelsToExclude.put("CN", channelsSet2);              // China
        channelsToExclude.put("IL", channelsToExcludeIsrael);   // Israel
        channelsToExclude.put("JP", channelsSet3);              // Japan
        channelsToExclude.put("KR", channelsSet2);              // South Korea
        channelsToExclude.put("TR", channelsSet3);              // Turkey
        channelsToExclude.put("ZA", channelsSet3);              // South Africa

        channels = new TreeSet<>(channelsSet1);
        channels.addAll(channelsSet2);
        channels.addAll(channelsSet3);
    }

    @NonNull
    SortedSet<Integer> findChannels(@NonNull String countryCode) {
        SortedSet<Integer> results = new TreeSet<>(channels);
        SortedSet<Integer> exclude = channelsToExclude.get(StringUtils.capitalize(countryCode));
        if (exclude != null) {
            results.removeAll(exclude);
        }
        return results;
    }

}
