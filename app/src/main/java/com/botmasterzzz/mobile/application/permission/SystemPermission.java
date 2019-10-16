package com.botmasterzzz.mobile.application.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Build;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.util.BuildUtils;

class SystemPermission {
    private final Activity activity;

    SystemPermission(@NonNull Activity activity) {
        this.activity = activity;
    }

    boolean isEnabled() {
        return !BuildUtils.isMinVersionM() || isProviderEnabledAndroidM();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean isProviderEnabledAndroidM() {
        try {
            LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            return isLocationEnabled(locationManager) || isNetworkProviderEnabled(locationManager) || isGPSProviderEnabled(locationManager);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isGPSProviderEnabled(@NonNull LocationManager locationManager) {
        try {
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isNetworkProviderEnabled(@NonNull LocationManager locationManager) {
        try {
            return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isLocationEnabled(@NonNull LocationManager locationManager) {
        return BuildUtils.isMinVersionP() && isLocationEnabledAndroidP(locationManager);
    }

    @TargetApi(Build.VERSION_CODES.P)
    private boolean isLocationEnabledAndroidP(@NonNull LocationManager locationManager) {
        try {
            return locationManager.isLocationEnabled();
        } catch (Exception e) {
            return false;
        }
    }

}
