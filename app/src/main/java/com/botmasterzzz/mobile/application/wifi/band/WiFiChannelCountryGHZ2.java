package com.botmasterzzz.mobile.application.wifi.band;

import androidx.annotation.NonNull;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

class WiFiChannelCountryGHZ2 {
    private final Set<String> countries;
    private final SortedSet<Integer> channels;
    private final SortedSet<Integer> world;

    WiFiChannelCountryGHZ2() {
        countries = new HashSet<>(Arrays.asList(
            "AS", "CA", "CO", "DO", "FM", "GT", "GU", "MP", "MX", "PA", "PR", "UM", "US", "UZ", "VI")
        );
        channels = new TreeSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
        world = new TreeSet<>(channels);
        world.add(12);
        world.add(13);
    }

    @NonNull
    SortedSet<Integer> findChannels(@NonNull String countryCode) {
        SortedSet<Integer> results = new TreeSet<>(world);
        String code = StringUtils.capitalize(countryCode);
        if (countries.contains(code)) {
            results = new TreeSet<>(channels);
        }
        return results;
    }

}