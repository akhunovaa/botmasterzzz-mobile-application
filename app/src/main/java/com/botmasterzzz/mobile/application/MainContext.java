package com.botmasterzzz.mobile.application;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.settings.Repository;
import com.botmasterzzz.mobile.application.settings.Settings;
import com.botmasterzzz.mobile.application.settings.SettingsFactory;
import com.botmasterzzz.mobile.application.vendor.VendorService;
import com.botmasterzzz.mobile.application.vendor.model.VendorServiceFactory;
import com.botmasterzzz.mobile.application.wifi.filter.adapter.FilterAdapter;
import com.botmasterzzz.mobile.application.wifi.scanner.ScannerService;
import com.botmasterzzz.mobile.application.wifi.scanner.ScannerServiceFactory;

public enum MainContext {
    INSTANCE;
    private Settings settings;
    private MainActivity mainActivity;
    private ScannerService scannerService;
    private VendorService vendorService;
    private Configuration configuration;
    private FilterAdapter filterAdapter;

    public Settings getSettings() {
        return settings;
    }

    void setSettings(Settings settings) {
        this.settings = settings;
    }

    public VendorService getVendorService() {
        return vendorService;
    }

    void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    public ScannerService getScannerService() {
        return scannerService;
    }

    void setScannerService(ScannerService scannerService) {
        this.scannerService = scannerService;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public Context getContext() {
        return mainActivity.getApplicationContext();
    }

    public Resources getResources() {
        return getContext().getResources();
    }

    public LayoutInflater getLayoutInflater() {
        return mainActivity.getLayoutInflater();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public FilterAdapter getFilterAdapter() {
        return filterAdapter;
    }

    void setFilterAdapter(FilterAdapter filterAdapter) {
        this.filterAdapter = filterAdapter;
    }

    void initialize(@NonNull MainActivity mainActivity, boolean largeScreen) {
        Context applicationContext = mainActivity.getApplicationContext();
        Handler handler = new Handler();
        Repository repository = new Repository(applicationContext);
        Settings currentSettings = SettingsFactory.make(repository);
        Configuration currentConfiguration = new Configuration(largeScreen);

        setMainActivity(mainActivity);
        setConfiguration(currentConfiguration);
        setSettings(currentSettings);
        setVendorService(VendorServiceFactory.makeVendorService(mainActivity.getResources()));
        setScannerService(ScannerServiceFactory.makeScannerService(mainActivity, handler, currentSettings));
        setFilterAdapter(new FilterAdapter(currentSettings));
    }

}
