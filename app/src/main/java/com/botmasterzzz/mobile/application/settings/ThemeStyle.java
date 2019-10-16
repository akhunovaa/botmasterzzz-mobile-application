package com.botmasterzzz.mobile.application.settings;

import androidx.annotation.StyleRes;

import com.botmasterzzz.mobile.application.MainContext;
import com.botmasterzzz.mobile.application.R;

public enum ThemeStyle {
    DARK(R.style.ThemeDark, R.style.ThemeDarkNoActionBar),
    LIGHT(R.style.ThemeLight, R.style.ThemeLightNoActionBar),
    SYSTEM(R.style.ThemeSystem, R.style.ThemeSystemNoActionBar);

    private final int theme;
    private final int themeNoActionBar;

    ThemeStyle(@StyleRes int theme, @StyleRes int themeNoActionBar) {
        this.theme = theme;
        this.themeNoActionBar = themeNoActionBar;
    }

    @StyleRes
    public static int getDefaultTheme() {
        Settings settings = MainContext.INSTANCE.getSettings();
        ThemeStyle themeStyle = (settings == null ? ThemeStyle.DARK : settings.getThemeStyle());
        return themeStyle.getTheme();
    }

    public @StyleRes
    int getTheme() {
        return theme;
    }

    public @StyleRes
    int getThemeNoActionBar() {
        return themeNoActionBar;
    }
}
