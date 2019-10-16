package com.botmasterzzz.mobile.application.wifi.channelgraph;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import com.botmasterzzz.mobile.application.wifi.band.WiFiChannel;
import com.botmasterzzz.mobile.application.wifi.band.WiFiChannels;
import com.botmasterzzz.mobile.application.wifi.graphutils.GraphConstants;
import com.botmasterzzz.mobile.application.wifi.graphutils.GraphViewWrapper;
import com.botmasterzzz.mobile.application.wifi.model.WiFiDetail;
import com.botmasterzzz.mobile.application.wifi.model.WiFiSignal;
import com.botmasterzzz.mobile.util.TitleLineGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

class DataManager {
    @NonNull
    Set<WiFiDetail> getNewSeries(@NonNull List<WiFiDetail> wiFiDetails, @NonNull Pair<WiFiChannel, WiFiChannel> wiFiChannelPair) {
        return new TreeSet<>(CollectionUtils.select(wiFiDetails, new InRangePredicate(wiFiChannelPair)));
    }

    @NonNull
    DataPoint[] getDataPoints(@NonNull WiFiDetail wiFiDetail, int levelMax) {
        WiFiSignal wiFiSignal = wiFiDetail.getWiFiSignal();
        int frequencyStart = wiFiSignal.getFrequencyStart();
        int frequencyEnd = wiFiSignal.getFrequencyEnd();
        int level = Math.min(wiFiSignal.getLevel(), levelMax);
        return new DataPoint[]{
            new DataPoint(frequencyStart, GraphConstants.MIN_Y),
            new DataPoint(frequencyStart + WiFiChannels.FREQUENCY_SPREAD, level),
            new DataPoint(wiFiSignal.getCenterFrequency(), level),
            new DataPoint(frequencyEnd - WiFiChannels.FREQUENCY_SPREAD, level),
            new DataPoint(frequencyEnd, GraphConstants.MIN_Y)
        };
    }

    void addSeriesData(@NonNull GraphViewWrapper graphViewWrapper, @NonNull Set<WiFiDetail> wiFiDetails, int levelMax) {
        IterableUtils.forEach(wiFiDetails, new SeriesClosure(graphViewWrapper, levelMax));
    }

    private class SeriesClosure implements Closure<WiFiDetail> {
        private final GraphViewWrapper graphViewWrapper;
        private final int levelMax;

        private SeriesClosure(GraphViewWrapper graphViewWrapper, int levelMax) {
            this.graphViewWrapper = graphViewWrapper;
            this.levelMax = levelMax;
        }

        @Override
        public void execute(WiFiDetail wiFiDetail) {
            DataPoint[] dataPoints = getDataPoints(wiFiDetail, levelMax);
            if (graphViewWrapper.isNewSeries(wiFiDetail)) {
                graphViewWrapper.addSeries(wiFiDetail, new TitleLineGraphSeries<>(dataPoints), true);
            } else {
                graphViewWrapper.updateSeries(wiFiDetail, dataPoints, true);
            }
        }
    }

}
