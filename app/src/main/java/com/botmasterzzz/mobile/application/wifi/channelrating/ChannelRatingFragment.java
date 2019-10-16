package com.botmasterzzz.mobile.application.wifi.channelrating;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;

import com.botmasterzzz.mobile.application.MainContext;
import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.application.util.BuildUtils;

public class ChannelRatingFragment extends Fragment implements OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ChannelRatingAdapter channelRatingAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.channel_rating_content, container, false);

        swipeRefreshLayout = view.findViewById(R.id.channelRatingRefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        if (BuildUtils.isMinVersionP()) {
            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.setEnabled(false);
        }

        TextView bestChannels = view.findViewById(R.id.channelRatingBestChannels);
        ListView listView = view.findViewById(R.id.channelRatingView);

        channelRatingAdapter = new ChannelRatingAdapter(getActivity(), bestChannels);
        listView.setAdapter(channelRatingAdapter);

        MainContext.INSTANCE.getScannerService().register(channelRatingAdapter);

        return view;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        MainContext.INSTANCE.getScannerService().update();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void onDestroy() {
        MainContext.INSTANCE.getScannerService().unregister(channelRatingAdapter);
        super.onDestroy();
    }

    ChannelRatingAdapter getChannelRatingAdapter() {
        return channelRatingAdapter;
    }

}
