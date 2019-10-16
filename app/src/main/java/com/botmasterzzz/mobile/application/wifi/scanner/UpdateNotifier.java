package com.botmasterzzz.mobile.application.wifi.scanner;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.wifi.model.WiFiData;

public interface UpdateNotifier {
    void update(@NonNull WiFiData wiFiData);
}
