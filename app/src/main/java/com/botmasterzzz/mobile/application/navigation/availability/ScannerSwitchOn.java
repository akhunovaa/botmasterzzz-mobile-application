package com.botmasterzzz.mobile.application.navigation.availability;

import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.MainActivity;
import com.botmasterzzz.mobile.application.MainContext;
import com.botmasterzzz.mobile.application.R;

class ScannerSwitchOn implements NavigationOption {

    @Override
    public void apply(@NonNull MainActivity mainActivity) {
        Menu menu = mainActivity.getOptionMenu().getMenu();
        if (menu != null) {
            MenuItem menuItem = menu.findItem(R.id.action_scanner);
            menuItem.setVisible(true);
            if (MainContext.INSTANCE.getScannerService().isRunning()) {
                menuItem.setTitle(R.string.scanner_pause);
                menuItem.setIcon(R.drawable.ic_pause);
            } else {
                menuItem.setTitle(R.string.scanner_play);
                menuItem.setIcon(R.drawable.ic_play_arrow);
            }
        }
    }

}
