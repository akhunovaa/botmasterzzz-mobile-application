package com.botmasterzzz.mobile.application.navigation.options;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;

import com.botmasterzzz.mobile.application.R;

public class OptionMenu {
    private Menu menu;

    public void create(@NonNull Activity activity, Menu menu) {
        activity.getMenuInflater().inflate(R.menu.optionmenu, menu);
        this.menu = menu;
        iconsVisible(menu);
    }

    public void select(@NonNull MenuItem item) {
        OptionAction.findOptionAction(item.getItemId()).getAction().execute();
    }

    public Menu getMenu() {
        return menu;
    }

    @SuppressLint("RestrictedApi")
    private void iconsVisible(Menu menu) {
        try {
            if (menu instanceof MenuBuilder) {
                ((MenuBuilder) menu).setOptionalIconsVisible(true);
            }
        } catch (Exception e) {
            // nothing
        }
    }

}
