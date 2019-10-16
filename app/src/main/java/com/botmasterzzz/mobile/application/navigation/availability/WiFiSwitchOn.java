package com.botmasterzzz.mobile.application.navigation.availability;

import android.content.res.Resources;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;

import com.botmasterzzz.mobile.application.MainActivity;
import com.botmasterzzz.mobile.application.MainContext;
import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.application.navigation.options.OptionMenu;
import com.botmasterzzz.mobile.application.wifi.band.WiFiBand;
import com.botmasterzzz.mobile.util.TextUtils;

class WiFiSwitchOn implements NavigationOption {

    static final String SPACER = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";

    @Override
    public void apply(@NonNull MainActivity mainActivity) {
        applyToActionBar(mainActivity);
        applyToMenu(mainActivity);
    }

    private void applyToActionBar(@NonNull MainActivity mainActivity) {
        ActionBar actionBar = mainActivity.getSupportActionBar();
        if (actionBar != null) {
            int colorSelected = ContextCompat.getColor(mainActivity, R.color.selected);
            int colorNotSelected = ContextCompat.getColor(mainActivity, R.color.regular);
            Resources resources = mainActivity.getResources();
            String wiFiBand2 = resources.getString(WiFiBand.GHZ2.getTextResource());
            String wiFiBand5 = resources.getString(WiFiBand.GHZ5.getTextResource());
            WiFiBand wiFiBand = MainContext.INSTANCE.getSettings().getWiFiBand();
            String subtitle = makeSubtitle(WiFiBand.GHZ2.equals(wiFiBand), wiFiBand2, wiFiBand5, colorSelected, colorNotSelected);
            actionBar.setSubtitle(TextUtils.fromHtml(subtitle));
        }
    }

    private void applyToMenu(@NonNull MainActivity mainActivity) {
        OptionMenu optionMenu = mainActivity.getOptionMenu();
        if (optionMenu != null) {
            Menu menu = optionMenu.getMenu();
            if (menu != null) {
                menu.findItem(R.id.action_wifi_band).setVisible(true);
            }
        }
    }

    @NonNull
    String makeSubtitle(boolean wiFiBand2Selected, String wiFiBand2, String wiFiBand5, int colorSelected, int colorNotSelected) {
        StringBuilder stringBuilder = new StringBuilder();
        if (wiFiBand2Selected) {
            stringBuilder.append(TextUtils.textToHtml(wiFiBand2, colorSelected, false));
        } else {
            stringBuilder.append(TextUtils.textToHtml(wiFiBand2, colorNotSelected, true));
        }
        stringBuilder.append(SPACER);
        if (wiFiBand2Selected) {
            stringBuilder.append(TextUtils.textToHtml(wiFiBand5, colorNotSelected, true));
        } else {
            stringBuilder.append(TextUtils.textToHtml(wiFiBand5, colorSelected, false));
        }
        return stringBuilder.toString();
    }

}
