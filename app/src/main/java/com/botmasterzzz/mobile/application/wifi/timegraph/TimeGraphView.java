package com.botmasterzzz.mobile.application.wifi.timegraph;

import android.content.res.Resources;
import android.view.View;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.Configuration;
import com.botmasterzzz.mobile.application.MainContext;
import com.botmasterzzz.mobile.application.settings.Settings;
import com.botmasterzzz.mobile.application.settings.ThemeStyle;
import com.botmasterzzz.mobile.application.wifi.band.WiFiBand;
import com.botmasterzzz.mobile.application.wifi.graphutils.GraphConstants;
import com.botmasterzzz.mobile.application.wifi.graphutils.GraphViewBuilder;
import com.botmasterzzz.mobile.application.wifi.graphutils.GraphViewNotifier;
import com.botmasterzzz.mobile.application.wifi.graphutils.GraphViewWrapper;
import com.botmasterzzz.mobile.application.wifi.model.WiFiData;
import com.botmasterzzz.mobile.application.wifi.model.WiFiDetail;
import com.botmasterzzz.mobile.application.wifi.predicate.FilterPredicate;
import com.jjoe64.graphview.GraphView;
import org.apache.commons.collections4.Predicate;

import java.util.List;
import java.util.Set;

class TimeGraphView implements GraphViewNotifier {
    private final WiFiBand wiFiBand;
    private DataManager dataManager;
    private GraphViewWrapper graphViewWrapper;

    TimeGraphView(@NonNull WiFiBand wiFiBand) {
        this.wiFiBand = wiFiBand;
        this.graphViewWrapper = makeGraphViewWrapper();
        this.dataManager = new DataManager();
    }

    @Override
    public void update(@NonNull WiFiData wiFiData) {
        Settings settings = MainContext.INSTANCE.getSettings();
        Predicate<WiFiDetail> predicate = FilterPredicate.makeOtherPredicate(settings);
        List<WiFiDetail> wiFiDetails = wiFiData.getWiFiDetails(predicate, settings.getSortBy());
        Set<WiFiDetail> newSeries = dataManager.addSeriesData(graphViewWrapper, wiFiDetails, settings.getGraphMaximumY());
        graphViewWrapper.removeSeries(newSeries);
        graphViewWrapper.updateLegend(settings.getTimeGraphLegend());
        graphViewWrapper.setVisibility(isSelected() ? View.VISIBLE : View.GONE);
    }

    private boolean isSelected() {
        return wiFiBand.equals(MainContext.INSTANCE.getSettings().getWiFiBand());
    }

    @Override
    @NonNull
    public GraphView getGraphView() {
        return graphViewWrapper.getGraphView();
    }

    private int getNumX() {
        return GraphConstants.NUM_X_TIME;
    }

    void setGraphViewWrapper(@NonNull GraphViewWrapper graphViewWrapper) {
        this.graphViewWrapper = graphViewWrapper;
    }

    void setDataManager(@NonNull DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @NonNull
    private GraphView makeGraphView(@NonNull MainContext mainContext, int graphMaximumY, @NonNull ThemeStyle themeStyle) {
        Resources resources = mainContext.getResources();
        return new GraphViewBuilder(mainContext.getContext(), getNumX(), graphMaximumY, themeStyle)
            .setLabelFormatter(new TimeAxisLabel())
            .setVerticalTitle("Мощность сигнала  (dBm)")
            .setHorizontalTitle("Кол-во сканирований")
            .setHorizontalLabelsVisible(false)
            .build();
    }

    @NonNull
    private GraphViewWrapper makeGraphViewWrapper() {
        MainContext mainContext = MainContext.INSTANCE;
        Settings settings = mainContext.getSettings();
        ThemeStyle themeStyle = settings.getThemeStyle();
        int graphMaximumY = settings.getGraphMaximumY();
        Configuration configuration = mainContext.getConfiguration();
        GraphView graphView = makeGraphView(mainContext, graphMaximumY, themeStyle);
        graphViewWrapper = new GraphViewWrapper(graphView, settings.getTimeGraphLegend(), themeStyle);
        configuration.setSize(graphViewWrapper.getSize(graphViewWrapper.calculateGraphType()));
        graphViewWrapper.setViewport();
        return graphViewWrapper;
    }

}
