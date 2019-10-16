package com.botmasterzzz.mobile.application;

import android.content.res.Configuration;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

class DrawerNavigation {
    private final ActionBarDrawerToggle drawerToggle;

    DrawerNavigation(@NonNull MainActivity mainActivity, @NonNull Toolbar toolbar) {
        DrawerLayout drawer = mainActivity.findViewById(R.id.drawer_layout);
        drawerToggle = create(
            mainActivity,
            toolbar,
            drawer,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
        drawer.addDrawerListener(drawerToggle);
        syncState();
    }

    void syncState() {
        drawerToggle.syncState();
    }

    void onConfigurationChanged(Configuration newConfig) {
        drawerToggle.onConfigurationChanged(newConfig);
    }

    ActionBarDrawerToggle create(
        @NonNull MainActivity mainActivity,
        @NonNull Toolbar toolbar,
        @NonNull DrawerLayout drawer,
        @StringRes int openDrawerContentDescRes,
        @StringRes int closeDrawerContentDescRes) {
        return new ActionBarDrawerToggle(
            mainActivity,
            drawer,
            toolbar,
            openDrawerContentDescRes,
            closeDrawerContentDescRes);
    }

}
