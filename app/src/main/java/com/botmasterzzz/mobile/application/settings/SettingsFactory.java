package com.botmasterzzz.mobile.application.settings;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.util.BuildUtils;

public class SettingsFactory {
    private SettingsFactory() {
        throw new IllegalStateException("Factory class");
    }

    @NonNull
    public static Settings make(@NonNull Repository repository) {
        if (BuildUtils.isMinVersionQ()) {
            return new SettingsAndroidQ(repository);
        } else if (BuildUtils.isVersionP()) {
            return new SettingsAndroidP(repository);
        } else {
            return new Settings(repository);
        }
    }
}
