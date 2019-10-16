package com.botmasterzzz.mobile.application.wifi.filter;

import android.app.Dialog;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.application.wifi.filter.adapter.SecurityAdapter;
import com.botmasterzzz.mobile.application.wifi.model.Security;

import java.util.HashMap;
import java.util.Map;

class SecurityFilter extends EnumFilter<Security, SecurityAdapter> {
    static final Map<Security, Integer> ids = new HashMap<>();

    static {
        ids.put(Security.NONE, R.id.filterSecurityNone);
        ids.put(Security.WPS, R.id.filterSecurityWPS);
        ids.put(Security.WEP, R.id.filterSecurityWEP);
        ids.put(Security.WPA, R.id.filterSecurityWPA);
        ids.put(Security.WPA2, R.id.filterSecurityWPA2);
    }

    SecurityFilter(@NonNull SecurityAdapter securityAdapter, @NonNull Dialog dialog) {
        super(ids, securityAdapter, dialog, R.id.filterSecurity);
    }
}
