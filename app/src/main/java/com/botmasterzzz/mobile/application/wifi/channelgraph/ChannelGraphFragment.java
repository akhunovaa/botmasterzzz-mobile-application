package com.botmasterzzz.mobile.application.wifi.channelgraph;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;

import com.botmasterzzz.mobile.application.MainContext;
import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.application.util.BuildUtils;
import com.botmasterzzz.mobile.application.wifi.graphutils.GraphViewAdd;

import org.apache.commons.collections4.IterableUtils;

public class ChannelGraphFragment extends Fragment implements OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ChannelGraphAdapter channelGraphAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph_content, container, false);

        swipeRefreshLayout = view.findViewById(R.id.graphRefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        if (BuildUtils.isMinVersionP()) {
            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.setEnabled(false);
        }

        LinearLayout linearLayout = view.findViewById(R.id.graphNavigation);
        ChannelGraphNavigation channelGraphNavigation = new ChannelGraphNavigation(linearLayout, getActivity());
        channelGraphAdapter = new ChannelGraphAdapter(channelGraphNavigation);
        addGraphViews(swipeRefreshLayout, channelGraphAdapter);

        MainContext.INSTANCE.getScannerService().register(channelGraphAdapter);

        return view;
    }

    private void addGraphViews(View view, ChannelGraphAdapter channelGraphAdapter) {
        IterableUtils.forEach(channelGraphAdapter.getGraphViews(),
            new GraphViewAdd(view.findViewById(R.id.graphFlipper)));
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
        MainContext.INSTANCE.getScannerService().unregister(channelGraphAdapter);
        super.onDestroy();
    }

    ChannelGraphAdapter getChannelGraphAdapter() {
        return channelGraphAdapter;
    }

}
