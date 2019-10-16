package com.botmasterzzz.mobile.application.navigation.items;

import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.MainActivity;
import com.botmasterzzz.mobile.application.navigation.NavigationMenu;

public interface NavigationItem {
    void activate(@NonNull MainActivity mainActivity, @NonNull MenuItem menuItem, @NonNull NavigationMenu navigationMenu);

    boolean isRegistered();

    int getVisibility();
}
