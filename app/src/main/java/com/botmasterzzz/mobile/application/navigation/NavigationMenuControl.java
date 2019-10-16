package com.botmasterzzz.mobile.application.navigation;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public interface NavigationMenuControl extends
    NavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    @NonNull
    MenuItem getCurrentMenuItem();

    @NonNull
    NavigationMenu getCurrentNavigationMenu();

    void setCurrentNavigationMenu(@NonNull NavigationMenu navigationMenu);

    @NonNull
    NavigationView getNavigationView();

    <T extends View> T findViewById(@IdRes int id);
}
