package com.botmasterzzz.mobile.application.wifi.filter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.MainContext;
import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.application.navigation.NavigationMenu;

public class Filter {

    private final AlertDialog alertDialog;

    private Filter(AlertDialog alertDialog) {
        this.alertDialog = alertDialog;
    }

    public static Filter build() {
        return new Filter(buildAlertDialog());
    }

    private static AlertDialog buildAlertDialog() {
        MainContext mainContext = MainContext.INSTANCE;
        if (mainContext.getMainActivity().isFinishing()) {
            return null;
        }
        View view = mainContext.getLayoutInflater().inflate(R.layout.filter_popup, null);
        return new AlertDialog
            .Builder(view.getContext())
            .setView(view)
            .setTitle(R.string.filter_title)
            .setIcon(R.drawable.ic_filter_list)
            .setNegativeButton(R.string.filter_reset, new Reset())
            .setNeutralButton(R.string.filter_close, new Close())
            .setPositiveButton(R.string.filter_apply, new Apply())
            .create();
    }

    public void show() {
        if (alertDialog != null && !alertDialog.isShowing()) {
            alertDialog.show();
            addWiFiBandFilter(alertDialog);
            addSSIDFilter(alertDialog);
            addStrengthFilter(alertDialog);
            addSecurityFilter(alertDialog);
        }
    }

    AlertDialog getAlertDialog() {
        return alertDialog;
    }

    private void addSSIDFilter(@NonNull AlertDialog alertDialog) {
        new SSIDFilter(MainContext.INSTANCE.getFilterAdapter().getSSIDAdapter(), alertDialog);
    }

    private void addWiFiBandFilter(@NonNull AlertDialog alertDialog) {
        if (NavigationMenu.ACCESS_POINTS.equals(MainContext.INSTANCE.getMainActivity().getCurrentNavigationMenu())) {
            new WiFiBandFilter(MainContext.INSTANCE.getFilterAdapter().getWiFiBandAdapter(), alertDialog);
        }
    }

    private void addStrengthFilter(@NonNull AlertDialog alertDialog) {
        new StrengthFilter(MainContext.INSTANCE.getFilterAdapter().getStrengthAdapter(), alertDialog);
    }

    private void addSecurityFilter(@NonNull AlertDialog alertDialog) {
        new SecurityFilter(MainContext.INSTANCE.getFilterAdapter().getSecurityAdapter(), alertDialog);
    }

    private static class Close implements OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            MainContext.INSTANCE.getFilterAdapter().reload();
        }
    }

    private static class Apply implements OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            MainContext mainContext = MainContext.INSTANCE;
            mainContext.getFilterAdapter().save();
            mainContext.getMainActivity().update();
        }
    }

    private static class Reset implements OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            MainContext mainContext = MainContext.INSTANCE;
            mainContext.getFilterAdapter().reset();
            mainContext.getMainActivity().update();
        }
    }
}
