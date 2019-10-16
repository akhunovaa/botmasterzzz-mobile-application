package com.botmasterzzz.mobile.application.settings;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.R;

public class SettingsAndroidP extends Settings {

    SettingsAndroidP(@NonNull Repository repository) {
        super(repository);
    }

    @Override
    public int getScanSpeed() {
        int scanSpeed = super.getScanSpeed();
        return isWiFiThrottleDisabled() ? Math.max(scanSpeed, SCAN_SPEED_DEFAULT) : scanSpeed;
    }

    @Override
    public boolean isWiFiThrottleDisabled() {
        Repository repository = getRepository();
        boolean defaultValue = repository.getResourceBoolean(R.bool.wifi_throttle_disabled_default);
        return repository.getBoolean(R.string.wifi_throttle_disabled_key, defaultValue);
    }

}
