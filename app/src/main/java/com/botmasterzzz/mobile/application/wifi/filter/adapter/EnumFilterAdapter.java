package com.botmasterzzz.mobile.application.wifi.filter.adapter;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.util.EnumUtils;

import java.util.Set;

public abstract class EnumFilterAdapter<T extends Enum> extends BasicFilterAdapter<T> {
    private final Class<T> enumType;

    EnumFilterAdapter(@NonNull Class<T> enumType, @NonNull Set<T> values) {
        super(values);
        this.enumType = enumType;
    }

    @Override
    public boolean isActive() {
        return getValues().size() != EnumUtils.values(enumType).size();
    }

    public boolean toggle(@NonNull T object) {
        boolean toggle = false;
        if (contains(object)) {
            if (getValues().size() > 1) {
                toggle = getValues().remove(object);
            }
        } else {
            toggle = getValues().add(object);
        }
        return toggle;
    }

    @Override
    public void reset() {
        setValues(EnumUtils.values(enumType));
    }

    public abstract int getColor(@NonNull T object);

    boolean contains(@NonNull T object) {
        return getValues().contains(object);
    }

}
