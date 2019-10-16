package com.botmasterzzz.mobile.application.wifi.filter;

import android.app.Dialog;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.application.wifi.band.WiFiBand;
import com.botmasterzzz.mobile.application.wifi.filter.adapter.WiFiBandAdapter;

import java.util.HashMap;
import java.util.Map;

class WiFiBandFilter extends EnumFilter<WiFiBand, WiFiBandAdapter> {
    static final Map<WiFiBand, Integer> ids = new HashMap<>();

    static {
        ids.put(WiFiBand.GHZ2, R.id.filterWifiBand2);
        ids.put(WiFiBand.GHZ5, R.id.filterWifiBand5);
    }

    WiFiBandFilter(@NonNull WiFiBandAdapter wiFiBandAdapter, @NonNull Dialog dialog) {
        super(ids, wiFiBandAdapter, dialog, R.id.filterWiFiBand);
    }
}
