package com.botmasterzzz.mobile.application.wifi.channelavailable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.botmasterzzz.mobile.application.MainContext;
import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.application.wifi.band.WiFiChannelCountry;

import java.util.ArrayList;
import java.util.List;

public class ChannelAvailableFragment extends ListFragment {
    private ChannelAvailableAdapter channelAvailableAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.channel_available_content, container, false);
        channelAvailableAdapter = new ChannelAvailableAdapter(getActivity(), getChannelAvailable());
        setListAdapter(channelAvailableAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        channelAvailableAdapter.clear();
        channelAvailableAdapter.addAll(getChannelAvailable());
    }

    @NonNull
    private List<WiFiChannelCountry> getChannelAvailable() {
        List<WiFiChannelCountry> results = new ArrayList<>();
        results.add(WiFiChannelCountry.get(MainContext.INSTANCE.getSettings().getCountryCode()));
        return results;
    }

}
