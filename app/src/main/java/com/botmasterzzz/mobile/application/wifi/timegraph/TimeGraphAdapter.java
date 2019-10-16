package com.botmasterzzz.mobile.application.wifi.timegraph;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.wifi.band.WiFiBand;
import com.botmasterzzz.mobile.application.wifi.graphutils.GraphAdapter;
import com.botmasterzzz.mobile.application.wifi.graphutils.GraphViewNotifier;
import com.botmasterzzz.mobile.util.EnumUtils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;

import java.util.ArrayList;
import java.util.List;

class TimeGraphAdapter extends GraphAdapter {
    TimeGraphAdapter() {
        super(makeGraphViewNotifiers());
    }

    @NonNull
    private static List<GraphViewNotifier> makeGraphViewNotifiers() {
        return new ArrayList<>(CollectionUtils.collect(EnumUtils.values(WiFiBand.class), new ToGraphViewNotifier()));
    }

    private static class ToGraphViewNotifier implements Transformer<WiFiBand, GraphViewNotifier> {
        @Override
        public GraphViewNotifier transform(WiFiBand input) {
            return new TimeGraphView(input);
        }
    }

}
