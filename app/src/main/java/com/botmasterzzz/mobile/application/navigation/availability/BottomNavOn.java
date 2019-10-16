package com.botmasterzzz.mobile.application.navigation.availability;

import android.view.View;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.MainActivity;
import com.botmasterzzz.mobile.application.R;

class BottomNavOn implements NavigationOption {

    @Override
    public void apply(@NonNull MainActivity mainActivity) {
        mainActivity.findViewById(R.id.nav_bottom).setVisibility(View.VISIBLE);
    }

}
