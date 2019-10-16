package com.botmasterzzz.mobile.application.wifi.accesspoint;

import android.net.wifi.WifiInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.MainActivity;
import com.botmasterzzz.mobile.application.MainContext;
import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.application.util.BuildUtils;
import com.botmasterzzz.mobile.application.wifi.model.WiFiConnection;
import com.botmasterzzz.mobile.application.wifi.model.WiFiData;
import com.botmasterzzz.mobile.application.wifi.model.WiFiDetail;
import com.botmasterzzz.mobile.application.wifi.scanner.UpdateNotifier;

import java.util.Locale;

public class ConnectionView implements UpdateNotifier {
    private final MainActivity mainActivity;
    private AccessPointDetail accessPointDetail;
    private AccessPointPopup accessPointPopup;

    public ConnectionView(@NonNull MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        setAccessPointDetail(new AccessPointDetail());
        setAccessPointPopup(new AccessPointPopup());
    }

    @Override
    public void update(@NonNull WiFiData wiFiData) {
        ConnectionViewType connectionViewType = MainContext.INSTANCE.getSettings().getConnectionViewType();
        displayConnection(wiFiData, connectionViewType);
        displayNoData(wiFiData);
    }

    void setAccessPointDetail(@NonNull AccessPointDetail accessPointDetail) {
        this.accessPointDetail = accessPointDetail;
    }

    void setAccessPointPopup(@NonNull AccessPointPopup accessPointPopup) {
        this.accessPointPopup = accessPointPopup;
    }

    private void displayNoData(@NonNull WiFiData wiFiData) {
        int visibility = noData(wiFiData) ? View.VISIBLE : View.GONE;
        mainActivity.findViewById(R.id.scanning).setVisibility(visibility);
        mainActivity.findViewById(R.id.no_data).setVisibility(visibility);
    }

    private int getNoLocationVisibility(int visibility) {
        return mainActivity.getPermissionService().isEnabled() ? View.GONE : visibility;
    }

    private boolean noData(@NonNull WiFiData wiFiData) {
        return mainActivity.getCurrentNavigationMenu().isRegistered() && wiFiData.getWiFiDetails().isEmpty();
    }

    private void displayConnection(@NonNull WiFiData wiFiData, @NonNull ConnectionViewType connectionViewType) {
        WiFiDetail connection = wiFiData.getConnection();
        View connectionView = mainActivity.findViewById(R.id.connection);
        WiFiConnection wiFiConnection = connection.getWiFiAdditional().getWiFiConnection();
        if (connectionViewType.isHide() || !wiFiConnection.isConnected()) {
            connectionView.setVisibility(View.GONE);
        } else {
            connectionView.setVisibility(View.VISIBLE);
            ViewGroup parent = connectionView.findViewById(R.id.connectionDetail);
            View view = accessPointDetail.makeView(parent.getChildAt(0), parent, connection, false, connectionViewType.getAccessPointViewType());
            if (parent.getChildCount() == 0) {
                parent.addView(view);
            }
            setViewConnection(connectionView, wiFiConnection);
            attachPopup(view, connection);
        }
    }

    private void setViewConnection(View connectionView, WiFiConnection wiFiConnection) {
        String ipAddress = wiFiConnection.getIpAddress();
        connectionView.<TextView>findViewById(R.id.ipAddress).setText(ipAddress);

        TextView textLinkSpeed = connectionView.findViewById(R.id.linkSpeed);
        int linkSpeed = wiFiConnection.getLinkSpeed();
        if (linkSpeed == WiFiConnection.LINK_SPEED_INVALID) {
            textLinkSpeed.setVisibility(View.GONE);
        } else {
            textLinkSpeed.setVisibility(View.VISIBLE);
            textLinkSpeed.setText(String.format(Locale.ENGLISH, "%d%s", linkSpeed, WifiInfo.LINK_SPEED_UNITS));
        }
    }

    private void attachPopup(@NonNull View view, @NonNull WiFiDetail wiFiDetail) {
        View popupView = view.findViewById(R.id.attachPopup);
        if (popupView != null) {
            accessPointPopup.attach(popupView, wiFiDetail);
            accessPointPopup.attach(view.findViewById(R.id.ssid), wiFiDetail);
        }
    }

}
