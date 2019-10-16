package com.botmasterzzz.mobile.application.wifi.timegraph;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.botmasterzzz.mobile.application.MainContext;
import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.application.util.BuildUtils;
import com.botmasterzzz.mobile.application.wifi.graphutils.GraphViewAdd;

import org.apache.commons.collections4.IterableUtils;

public class TimeGraphFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private TimeGraphAdapter timeGraphAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph_content, container, false);

        swipeRefreshLayout = view.findViewById(R.id.graphRefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        if (BuildUtils.isMinVersionP()) {
            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.setEnabled(false);
        }

        timeGraphAdapter = new TimeGraphAdapter();
        addGraphViews(swipeRefreshLayout, timeGraphAdapter);

        MainContext.INSTANCE.getScannerService().register(timeGraphAdapter);

        return view;
    }

    private void addGraphViews(View view, TimeGraphAdapter timeGraphAdapter) {
        IterableUtils.forEach(timeGraphAdapter.getGraphViews(),
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
        MainContext.INSTANCE.getScannerService().unregister(timeGraphAdapter);
        super.onDestroy();
    }

    TimeGraphAdapter getTimeGraphAdapter() {
        return timeGraphAdapter;
    }

}
