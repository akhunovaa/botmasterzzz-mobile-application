package com.botmasterzzz.mobile.application.wifi.channelgraph;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import com.botmasterzzz.mobile.application.wifi.band.WiFiBand;
import com.botmasterzzz.mobile.application.wifi.band.WiFiChannel;
import com.botmasterzzz.mobile.application.wifi.graphutils.GraphAdapter;
import com.botmasterzzz.mobile.application.wifi.graphutils.GraphViewNotifier;
import com.botmasterzzz.mobile.application.wifi.model.WiFiData;
import com.botmasterzzz.mobile.util.EnumUtils;

import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.IterableUtils;

import java.util.ArrayList;
import java.util.List;

class ChannelGraphAdapter extends GraphAdapter {
    private final ChannelGraphNavigation channelGraphNavigation;

    ChannelGraphAdapter(@NonNull ChannelGraphNavigation channelGraphNavigation) {
        super(makeGraphViewNotifiers());
        this.channelGraphNavigation = channelGraphNavigation;
    }

    private static List<GraphViewNotifier> makeGraphViewNotifiers() {
        List<GraphViewNotifier> graphViewNotifiers = new ArrayList<>();
        IterableUtils.forEach(EnumUtils.values(WiFiBand.class), new WiFiBandClosure(graphViewNotifiers));
        return graphViewNotifiers;
    }

    @Override
    public void update(@NonNull WiFiData wiFiData) {
        super.update(wiFiData);
        channelGraphNavigation.update(wiFiData);
    }

    private static class WiFiBandClosure implements Closure<WiFiBand> {
        private final List<GraphViewNotifier> graphViewNotifiers;

        private WiFiBandClosure(@NonNull List<GraphViewNotifier> graphViewNotifiers) {
            this.graphViewNotifiers = graphViewNotifiers;
        }

        @Override
        public void execute(WiFiBand wiFiBand) {
            IterableUtils.forEach(wiFiBand.getWiFiChannels().getWiFiChannelPairs(), new WiFiChannelClosure(graphViewNotifiers, wiFiBand));
        }
    }

    private static class WiFiChannelClosure implements Closure<Pair<WiFiChannel, WiFiChannel>> {
        private final List<GraphViewNotifier> graphViewNotifiers;
        private final WiFiBand wiFiBand;

        private WiFiChannelClosure(@NonNull List<GraphViewNotifier> graphViewNotifiers, @NonNull WiFiBand wiFiBand) {
            this.graphViewNotifiers = graphViewNotifiers;
            this.wiFiBand = wiFiBand;
        }

        @Override
        public void execute(Pair<WiFiChannel, WiFiChannel> wiFiChannelPair) {
            graphViewNotifiers.add(new ChannelGraphView(wiFiBand, wiFiChannelPair));
        }
    }
}
