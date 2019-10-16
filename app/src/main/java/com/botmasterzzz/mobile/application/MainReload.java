package com.botmasterzzz.mobile.application;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.settings.Settings;
import com.botmasterzzz.mobile.application.settings.ThemeStyle;
import com.botmasterzzz.mobile.application.wifi.accesspoint.ConnectionViewType;

import java.util.Locale;

class MainReload {
    private ThemeStyle themeStyle;
    private ConnectionViewType connectionViewType;
    private Locale languageLocale;

    MainReload(@NonNull Settings settings) {
        setThemeStyle(settings.getThemeStyle());
        setConnectionViewType(settings.getConnectionViewType());
        setLanguageLocale(settings.getLanguageLocale());
    }

    boolean shouldReload(@NonNull Settings settings) {
        return isThemeChanged(settings)
            || isConnectionViewTypeChanged(settings)
            || isLanguageChanged(settings);
    }

    private boolean isConnectionViewTypeChanged(Settings settings) {
        ConnectionViewType currentConnectionViewType = settings.getConnectionViewType();
        boolean connectionViewTypeChanged = !getConnectionViewType().equals(currentConnectionViewType);
        if (connectionViewTypeChanged) {
            setConnectionViewType(currentConnectionViewType);
        }
        return connectionViewTypeChanged;
    }

    private boolean isThemeChanged(Settings settings) {
        ThemeStyle settingThemeStyle = settings.getThemeStyle();
        boolean themeChanged = !getThemeStyle().equals(settingThemeStyle);
        if (themeChanged) {
            setThemeStyle(settingThemeStyle);
        }
        return themeChanged;
    }

    private boolean isLanguageChanged(Settings settings) {
        Locale settingLanguageLocale = settings.getLanguageLocale();
        boolean languageLocaleChanged = !getLanguageLocale().equals(settingLanguageLocale);
        if (languageLocaleChanged) {
            setLanguageLocale(settingLanguageLocale);
        }
        return languageLocaleChanged;
    }

    @NonNull
    ThemeStyle getThemeStyle() {
        return themeStyle;
    }

    private void setThemeStyle(@NonNull ThemeStyle themeStyle) {
        this.themeStyle = themeStyle;
    }

    @NonNull
    ConnectionViewType getConnectionViewType() {
        return connectionViewType;
    }

    private void setConnectionViewType(@NonNull ConnectionViewType connectionViewType) {
        this.connectionViewType = connectionViewType;
    }

    @NonNull
    Locale getLanguageLocale() {
        return languageLocale;
    }

    private void setLanguageLocale(Locale languageLocale) {
        this.languageLocale = languageLocale;
    }

}
