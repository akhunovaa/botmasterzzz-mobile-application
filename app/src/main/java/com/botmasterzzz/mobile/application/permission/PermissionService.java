package com.botmasterzzz.mobile.application.permission;

import android.app.Activity;

import androidx.annotation.NonNull;

public class PermissionService {
    private SystemPermission systemPermission;
    private ApplicationPermission applicationPermission;

    public PermissionService(@NonNull Activity activity) {
        this.applicationPermission = new ApplicationPermission(activity);
        this.systemPermission = new SystemPermission(activity);
    }

    public boolean isEnabled() {
        return isSystemEnabled() && isPermissionGranted();
    }

    public boolean isSystemEnabled() {
        return systemPermission.isEnabled();
    }

    public void check() {
        applicationPermission.check();
    }

    public boolean isGranted(int requestCode, int[] grantResults) {
        return applicationPermission.isGranted(requestCode, grantResults);
    }

    public boolean isPermissionGranted() {
        return applicationPermission.isGranted();
    }

    void setSystemPermission(SystemPermission systemPermission) {
        this.systemPermission = systemPermission;
    }

    void setApplicationPermission(ApplicationPermission applicationPermission) {
        this.applicationPermission = applicationPermission;
    }

}
