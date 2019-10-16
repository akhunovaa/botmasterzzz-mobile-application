package com.botmasterzzz.mobile.application.navigation.items;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.botmasterzzz.mobile.application.MainActivity;
import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.application.navigation.NavigationMenu;

class FragmentItem implements NavigationItem {
    private final Fragment fragment;
    private final boolean registered;
    private final int visibility;

    FragmentItem(@NonNull Fragment fragment) {
        this(fragment, true, View.VISIBLE);
    }

    FragmentItem(@NonNull Fragment fragment, boolean registered) {
        this(fragment, registered, View.VISIBLE);
    }

    FragmentItem(@NonNull Fragment fragment, boolean registered, int visibility) {
        this.fragment = fragment;
        this.registered = registered;
        this.visibility = visibility;
    }

    @Override
    public void activate(@NonNull MainActivity mainActivity, @NonNull MenuItem menuItem, @NonNull NavigationMenu navigationMenu) {
        FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
        if (fragmentManager.isStateSaved()) {
            return;
        }
        updateMainActivity(mainActivity, menuItem, navigationMenu);
        startFragment(fragmentManager);
    }

    @Override
    public boolean isRegistered() {
        return registered;
    }

    @Override
    public int getVisibility() {
        return visibility;
    }

    @NonNull
    Fragment getFragment() {
        return fragment;
    }

    private void startFragment(FragmentManager fragmentManager) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment, fragment).commit();
    }

    private void updateMainActivity(@NonNull MainActivity mainActivity, @NonNull MenuItem menuItem, @NonNull NavigationMenu navigationMenu) {
        mainActivity.setCurrentNavigationMenu(navigationMenu);
        mainActivity.setTitle(menuItem.getTitle());
        mainActivity.updateActionBar();
        mainActivity.mainConnectionVisibility(visibility);
    }

}
