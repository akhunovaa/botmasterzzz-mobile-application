package com.botmasterzzz.mobile.application.wifi.model;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.util.EnumUtils;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

public enum Security {
    NONE(R.drawable.ic_lock_open),
    WPS(R.drawable.ic_lock_outline),
    WEP(R.drawable.ic_lock_outline),
    WPA(R.drawable.ic_lock),
    WPA2(R.drawable.ic_lock);

    private final int imageResource;

    Security(int imageResource) {
        this.imageResource = imageResource;
    }

    @NonNull
    public static List<Security> findAll(String capabilities) {
        Set<Security> results = new TreeSet<>();
        if (capabilities != null) {
            String[] values = capabilities.toUpperCase(Locale.getDefault())
                .replace("][", "-").replace("]", "").replace("[", "").split("-");
            for (String value : values) {
                try {
                    results.add(Security.valueOf(value));
                } catch (Exception e) {
                    //skip
                }
            }
        }
        return new ArrayList<>(results);
    }

    @NonNull
    public static Security findOne(String capabilities) {
        Security result = IterableUtils.find(EnumUtils.values(Security.class), new SecurityPredicate(findAll(capabilities)));
        return result == null ? Security.NONE : result;
    }

    public int getImageResource() {
        return imageResource;
    }

    private static class SecurityPredicate implements Predicate<Security> {
        private final List<Security> securities;

        private SecurityPredicate(@NonNull List<Security> securities) {
            this.securities = securities;
        }

        @Override
        public boolean evaluate(Security security) {
            return securities.contains(security);
        }
    }

}
