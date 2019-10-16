package com.botmasterzzz.mobile.application.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.application.util.BuildUtils;

class PermissionDialog {
    private final Activity activity;

    PermissionDialog(@NonNull Activity activity) {
        this.activity = activity;
    }

    void show() {
        View view = activity.getLayoutInflater().inflate(R.layout.info_permission, null);
        new AlertDialog
            .Builder(activity)
            .setView(view)
            .setTitle(R.string.app_full_name)
            .setIcon(R.drawable.ic_app)
            .setPositiveButton(android.R.string.ok, new OkClick(activity))
            .setNegativeButton(android.R.string.cancel, new CancelClick(activity))
            .create()
            .show();
    }

    static class OkClick implements OnClickListener {
        private final Activity activity;

        OkClick(@NonNull Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            requestPermissionsAndroidM();
        }

        @TargetApi(Build.VERSION_CODES.M)
        private void requestPermissionsAndroidM() {
            if (BuildUtils.isMinVersionM()) {
                activity.requestPermissions(ApplicationPermission.PERMISSIONS, ApplicationPermission.REQUEST_CODE);
            }
        }

    }

    static class CancelClick implements OnClickListener {
        private final Activity activity;

        CancelClick(@NonNull Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            activity.finish();
        }
    }

}
