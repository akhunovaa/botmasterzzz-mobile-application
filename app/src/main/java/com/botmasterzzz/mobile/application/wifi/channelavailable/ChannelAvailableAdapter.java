package com.botmasterzzz.mobile.application.wifi.channelavailable;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.botmasterzzz.mobile.application.MainContext;
import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.application.wifi.band.WiFiBand;
import com.botmasterzzz.mobile.application.wifi.band.WiFiChannelCountry;

import java.util.List;
import java.util.Locale;

class ChannelAvailableAdapter extends ArrayAdapter<WiFiChannelCountry> {
    private static final String SEPARATOR = ",";

    ChannelAvailableAdapter(@NonNull Context context, @NonNull List<WiFiChannelCountry> wiFiChannelCountries) {
        super(context, R.layout.channel_available_details, wiFiChannelCountries);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MainContext mainContext = MainContext.INSTANCE;
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = mainContext.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.channel_available_details, parent, false);
        }

        WiFiChannelCountry wiFiChannelCountry = getItem(position);
        view.<TextView>findViewById(R.id.channel_available_title_ghz_2)
            .setText(String.format(Locale.ENGLISH, "%s : ",
                view.getResources().getString(WiFiBand.GHZ2.getTextResource())));
        view.<TextView>findViewById(R.id.channel_available_ghz_2)
            .setText(TextUtils.join(SEPARATOR, wiFiChannelCountry.getChannelsGHZ2().toArray()));
        view.<TextView>findViewById(R.id.channel_available_title_ghz_5)
            .setText(String.format(Locale.ENGLISH, "%s : ",
                view.getResources().getString(WiFiBand.GHZ5.getTextResource())));
        view.<TextView>findViewById(R.id.channel_available_ghz_5)
            .setText(TextUtils.join(SEPARATOR, wiFiChannelCountry.getChannelsGHZ5().toArray()));
        return view;
    }

}
