package com.botmasterzzz.mobile.application.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.application.util.BuildUtils;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle bundle, String rootKey) {
        addPreferencesFromResource(R.xml.settings);
        findPreference(getString(R.string.experimental_key)).setVisible(BuildUtils.isVersionP());
        findPreference(getString(R.string.wifi_off_on_exit_key)).setVisible(!BuildUtils.isMinVersionQ());
    }

}
