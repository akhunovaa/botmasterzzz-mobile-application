package com.botmasterzzz.mobile.application.wifi.filter;

import android.app.Dialog;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.application.wifi.filter.adapter.StrengthAdapter;
import com.botmasterzzz.mobile.application.wifi.model.Strength;

import java.util.HashMap;
import java.util.Map;

class StrengthFilter extends EnumFilter<Strength, StrengthAdapter> {
    static final Map<Strength, Integer> ids = new HashMap<>();

    static {
        ids.put(Strength.ZERO, R.id.filterStrength0);
        ids.put(Strength.ONE, R.id.filterStrength1);
        ids.put(Strength.TWO, R.id.filterStrength2);
        ids.put(Strength.THREE, R.id.filterStrength3);
        ids.put(Strength.FOUR, R.id.filterStrength4);
    }

    StrengthFilter(@NonNull StrengthAdapter strengthAdapter, @NonNull Dialog dialog) {
        super(ids, strengthAdapter, dialog, R.id.filterStrength);
    }
}
