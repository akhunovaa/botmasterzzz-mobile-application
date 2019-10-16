package com.botmasterzzz.mobile.application.permission;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.util.BuildUtils;

class ApplicationPermission {
    static final String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION};
    static final int REQUEST_CODE = 0x123450;

    private final Activity activity;
    private final PermissionDialog permissionDialog;

    ApplicationPermission(@NonNull Activity activity) {
        this(activity, new PermissionDialog(activity));
    }

    ApplicationPermission(@NonNull Activity activity, @NonNull PermissionDialog permissionDialog) {
        this.activity = activity;
        this.permissionDialog = permissionDialog;
    }

    void check() {
        if (isGranted()) {
            return;
        }
        if (activity.isFinishing()) {
            return;
        }
        permissionDialog.show();
    }

    boolean isGranted(int requestCode, @NonNull int[] grantResults) {
        return requestCode == REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }

    boolean isGranted() {
        return !BuildUtils.isMinVersionM() || isGrantedAndroidM();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean isGrantedAndroidM() {
        return activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

}
