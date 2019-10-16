package com.botmasterzzz.mobile.application.navigation.availability;

import android.view.Menu;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.MainActivity;
import com.botmasterzzz.mobile.application.R;

class FilterOff implements NavigationOption {

    @Override
    public void apply(@NonNull MainActivity mainActivity) {
        Menu menu = mainActivity.getOptionMenu().getMenu();
        if (menu != null) {
            menu.findItem(R.id.action_filter).setVisible(false);
        }
    }
}
