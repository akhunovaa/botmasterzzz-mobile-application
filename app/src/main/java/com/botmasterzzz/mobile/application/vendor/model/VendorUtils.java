package com.botmasterzzz.mobile.application.vendor.model;

import androidx.annotation.NonNull;

import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

class VendorUtils {
    static final int MAX_SIZE = 6;
    private static final String SEPARATOR = ":";

    private VendorUtils() {
        throw new IllegalStateException("Utility class");
    }

    @NonNull
    static String clean(String macAddress) {
        if (macAddress == null) {
            return StringUtils.EMPTY;
        }
        String result = macAddress.replace(SEPARATOR, "");
        Locale locale = Locale.getDefault();
        return result.substring(0, Math.min(result.length(), MAX_SIZE)).toUpperCase(locale);
    }

    @NonNull
    static String toMacAddress(String source) {
        if (source == null) {
            return StringUtils.EMPTY;
        }
        if (source.length() < MAX_SIZE) {
            return "*" + source + "*";
        }
        return source.substring(0, 2)
            + SEPARATOR + source.substring(2, 4)
            + SEPARATOR + source.substring(4, 6);
    }

}
