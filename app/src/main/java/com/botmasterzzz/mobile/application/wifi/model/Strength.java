package com.botmasterzzz.mobile.application.wifi.model;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.R;

public enum Strength {
    ZERO(R.drawable.ic_signal_wifi_0_bar, R.color.error),
    ONE(R.drawable.ic_signal_wifi_1_bar, R.color.warning),
    TWO(R.drawable.ic_signal_wifi_2_bar, R.color.warning),
    THREE(R.drawable.ic_signal_wifi_3_bar, R.color.success),
    FOUR(R.drawable.ic_signal_wifi_4_bar, R.color.success);

    private final int imageResource;
    private final int colorResource;

    Strength(int imageResource, int colorResource) {
        this.imageResource = imageResource;
        this.colorResource = colorResource;
    }

    @NonNull
    public static Strength calculate(int level) {
        int index = WiFiUtils.calculateSignalLevel(level, values().length);
        return Strength.values()[index];
    }

    @NonNull
    public static Strength reverse(Strength strength) {
        int index = Strength.values().length - strength.ordinal() - 1;
        return Strength.values()[index];
    }

    public int colorResource() {
        return colorResource;
    }

    public int colorResourceDefault() {
        return R.color.regular;
    }

    public int imageResource() {
        return imageResource;
    }

    public boolean weak() {
        return ZERO.equals(this);
    }

}
