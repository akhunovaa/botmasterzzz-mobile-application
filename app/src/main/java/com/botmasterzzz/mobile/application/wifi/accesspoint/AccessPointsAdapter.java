package com.botmasterzzz.mobile.application.wifi.accesspoint;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.application.wifi.model.WiFiData;
import com.botmasterzzz.mobile.application.wifi.model.WiFiDetail;
import com.botmasterzzz.mobile.application.wifi.scanner.UpdateNotifier;


class AccessPointsAdapter extends BaseExpandableListAdapter implements UpdateNotifier {
    private AccessPointsAdapterData accessPointsAdapterData;
    private AccessPointDetail accessPointDetail;
    private AccessPointPopup accessPointPopup;
    private ExpandableListView expandableListView;

    AccessPointsAdapter() {
        super();
        setAccessPointsAdapterData(new AccessPointsAdapterData());
        setAccessPointDetail(new AccessPointDetail());
        setAccessPointPopup(new AccessPointPopup());
        expandableListView = null;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        WiFiDetail wiFiDetail = (WiFiDetail) getGroup(groupPosition);
        View view = accessPointDetail.makeView(convertView, parent, wiFiDetail, false);
        attachPopup(view, wiFiDetail);

        ImageView groupIndicator = view.findViewById(R.id.groupIndicator);
        int childrenCount = getChildrenCount(groupPosition);
        if (childrenCount > 0) {
            groupIndicator.setVisibility(View.VISIBLE);
            groupIndicator.setImageResource(isExpanded
                ? R.drawable.ic_expand_less
                : R.drawable.ic_expand_more);
        } else {
            groupIndicator.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        WiFiDetail wiFiDetail = (WiFiDetail) getChild(groupPosition, childPosition);
        View view = accessPointDetail.makeView(convertView, parent, wiFiDetail, true);
        attachPopup(view, wiFiDetail);
        view.findViewById(R.id.groupIndicator).setVisibility(View.GONE);
        return view;
    }

    @Override
    public void update(@NonNull WiFiData wiFiData) {
        accessPointsAdapterData.update(wiFiData, expandableListView);
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return accessPointsAdapterData.parentsCount();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return accessPointsAdapterData.childrenCount(groupPosition);
    }

    @Override
    public Object getGroup(int groupPosition) {
        return accessPointsAdapterData.parent(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return accessPointsAdapterData.child(groupPosition, childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        accessPointsAdapterData.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        accessPointsAdapterData.onGroupExpanded(groupPosition);
    }

    void setAccessPointsAdapterData(@NonNull AccessPointsAdapterData accessPointsAdapterData) {
        this.accessPointsAdapterData = accessPointsAdapterData;
    }

    void setAccessPointDetail(@NonNull AccessPointDetail accessPointDetail) {
        this.accessPointDetail = accessPointDetail;
    }

    void setAccessPointPopup(@NonNull AccessPointPopup accessPointPopup) {
        this.accessPointPopup = accessPointPopup;
    }

    void setExpandableListView(ExpandableListView expandableListView) {
        this.expandableListView = expandableListView;
    }

    private void attachPopup(@NonNull View view, @NonNull WiFiDetail wiFiDetail) {
        View popupView = view.findViewById(R.id.attachPopup);
        if (popupView != null) {
            accessPointPopup.attach(popupView, wiFiDetail);
            accessPointPopup.attach(view.findViewById(R.id.ssid), wiFiDetail);
        }
    }

}
