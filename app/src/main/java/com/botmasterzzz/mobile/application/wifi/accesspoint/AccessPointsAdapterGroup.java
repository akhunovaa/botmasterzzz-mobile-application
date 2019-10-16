package com.botmasterzzz.mobile.application.wifi.accesspoint;

import android.widget.ExpandableListView;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.MainContext;
import com.botmasterzzz.mobile.application.wifi.model.GroupBy;
import com.botmasterzzz.mobile.application.wifi.model.WiFiDetail;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class AccessPointsAdapterGroup {
    private final Set<String> expanded;
    private GroupBy groupBy;

    AccessPointsAdapterGroup() {
        expanded = new HashSet<>();
        groupBy = null;
    }

    void update(@NonNull List<WiFiDetail> wiFiDetails, ExpandableListView expandableListView) {
        updateGroupBy();
        if (isGroupExpandable() && expandableListView != null) {
            int groupCount = expandableListView.getExpandableListAdapter().getGroupCount();
            for (int i = 0; i < groupCount; i++) {
                WiFiDetail wiFiDetail = getWiFiDetail(wiFiDetails, i);
                if (expanded.contains(getGroupExpandKey(wiFiDetail))) {
                    expandableListView.expandGroup(i);
                } else {
                    expandableListView.collapseGroup(i);
                }
            }
        }
    }

    void updateGroupBy() {
        GroupBy currentGroupBy = MainContext.INSTANCE.getSettings().getGroupBy();
        if (!currentGroupBy.equals(this.groupBy)) {
            expanded.clear();
            this.groupBy = currentGroupBy;
        }
    }

    GroupBy getGroupBy() {
        return groupBy;
    }


    void onGroupCollapsed(@NonNull List<WiFiDetail> wiFiDetails, int groupPosition) {
        if (isGroupExpandable()) {
            WiFiDetail wiFiDetail = getWiFiDetail(wiFiDetails, groupPosition);
            if (wiFiDetail.noChildren()) {
                expanded.remove(getGroupExpandKey(wiFiDetail));
            }
        }
    }

    void onGroupExpanded(@NonNull List<WiFiDetail> wiFiDetails, int groupPosition) {
        if (isGroupExpandable()) {
            WiFiDetail wiFiDetail = getWiFiDetail(wiFiDetails, groupPosition);
            if (wiFiDetail.noChildren()) {
                expanded.add(getGroupExpandKey(wiFiDetail));
            }
        }
    }

    boolean isGroupExpandable() {
        return GroupBy.SSID.equals(this.groupBy) || GroupBy.CHANNEL.equals(this.groupBy);
    }

    String getGroupExpandKey(@NonNull WiFiDetail wiFiDetail) {
        String result = StringUtils.EMPTY;
        if (GroupBy.SSID.equals(this.groupBy)) {
            result = wiFiDetail.getSSID();
        }
        if (GroupBy.CHANNEL.equals(this.groupBy)) {
            result += Integer.toString(wiFiDetail.getWiFiSignal().getPrimaryWiFiChannel().getChannel());
        }
        return result;
    }

    Set<String> getExpanded() {
        return expanded;
    }

    private WiFiDetail getWiFiDetail(@NonNull List<WiFiDetail> wiFiDetails, int index) {
        try {
            return wiFiDetails.get(index);
        } catch (IndexOutOfBoundsException e) {
            return WiFiDetail.EMPTY;
        }
    }

}
