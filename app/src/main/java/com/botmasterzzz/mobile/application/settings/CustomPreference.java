package com.botmasterzzz.mobile.application.settings;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.preference.ListPreference;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;

import java.util.ArrayList;
import java.util.List;

class CustomPreference extends ListPreference {
    CustomPreference(@NonNull Context context, AttributeSet attrs, @NonNull List<Data> data, @NonNull String defaultValue) {
        super(context, attrs);
        setEntries(getNames(data));
        setEntryValues(getCodes(data));
        setDefaultValue(defaultValue);
    }

    @NonNull
    private CharSequence[] getCodes(@NonNull List<Data> data) {
        return new ArrayList<>(CollectionUtils.collect(data, new ToCode())).toArray(new CharSequence[]{});
    }

    @NonNull
    private CharSequence[] getNames(@NonNull List<Data> data) {
        return new ArrayList<>(CollectionUtils.collect(data, new ToName())).toArray(new CharSequence[]{});
    }

    private static class ToCode implements Transformer<Data, String> {
        @Override
        public String transform(Data input) {
            return input.getCode();
        }
    }

    private static class ToName implements Transformer<Data, String> {
        @Override
        public String transform(Data input) {
            return input.getName();
        }
    }
}
