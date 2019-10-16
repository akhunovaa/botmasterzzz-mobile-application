package com.botmasterzzz.mobile.application.settings;

import androidx.annotation.NonNull;

public class SettingsAndroidQ extends Settings {

    SettingsAndroidQ(@NonNull Repository repository) {
        super(repository);
    }

    @Override
    public boolean isWiFiOffOnExit() {
        return false;
    }

}
