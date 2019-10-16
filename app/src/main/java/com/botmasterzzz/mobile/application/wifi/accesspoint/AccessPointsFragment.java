package com.botmasterzzz.mobile.application.wifi.accesspoint;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;

import com.botmasterzzz.mobile.application.MainContext;
import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.application.util.BuildUtils;

public class AccessPointsFragment extends Fragment implements OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private AccessPointsAdapter accessPointsAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.access_points_content, container, false);

        swipeRefreshLayout = view.findViewById(R.id.accessPointsRefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        if (BuildUtils.isMinVersionP()) {
            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.setEnabled(false);
        }

        accessPointsAdapter = new AccessPointsAdapter();
        ExpandableListView expandableListView = view.findViewById(R.id.accessPointsView);
        expandableListView.setAdapter(accessPointsAdapter);
        accessPointsAdapter.setExpandableListView(expandableListView);

        MainContext.INSTANCE.getScannerService().register(accessPointsAdapter);

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
        MainContext.INSTANCE.getScannerService().unregister(accessPointsAdapter);
        super.onDestroy();
    }

    AccessPointsAdapter getAccessPointsAdapter() {
        return accessPointsAdapter;
    }

}
