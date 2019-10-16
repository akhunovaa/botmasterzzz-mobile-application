package com.botmasterzzz.mobile.application.wifi.filter.adapter;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.application.settings.Settings;
import com.botmasterzzz.mobile.application.wifi.model.Security;

import java.util.Set;

public class SecurityAdapter extends EnumFilterAdapter<Security> {

    SecurityAdapter(@NonNull Set<Security> values) {
        super(Security.class, values);
    }

    @Override
    public int getColor(@NonNull Security object) {
        return contains(object) ? R.color.selected : R.color.regular;
    }

    @Override
    public void save(@NonNull Settings settings) {
        settings.saveSecurities(getValues());
    }

}
