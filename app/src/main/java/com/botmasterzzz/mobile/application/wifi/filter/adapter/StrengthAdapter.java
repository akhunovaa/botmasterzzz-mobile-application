package com.botmasterzzz.mobile.application.wifi.filter.adapter;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.settings.Settings;
import com.botmasterzzz.mobile.application.wifi.model.Strength;

import java.util.Set;

public class StrengthAdapter extends EnumFilterAdapter<Strength> {
    StrengthAdapter(@NonNull Set<Strength> values) {
        super(Strength.class, values);
    }

    @Override
    public int getColor(@NonNull Strength object) {
        return contains(object) ? object.colorResource() : object.colorResourceDefault();
    }

    @Override
    public void save(@NonNull Settings settings) {
        settings.saveStrengths(getValues());
    }
}
