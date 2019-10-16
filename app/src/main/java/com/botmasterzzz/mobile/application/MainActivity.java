package com.botmasterzzz.mobile.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.botmasterzzz.mobile.application.navigation.NavigationMenu;
import com.botmasterzzz.mobile.application.navigation.NavigationMenuControl;
import com.botmasterzzz.mobile.application.navigation.NavigationMenuController;
import com.botmasterzzz.mobile.application.navigation.options.OptionMenu;
import com.botmasterzzz.mobile.application.permission.PermissionService;
import com.botmasterzzz.mobile.application.settings.Repository;
import com.botmasterzzz.mobile.application.settings.Settings;
import com.botmasterzzz.mobile.application.settings.SettingsFactory;
import com.botmasterzzz.mobile.application.util.ConfigurationUtils;
import com.botmasterzzz.mobile.application.wifi.accesspoint.ConnectionView;
import com.botmasterzzz.mobile.application.wifi.band.WiFiBand;
import com.botmasterzzz.mobile.application.wifi.band.WiFiChannel;
import com.botmasterzzz.mobile.util.EnumUtils;
import com.google.android.material.navigation.NavigationView;


import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationMenuControl, OnSharedPreferenceChangeListener {

    private MainReload mainReload;
    private DrawerNavigation drawerNavigation;
    private NavigationMenuController navigationMenuController;
    private OptionMenu optionMenu;
    private String currentCountryCode;
    private PermissionService permissionService;

    @Override
    protected void attachBaseContext(Context newBase) {
        Repository repository = new Repository(newBase);
        Settings settings = SettingsFactory.make(repository);
        Locale newLocale = settings.getLanguageLocale();
        Context context = ConfigurationUtils.createContext(newBase, newLocale);
        super.attachBaseContext(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MainContext mainContext = MainContext.INSTANCE;
        mainContext.initialize(this, isLargeScreen());

        Settings settings = mainContext.getSettings();
        settings.initializeDefaultValues();

        setTheme(settings.getThemeStyle().getThemeNoActionBar());

        setWiFiChannelPairs(mainContext);

        mainReload = new MainReload(settings);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        settings.registerOnSharedPreferenceChangeListener(this);

        setOptionMenu(new OptionMenu());

        ActivityUtils.keepScreenOn();

        Toolbar toolbar = ActivityUtils.setupToolbar();
        drawerNavigation = new DrawerNavigation(this, toolbar);

        navigationMenuController = new NavigationMenuController(this);
        navigationMenuController.setCurrentNavigationMenu(settings.getSelectedMenu());
        onNavigationItemSelected(getCurrentMenuItem());

        ConnectionView connectionView = new ConnectionView(this);
        mainContext.getScannerService().register(connectionView);

        permissionService = new PermissionService(this);
        permissionService.check();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerNavigation.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerNavigation.onConfigurationChanged(newConfig);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (!permissionService.isGranted(requestCode, grantResults)) {
            finish();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void setWiFiChannelPairs(MainContext mainContext) {
        Settings settings = mainContext.getSettings();
        String countryCode = settings.getCountryCode();
        if (!countryCode.equals(currentCountryCode)) {
            Pair<WiFiChannel, WiFiChannel> pair = WiFiBand.GHZ5.getWiFiChannels().getWiFiChannelPairFirst(countryCode);
            mainContext.getConfiguration().setWiFiChannelPair(pair);
            currentCountryCode = countryCode;
        }
    }

    private boolean isLargeScreen() {
        Configuration configuration = getResources().getConfiguration();
        int screenLayoutSize = configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        return screenLayoutSize == Configuration.SCREENLAYOUT_SIZE_LARGE ||
            screenLayoutSize == Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        MainContext mainContext = MainContext.INSTANCE;
        if (mainReload.shouldReload(mainContext.getSettings())) {
            MainContext.INSTANCE.getScannerService().stop();
            recreate();
        } else {
            ActivityUtils.keepScreenOn();
            setWiFiChannelPairs(mainContext);
            update();
        }
    }

    public void update() {
        MainContext.INSTANCE.getScannerService().update();
        updateActionBar();
    }

    @Override
    public void onBackPressed() {
        if (!closeDrawer()) {
            NavigationMenu selectedMenu = MainContext.INSTANCE.getSettings().getSelectedMenu();
            if (selectedMenu.equals(getCurrentNavigationMenu())) {
                super.onBackPressed();
            } else {
                setCurrentNavigationMenu(selectedMenu);
                onNavigationItemSelected(getCurrentMenuItem());
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        closeDrawer();
        NavigationMenu currentNavigationMenu = EnumUtils.find(NavigationMenu.class, menuItem.getItemId(), NavigationMenu.ACCESS_POINTS);
        currentNavigationMenu.activateNavigationMenu(this, menuItem);
        return true;
    }

    private boolean closeDrawer() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }

    @Override
    protected void onPause() {
        MainContext.INSTANCE.getScannerService().pause();
        updateActionBar();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (permissionService.isPermissionGranted()) {
            if (!permissionService.isSystemEnabled()) {
                ActivityUtils.startLocationSettings();
            }
            MainContext.INSTANCE.getScannerService().resume();
        }
        updateActionBar();
    }

    @Override
    protected void onStop() {
        MainContext.INSTANCE.getScannerService().stop();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        optionMenu.create(this, menu);
        updateActionBar();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        optionMenu.select(item);
        updateActionBar();
        return true;
    }

    public void updateActionBar() {
        getCurrentNavigationMenu().activateOptions(this);
    }

    public OptionMenu getOptionMenu() {
        return optionMenu;
    }

    void setOptionMenu(@NonNull OptionMenu optionMenu) {
        this.optionMenu = optionMenu;
    }

    @NonNull
    @Override
    public MenuItem getCurrentMenuItem() {
        return navigationMenuController.getCurrentMenuItem();
    }

    @NonNull
    @Override
    public NavigationMenu getCurrentNavigationMenu() {
        return navigationMenuController.getCurrentNavigationMenu();
    }

    @Override
    public void setCurrentNavigationMenu(@NonNull NavigationMenu navigationMenu) {
        navigationMenuController.setCurrentNavigationMenu(navigationMenu);
        MainContext.INSTANCE.getSettings().saveSelectedMenu(navigationMenu);
    }

    @NonNull
    @Override
    public NavigationView getNavigationView() {
        return navigationMenuController.getNavigationView();
    }

    public void mainConnectionVisibility(int visibility) {
        findViewById(R.id.main_connection).setVisibility(visibility);
    }

    public NavigationMenuController getNavigationMenuController() {
        return navigationMenuController;
    }

    public PermissionService getPermissionService() {
        return permissionService;
    }

    void setNavigationMenuController(NavigationMenuController navigationMenuController) {
        this.navigationMenuController = navigationMenuController;
    }

    void setDrawerNavigation(DrawerNavigation drawerNavigation) {
        this.drawerNavigation = drawerNavigation;
    }

    void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
}
