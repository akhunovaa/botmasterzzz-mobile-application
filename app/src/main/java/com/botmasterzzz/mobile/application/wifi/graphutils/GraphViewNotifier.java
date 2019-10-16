package com.botmasterzzz.mobile.application.wifi.graphutils;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.wifi.model.WiFiData;
import com.jjoe64.graphview.GraphView;

public interface GraphViewNotifier {
    @NonNull
    GraphView getGraphView();

    void update(@NonNull WiFiData wiFiData);
}
