package com.botmasterzzz.mobile.application.wifi.graphutils;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.wifi.model.WiFiDetail;
import com.jjoe64.graphview.series.BaseSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.Series;

import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

class SeriesCache {
    private final Map<WiFiDetail, BaseSeries<DataPoint>> cache;

    SeriesCache() {
        this.cache = new TreeMap<>();
    }

    @NonNull
    List<WiFiDetail> difference(@NonNull Set<WiFiDetail> series) {
        Set<WiFiDetail> difference = new TreeSet<>(cache.keySet());
        difference.removeAll(series);
        return new ArrayList<>(difference);
    }

    @NonNull
    List<BaseSeries<DataPoint>> remove(@NonNull List<WiFiDetail> series) {
        List<BaseSeries<DataPoint>> removeSeries = new ArrayList<>();
        IterableUtils.forEach(CollectionUtils.select(series, new RemovePredicate()), new RemoveClosure(removeSeries));
        return removeSeries;
    }

    WiFiDetail find(@NonNull Series series) {
        return IterableUtils.find(cache.keySet(), new FindPredicate(series));
    }

    boolean contains(@NonNull WiFiDetail wiFiDetail) {
        return cache.containsKey(wiFiDetail);
    }

    @NonNull
    BaseSeries<DataPoint> get(@NonNull WiFiDetail wiFiDetail) {
        return cache.get(wiFiDetail);
    }

    @NonNull
    BaseSeries<DataPoint> put(@NonNull WiFiDetail wiFiDetail, @NonNull BaseSeries<DataPoint> series) {
        return cache.put(wiFiDetail, series);
    }

    private class RemoveClosure implements Closure<WiFiDetail> {
        private final List<BaseSeries<DataPoint>> removeSeries;

        private RemoveClosure(List<BaseSeries<DataPoint>> removeSeries) {
            this.removeSeries = removeSeries;
        }

        @Override
        public void execute(WiFiDetail wiFiDetail) {
            removeSeries.add(cache.get(wiFiDetail));
            cache.remove(wiFiDetail);
        }
    }

    private class RemovePredicate implements Predicate<WiFiDetail> {
        @Override
        public boolean evaluate(WiFiDetail wiFiDetail) {
            return cache.containsKey(wiFiDetail);
        }
    }

    private class FindPredicate implements Predicate<WiFiDetail> {
        private final Series series;

        private FindPredicate(@NonNull Series series) {
            this.series = series;
        }

        @Override
        public boolean evaluate(WiFiDetail wiFiDetail) {
            return series.equals(cache.get(wiFiDetail));
        }
    }
}
