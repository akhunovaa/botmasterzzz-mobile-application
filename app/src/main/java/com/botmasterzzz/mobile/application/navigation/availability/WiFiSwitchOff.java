package com.botmasterzzz.mobile.application.navigation.availability;

import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import com.botmasterzzz.mobile.application.MainActivity;
import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.application.navigation.options.OptionMenu;

import org.apache.commons.lang3.StringUtils;

class WiFiSwitchOff implements NavigationOption {

    @Override
    public void apply(@NonNull MainActivity mainActivity) {
        applyToActionBar(mainActivity);
        applyToMenu(mainActivity);
    }

    private void applyToActionBar(@NonNull MainActivity mainActivity) {
        ActionBar actionBar = mainActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setSubtitle(StringUtils.EMPTY);
        }
    }

    private void applyToMenu(@NonNull MainActivity mainActivity) {
        OptionMenu optionMenu = mainActivity.getOptionMenu();
        if (optionMenu != null) {
            Menu menu = optionMenu.getMenu();
            if (menu != null) {
                menu.findItem(R.id.action_wifi_band).setVisible(false);
            }
        }
    }
}
