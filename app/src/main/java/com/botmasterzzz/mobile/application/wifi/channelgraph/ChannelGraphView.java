package com.botmasterzzz.mobile.application.wifi.channelgraph;

import android.content.res.Resources;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import com.botmasterzzz.mobile.application.Configuration;
import com.botmasterzzz.mobile.application.MainContext;

import com.botmasterzzz.mobile.application.settings.Settings;
import com.botmasterzzz.mobile.application.settings.ThemeStyle;
import com.botmasterzzz.mobile.application.wifi.band.WiFiBand;
import com.botmasterzzz.mobile.application.wifi.band.WiFiChannel;
import com.botmasterzzz.mobile.application.wifi.band.WiFiChannels;
import com.botmasterzzz.mobile.application.wifi.graphutils.GraphColor;
import com.botmasterzzz.mobile.application.wifi.graphutils.GraphConstants;
import com.botmasterzzz.mobile.application.wifi.graphutils.GraphViewBuilder;
import com.botmasterzzz.mobile.application.wifi.graphutils.GraphViewNotifier;
import com.botmasterzzz.mobile.application.wifi.graphutils.GraphViewWrapper;
import com.botmasterzzz.mobile.application.wifi.model.WiFiData;
import com.botmasterzzz.mobile.application.wifi.model.WiFiDetail;
import com.botmasterzzz.mobile.application.wifi.predicate.FilterPredicate;
import com.botmasterzzz.mobile.util.TitleLineGraphSeries;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;

import org.apache.commons.collections4.Predicate;

import java.util.List;
import java.util.Set;

class ChannelGraphView implements GraphViewNotifier {
    private final WiFiBand wiFiBand;
    private final Pair<WiFiChannel, WiFiChannel> wiFiChannelPair;
    private GraphViewWrapper graphViewWrapper;
    private DataManager dataManager;

    ChannelGraphView(@NonNull WiFiBand wiFiBand, @NonNull Pair<WiFiChannel, WiFiChannel> wiFiChannelPair) {
        this.wiFiBand = wiFiBand;
        this.wiFiChannelPair = wiFiChannelPair;
        this.graphViewWrapper = makeGraphViewWrapper();
        this.dataManager = new DataManager();
    }

    @Override
    public void update(@NonNull WiFiData wiFiData) {
        Settings settings = MainContext.INSTANCE.getSettings();
        Predicate<WiFiDetail> predicate = FilterPredicate.makeOtherPredicate(settings);
        List<WiFiDetail> wiFiDetails = wiFiData.getWiFiDetails(predicate, settings.getSortBy());
        Set<WiFiDetail> newSeries = dataManager.getNewSeries(wiFiDetails, wiFiChannelPair);
        dataManager.addSeriesData(graphViewWrapper, newSeries, settings.getGraphMaximumY());
        graphViewWrapper.removeSeries(newSeries);
        graphViewWrapper.updateLegend(settings.getChannelGraphLegend());
        graphViewWrapper.setVisibility(isSelected() ? View.VISIBLE : View.GONE);
    }

    private boolean isSelected() {
        Settings settings = MainContext.INSTANCE.getSettings();
        WiFiBand currentWiFiBand = settings.getWiFiBand();
        Configuration configuration = MainContext.INSTANCE.getConfiguration();
        Pair<WiFiChannel, WiFiChannel> currentWiFiChannelPair = configuration.getWiFiChannelPair();
        return wiFiBand.equals(currentWiFiBand) && (WiFiBand.GHZ2.equals(wiFiBand) || wiFiChannelPair.equals(currentWiFiChannelPair));
    }

    @Override
    @NonNull
    public GraphView getGraphView() {
        return graphViewWrapper.getGraphView();
    }

    private int getNumX() {
        int channelFirst = wiFiChannelPair.first.getChannel() - WiFiChannels.CHANNEL_OFFSET;
        int channelLast = wiFiChannelPair.second.getChannel() + WiFiChannels.CHANNEL_OFFSET;
        return Math.min(GraphConstants.NUM_X_CHANNEL, channelLast - channelFirst + 1);
    }

    @NonNull
    private GraphView makeGraphView(@NonNull MainContext mainContext, int graphMaximumY, @NonNull ThemeStyle themeStyle) {
        Resources resources = mainContext.getResources();
        return new GraphViewBuilder(mainContext.getContext(), getNumX(), graphMaximumY, themeStyle)
            .setLabelFormatter(new ChannelAxisLabel(wiFiBand, wiFiChannelPair))
            .setVerticalTitle("Сила сигнала (dBm)")
            .setHorizontalTitle("WiFi каналы")
            .build();
    }

    @NonNull
    private GraphViewWrapper makeGraphViewWrapper() {
        MainContext mainContext = MainContext.INSTANCE;
        Settings settings = mainContext.getSettings();
        Configuration configuration = mainContext.getConfiguration();
        ThemeStyle themeStyle = settings.getThemeStyle();
        int graphMaximumY = settings.getGraphMaximumY();
        GraphView graphView = makeGraphView(mainContext, graphMaximumY, themeStyle);
        graphViewWrapper = new GraphViewWrapper(graphView, settings.getChannelGraphLegend(), themeStyle);
        configuration.setSize(graphViewWrapper.getSize(graphViewWrapper.calculateGraphType()));
        int minX = wiFiChannelPair.first.getFrequency() - WiFiChannels.FREQUENCY_OFFSET;
        int maxX = minX + (graphViewWrapper.getViewportCntX() * WiFiChannels.FREQUENCY_SPREAD);
        graphViewWrapper.setViewport(minX, maxX);
        graphViewWrapper.addSeries(makeDefaultSeries(wiFiChannelPair.second.getFrequency(), minX));
        return graphViewWrapper;
    }

    private TitleLineGraphSeries<DataPoint> makeDefaultSeries(int frequencyEnd, int minX) {
        DataPoint[] dataPoints = new DataPoint[]{
            new DataPoint(minX, GraphConstants.MIN_Y),
            new DataPoint(frequencyEnd + WiFiChannels.FREQUENCY_OFFSET, GraphConstants.MIN_Y)
        };

        TitleLineGraphSeries<DataPoint> series = new TitleLineGraphSeries<>(dataPoints);
        series.setColor((int) GraphColor.TRANSPARENT.getPrimary());
        series.setThickness(GraphConstants.THICKNESS_INVISIBLE);
        return series;
    }

    void setGraphViewWrapper(@NonNull GraphViewWrapper graphViewWrapper) {
        this.graphViewWrapper = graphViewWrapper;
    }

    void setDataManager(@NonNull DataManager dataManager) {
        this.dataManager = dataManager;
    }

}
