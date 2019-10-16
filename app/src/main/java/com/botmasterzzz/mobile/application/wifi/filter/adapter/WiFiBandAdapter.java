package com.botmasterzzz.mobile.application.wifi.filter.adapter;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.application.settings.Settings;
import com.botmasterzzz.mobile.application.wifi.band.WiFiBand;

import java.util.Set;

public class WiFiBandAdapter extends EnumFilterAdapter<WiFiBand> {

    WiFiBandAdapter(@NonNull Set<WiFiBand> values) {
        super(WiFiBand.class, values);
    }

    @Override
    public int getColor(@NonNull WiFiBand object) {
        return contains(object) ? R.color.selected : R.color.regular;
    }

    @Override
    public void save(@NonNull Settings settings) {
        settings.saveWiFiBands(getValues());
    }
}
