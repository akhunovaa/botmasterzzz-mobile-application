package com.botmasterzzz.mobile.application.settings;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.application.navigation.NavigationGroup;
import com.botmasterzzz.mobile.application.navigation.NavigationMenu;
import com.botmasterzzz.mobile.application.util.LocaleUtils;
import com.botmasterzzz.mobile.application.wifi.accesspoint.AccessPointViewType;
import com.botmasterzzz.mobile.application.wifi.accesspoint.ConnectionViewType;
import com.botmasterzzz.mobile.application.wifi.band.WiFiBand;
import com.botmasterzzz.mobile.application.wifi.graphutils.GraphLegend;
import com.botmasterzzz.mobile.application.wifi.model.GroupBy;
import com.botmasterzzz.mobile.application.wifi.model.Security;
import com.botmasterzzz.mobile.application.wifi.model.SortBy;
import com.botmasterzzz.mobile.application.wifi.model.Strength;
import com.botmasterzzz.mobile.util.EnumUtils;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static android.content.SharedPreferences.OnSharedPreferenceChangeListener;

public class Settings {
    static final int SCAN_SPEED_DEFAULT = 5;
    static final int GRAPH_Y_MULTIPLIER = -10;
    static final int GRAPH_Y_DEFAULT = 2;

    private final Repository repository;

    Settings(@NonNull Repository repository) {
        this.repository = repository;
    }

    public void initializeDefaultValues() {
        repository.initializeDefaultValues();
    }

    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        repository.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    public int getScanSpeed() {
        int defaultValue = repository.getStringAsInteger(R.string.scan_speed_default, SCAN_SPEED_DEFAULT);
        return repository.getStringAsInteger(R.string.scan_speed_key, defaultValue);
    }

    public boolean isWiFiThrottleDisabled() {
        return false;
    }

    public int getGraphMaximumY() {
        int defaultValue = repository.getStringAsInteger(R.string.graph_maximum_y_default, GRAPH_Y_DEFAULT);
        int result = repository.getStringAsInteger(R.string.graph_maximum_y_key, defaultValue);
        return result * GRAPH_Y_MULTIPLIER;
    }

    public void toggleWiFiBand() {
        repository.save(R.string.wifi_band_key, getWiFiBand().toggle().ordinal());
    }

    @NonNull
    public String getCountryCode() {
        String countryCode = LocaleUtils.getDefaultCountryCode();
        return repository.getString(R.string.country_code_key, countryCode);
    }

    @NonNull
    public Locale getLanguageLocale() {
        String defaultLanguageTag = LocaleUtils.getDefaultLanguageTag();
        String languageTag = repository.getString(R.string.language_key, defaultLanguageTag);
        return LocaleUtils.findByLanguageTag(languageTag);
    }

    @NonNull
    public SortBy getSortBy() {
        return find(SortBy.class, R.string.sort_by_key, SortBy.STRENGTH);
    }

    @NonNull
    public GroupBy getGroupBy() {
        return find(GroupBy.class, R.string.group_by_key, GroupBy.NONE);
    }

    @NonNull
    public AccessPointViewType getAccessPointView() {
        return find(AccessPointViewType.class, R.string.ap_view_key, AccessPointViewType.COMPLETE);
    }

    @NonNull
    public ConnectionViewType getConnectionViewType() {
        return find(ConnectionViewType.class, R.string.connection_view_key, ConnectionViewType.COMPLETE);
    }

    @NonNull
    public GraphLegend getChannelGraphLegend() {
        return find(GraphLegend.class, R.string.channel_graph_legend_key, GraphLegend.HIDE);
    }

    @NonNull
    public GraphLegend getTimeGraphLegend() {
        return find(GraphLegend.class, R.string.time_graph_legend_key, GraphLegend.LEFT);
    }

    @NonNull
    public WiFiBand getWiFiBand() {
        return find(WiFiBand.class, R.string.wifi_band_key, WiFiBand.GHZ2);
    }

    public boolean isWiFiOffOnExit() {
        return repository.getBoolean(R.string.wifi_off_on_exit_key, repository.getResourceBoolean(R.bool.wifi_off_on_exit_default));
    }

    public boolean isKeepScreenOn() {
        return repository.getBoolean(R.string.keep_screen_on_key, repository.getResourceBoolean(R.bool.keep_screen_on_default));
    }

    @NonNull
    public ThemeStyle getThemeStyle() {
        return find(ThemeStyle.class, R.string.theme_key, ThemeStyle.DARK);
    }

    @NonNull
    public NavigationMenu getSelectedMenu() {
        return find(NavigationMenu.class, R.string.selected_menu_key, NavigationMenu.ACCESS_POINTS);
    }

    public void saveSelectedMenu(@NonNull NavigationMenu navigationMenu) {
        if (NavigationGroup.GROUP_FEATURE.getNavigationMenus().contains(navigationMenu)) {
            repository.save(R.string.selected_menu_key, navigationMenu.ordinal());
        }
    }

    @NonNull
    public Set<String> getSSIDs() {
        return repository.getStringSet(R.string.filter_ssid_key, new HashSet<>());
    }

    public void saveSSIDs(@NonNull Set<String> values) {
        repository.saveStringSet(R.string.filter_ssid_key, values);
    }

    @NonNull
    public Set<WiFiBand> getWiFiBands() {
        return findSet(WiFiBand.class, R.string.filter_wifi_band_key, WiFiBand.GHZ2);
    }

    public void saveWiFiBands(@NonNull Set<WiFiBand> values) {
        saveSet(R.string.filter_wifi_band_key, values);
    }

    @NonNull
    public Set<Strength> getStrengths() {
        return findSet(Strength.class, R.string.filter_strength_key, Strength.FOUR);
    }

    public void saveStrengths(@NonNull Set<Strength> values) {
        saveSet(R.string.filter_strength_key, values);
    }

    @NonNull
    public Set<Security> getSecurities() {
        return findSet(Security.class, R.string.filter_security_key, Security.NONE);
    }

    public void saveSecurities(@NonNull Set<Security> values) {
        saveSet(R.string.filter_security_key, values);
    }

    public void saveAccessToken(@NonNull String accessToken) {
        repository.save(R.string.access_token_key, accessToken);
    }

    public String retreiveAccessToken() {
        return repository.getString(R.string.access_token_key, "empty");
    }

    @NonNull
    Repository getRepository() {
        return repository;
    }

    @NonNull
    private <T extends Enum> T find(@NonNull Class<T> enumType, int key, @NonNull T defaultValue) {
        int value = repository.getStringAsInteger(key, defaultValue.ordinal());
        return EnumUtils.find(enumType, value, defaultValue);
    }

    @NonNull
    private <T extends Enum> Set<T> findSet(@NonNull Class<T> enumType, int key, @NonNull T defaultValue) {
        Set<String> defaultValues = EnumUtils.ordinals(enumType);
        Set<String> values = repository.getStringSet(key, defaultValues);
        return EnumUtils.find(enumType, values, defaultValue);
    }

    private <T extends Enum> void saveSet(int key, @NonNull Set<T> values) {
        repository.saveStringSet(key, EnumUtils.find(values));
    }

}
