package com.botmasterzzz.mobile.application.navigation.availability;

import java.util.Arrays;
import java.util.List;

public class NavigationOptionFactory {
    public static final NavigationOption FILTER_OFF = new FilterOff();
    public static final NavigationOption FILTER_ON = new FilterOn();
    public static final NavigationOption SCANNER_SWITCH_OFF = new ScannerSwitchOff();
    public static final NavigationOption SCANNER_SWITCH_ON = new ScannerSwitchOn();
    public static final NavigationOption WIFI_SWITCH_OFF = new WiFiSwitchOff();
    public static final NavigationOption WIFI_SWITCH_ON = new WiFiSwitchOn();
    public static final NavigationOption BOTTOM_NAV_OFF = new BottomNavOff();
    public static final NavigationOption BOTTOM_NAV_ON = new BottomNavOn();

    public static final List<NavigationOption> AP = Arrays.asList(WIFI_SWITCH_OFF, SCANNER_SWITCH_ON, FILTER_ON, BOTTOM_NAV_ON);
    public static final List<NavigationOption> OFF = Arrays.asList(WIFI_SWITCH_OFF, SCANNER_SWITCH_OFF, FILTER_OFF, BOTTOM_NAV_OFF);

    private NavigationOptionFactory() {
        throw new IllegalStateException("Factory class");
    }
}
