package com.botmasterzzz.mobile.application.wifi.filter.adapter;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.settings.Settings;

import java.util.Set;

public abstract class BasicFilterAdapter<T> {
    private Set<T> values;

    BasicFilterAdapter(@NonNull Set<T> values) {
        setValues(values);
    }

    @NonNull
    public Set<T> getValues() {
        return values;
    }

    public void setValues(@NonNull Set<T> values) {
        this.values = values;
    }

    abstract boolean isActive();

    abstract void reset();

    abstract void save(@NonNull Settings settings);
}
