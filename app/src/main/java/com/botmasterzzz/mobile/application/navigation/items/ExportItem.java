package com.botmasterzzz.mobile.application.navigation.items;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.MainActivity;
import com.botmasterzzz.mobile.application.MainContext;
import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.application.export.Export;
import com.botmasterzzz.mobile.application.navigation.NavigationMenu;
import com.botmasterzzz.mobile.application.wifi.model.UserDevice;
import com.botmasterzzz.mobile.application.wifi.model.UserWiFiData;
import com.botmasterzzz.mobile.application.wifi.model.WiFiDetail;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

class ExportItem implements NavigationItem {
    private static final String TIME_STAMP_FORMAT = "yyyy/MM/dd-HH:mm:ss";
    private String exportData;
    private String timestamp;
    private UserDevice userDevice;

    @Override
    public void activate(@NonNull MainActivity mainActivity, @NonNull MenuItem menuItem, @NonNull NavigationMenu navigationMenu) {
        timestamp = new SimpleDateFormat(TIME_STAMP_FORMAT, Locale.US).format(new Date());
        String title = getTitle(mainActivity, timestamp);
        List<WiFiDetail> wiFiDetails = getWiFiDetails();
        if (!dataAvailable(wiFiDetails)) {
            Toast.makeText(mainActivity, R.string.no_data, Toast.LENGTH_LONG).show();
            exportData = StringUtils.EMPTY;
            return;
        }
        String deviceName = getDeviceName();
        String androidVersion = getAndroidVersion();
        Context context = mainActivity.getApplicationContext();
        WifiManager wifiMan = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiMan.getConnectionInfo();
        int ipAddress = wifiInf.getIpAddress();
        String ip = String.format("%d.%d.%d.%d", (ipAddress & 0xff),(ipAddress >> 8 & 0xff),(ipAddress >> 16 & 0xff),(ipAddress >> 24 & 0xff));
        String macAddress = wifiInf.getMacAddress();
        userDevice = new UserDevice();
        userDevice.setModelName(deviceName);
        userDevice.setOsVersion(androidVersion);
        userDevice.setIpAddress(ip);
        userDevice.setMacAddress(macAddress);
        for (WiFiDetail wiFiDetail : wiFiDetails) {
            UserWiFiData userWiFiData = new UserWiFiData();
            userWiFiData.setBssid(wiFiDetail.getBSSID());
            userWiFiData.setSsid(wiFiDetail.getSSID());
            userWiFiData.setChannel(wiFiDetail.getSSID());
            userWiFiData.setChannel(wiFiDetail.getWiFiSignal().getChannelDisplay());
            userWiFiData.setSecurity(wiFiDetail.getCapabilities());
            int level = wiFiDetail.getWiFiSignal().getLevel();
            userWiFiData.setRssi(level + "ddBm");
            userWiFiData.setCc("RU");
            userWiFiData.setDistance(wiFiDetail.getWiFiSignal().getDistance());
            userWiFiData.setIs80211mc(wiFiDetail.getWiFiSignal().is80211mc());
            userWiFiData.setPrimaryFrequency(wiFiDetail.getWiFiSignal().getPrimaryFrequency());
            userWiFiData.setCenterFrequency(wiFiDetail.getWiFiSignal().getFrequencyStart());
            userWiFiData.setEndFrequency(wiFiDetail.getWiFiSignal().getFrequencyEnd());
            userDevice.addUserWifiData(userWiFiData);
        }
        exportData = new Export(wiFiDetails, timestamp).getData();
        Intent intent = createIntent(title, exportData);
        Intent chooser = createChooserIntent(intent, title);
        if (!exportAvailable(mainActivity, chooser)) {
            Toast.makeText(mainActivity, R.string.export_not_available, Toast.LENGTH_LONG).show();
            return;
        }
        try {
            mainActivity.startActivity(chooser);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(mainActivity, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean isRegistered() {
        return false;
    }

    @Override
    public int getVisibility() {
        return View.GONE;
    }

    private boolean exportAvailable(@NonNull MainActivity mainActivity, @NonNull Intent chooser) {
        return chooser.resolveActivity(mainActivity.getPackageManager()) != null;
    }

    private boolean dataAvailable(@NonNull List<WiFiDetail> wiFiDetails) {
        return !wiFiDetails.isEmpty();
    }

    @NonNull
    String getExportData() {
        return exportData;
    }

    @NonNull
    String getTimestamp() {
        return timestamp;
    }

    @NonNull
    private List<WiFiDetail> getWiFiDetails() {
        return MainContext.INSTANCE.getScannerService().getWiFiData().getWiFiDetails();
    }

    @NonNull
    private String getTitle(@NonNull MainActivity mainActivity, @NonNull String timestamp) {
        Resources resources = mainActivity.getResources();
        String title = resources.getString(R.string.action_access_points);
        return title + "-" + timestamp;
    }

    private Intent createIntent(String title, String data) {
        Intent intent = createSendIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, title);
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, data);
        return intent;
    }

    Intent createSendIntent() {
        return new Intent(Intent.ACTION_SEND);
    }

    Intent createChooserIntent(@NonNull Intent intent, @NonNull String title) {
        return Intent.createChooser(intent, title);
    }

    private String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    private String getAndroidVersion() {
        String release = Build.VERSION.RELEASE;
        int sdkVersion = Build.VERSION.SDK_INT;
        return "Android SDK: " + sdkVersion + " (" + release +")";
    }

}
