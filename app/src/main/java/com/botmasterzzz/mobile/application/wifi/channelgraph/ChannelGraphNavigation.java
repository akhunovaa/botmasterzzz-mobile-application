package com.botmasterzzz.mobile.application.wifi.channelgraph;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;

import com.botmasterzzz.mobile.application.Configuration;
import com.botmasterzzz.mobile.application.MainContext;
import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.application.settings.Settings;
import com.botmasterzzz.mobile.application.wifi.band.WiFiBand;
import com.botmasterzzz.mobile.application.wifi.band.WiFiChannel;
import com.botmasterzzz.mobile.application.wifi.band.WiFiChannels;
import com.botmasterzzz.mobile.application.wifi.band.WiFiChannelsGHZ5;
import com.botmasterzzz.mobile.application.wifi.model.WiFiData;
import com.botmasterzzz.mobile.application.wifi.model.WiFiDetail;
import com.botmasterzzz.mobile.application.wifi.predicate.FilterPredicate;
import com.botmasterzzz.mobile.util.TextUtils;

import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.Predicate;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

class ChannelGraphNavigation {
    static final Map<Pair<WiFiChannel, WiFiChannel>, Integer> ids = new HashMap<>();
    private static final String ACTIVITY_NONE = "&#8722";
    private static final String ACTIVITY_ON = "&#9585;&#9586;";

    static {
        ids.put(WiFiChannelsGHZ5.SET1, R.id.graphNavigationSet1);
        ids.put(WiFiChannelsGHZ5.SET2, R.id.graphNavigationSet2);
        ids.put(WiFiChannelsGHZ5.SET3, R.id.graphNavigationSet3);
    }

    private final Context context;
    private final View view;

    ChannelGraphNavigation(@NonNull View inputView, @NonNull Context context) {
        this.view = inputView;
        this.context = context;
        IterableUtils.forEach(ids.keySet(), new OnClickListenerClosure());
    }

    void update(@NonNull WiFiData wiFiData) {
        Collection<Pair<WiFiChannel, WiFiChannel>> visible = CollectionUtils.select(ids.keySet(), new PairPredicate());
        updateButtons(wiFiData, visible);
        view.setVisibility(visible.size() > 1 ? View.VISIBLE : View.GONE);
    }

    private void updateButtons(@NonNull WiFiData wiFiData, Collection<Pair<WiFiChannel, WiFiChannel>> visible) {
        if (visible.size() > 1) {
            MainContext mainContext = MainContext.INSTANCE;
            Configuration configuration = mainContext.getConfiguration();
            Settings settings = mainContext.getSettings();
            Predicate<WiFiDetail> predicate = FilterPredicate.makeOtherPredicate(settings);
            Pair<WiFiChannel, WiFiChannel> selectedWiFiChannelPair = configuration.getWiFiChannelPair();
            List<WiFiDetail> wiFiDetails = wiFiData.getWiFiDetails(predicate, settings.getSortBy());
            IterableUtils.forEach(ids.keySet(), new ButtonClosure(visible, selectedWiFiChannelPair, wiFiDetails));
        }
    }

    static class SetOnClickListener implements OnClickListener {
        private final Pair<WiFiChannel, WiFiChannel> wiFiChannelPair;

        SetOnClickListener(@NonNull Pair<WiFiChannel, WiFiChannel> wiFiChannelPair) {
            this.wiFiChannelPair = wiFiChannelPair;
        }

        @Override
        public void onClick(View view) {
            MainContext mainContext = MainContext.INSTANCE;
            mainContext.getConfiguration().setWiFiChannelPair(wiFiChannelPair);
            mainContext.getScannerService().update();
        }
    }

    private class OnClickListenerClosure implements Closure<Pair<WiFiChannel, WiFiChannel>> {
        public void execute(Pair<WiFiChannel, WiFiChannel> input) {
            view.findViewById(ids.get(input)).setOnClickListener(new SetOnClickListener(input));
        }
    }

    private class ButtonClosure implements Closure<Pair<WiFiChannel, WiFiChannel>> {
        private final Collection<Pair<WiFiChannel, WiFiChannel>> visible;
        private final Pair<WiFiChannel, WiFiChannel> selectedWiFiChannelPair;
        private final List<WiFiDetail> wiFiDetails;

        private ButtonClosure(@NonNull Collection<Pair<WiFiChannel, WiFiChannel>> visible,
                              @NonNull Pair<WiFiChannel, WiFiChannel> selectedWiFiChannelPair,
                              @NonNull List<WiFiDetail> wiFiDetails) {
            this.visible = visible;
            this.selectedWiFiChannelPair = selectedWiFiChannelPair;
            this.wiFiDetails = wiFiDetails;
        }

        @Override
        public void execute(Pair<WiFiChannel, WiFiChannel> input) {
            Button button = view.findViewById(ids.get(input));
            if (visible.contains(input)) {
                button.setVisibility(View.VISIBLE);
                setSelected(button, input.equals(selectedWiFiChannelPair));
                setActivity(button, input, IterableUtils.matchesAny(wiFiDetails, new InRangePredicate(input)));
            } else {
                button.setVisibility(View.GONE);
                setSelected(button, false);
            }
        }

        private void setSelected(Button button, boolean selected) {
            if (selected) {
                button.setBackgroundColor(ContextCompat.getColor(context, R.color.selected));
                button.setSelected(true);
            } else {
                button.setBackgroundColor(ContextCompat.getColor(context, R.color.background));
                button.setSelected(false);
            }
        }

        private void setActivity(Button button, Pair<WiFiChannel, WiFiChannel> pair, boolean activity) {
            button.setText(TextUtils.fromHtml(String.format(Locale.ENGLISH, "<strong>%d %s %d</strong>",
                pair.first.getChannel(),
                activity ? ACTIVITY_ON : ACTIVITY_NONE,
                pair.second.getChannel())));
        }
    }

    private class PairPredicate implements Predicate<Pair<WiFiChannel, WiFiChannel>> {
        private final WiFiBand wiFiBand;
        private final String countryCode;
        private final WiFiChannels wiFiChannels;

        private PairPredicate() {
            Settings settings = MainContext.INSTANCE.getSettings();
            wiFiBand = settings.getWiFiBand();
            countryCode = settings.getCountryCode();
            wiFiChannels = wiFiBand.getWiFiChannels();
        }

        @Override
        public boolean evaluate(Pair<WiFiChannel, WiFiChannel> object) {
            return wiFiBand.isGHZ5() && wiFiChannels.isChannelAvailable(countryCode, object.first.getChannel());
        }
    }

}
