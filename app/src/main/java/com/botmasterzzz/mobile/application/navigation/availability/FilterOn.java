package com.botmasterzzz.mobile.application.navigation.availability;

import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.botmasterzzz.mobile.application.MainActivity;
import com.botmasterzzz.mobile.application.MainContext;
import com.botmasterzzz.mobile.application.R;

class FilterOn implements NavigationOption {

    @Override
    public void apply(@NonNull MainActivity mainActivity) {
        Menu menu = mainActivity.getOptionMenu().getMenu();
        if (menu != null) {
            MenuItem menuItem = menu.findItem(R.id.action_filter);
            menuItem.setVisible(true);
            setIconColor(mainActivity, menuItem, MainContext.INSTANCE.getFilterAdapter().isActive());
        }
    }

    private void setIconColor(@NonNull MainActivity mainActivity, @NonNull MenuItem menuItem, boolean active) {
        int color = ContextCompat.getColor(mainActivity, active ? R.color.selected : R.color.regular);
        DrawableCompat.setTint(menuItem.getIcon(), color);
    }

}
