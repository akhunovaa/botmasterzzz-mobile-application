package com.botmasterzzz.mobile.application.wifi.graphutils;

import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.MainContext;
import com.botmasterzzz.mobile.application.R;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.Predicate;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

class GraphColors {
    private final List<GraphColor> availableGraphColors;
    private final Deque<GraphColor> currentGraphColors;

    GraphColors() {
        currentGraphColors = new ArrayDeque<>();
        availableGraphColors = new ArrayList<>();
    }

    @NonNull
    private List<GraphColor> getAvailableGraphColors() {
        if (availableGraphColors.isEmpty()) {
            Resources resources = MainContext.INSTANCE.getResources();
            String[] colorsAsStrings = resources.getStringArray(R.array.graph_colors);
            for (int i = 0; i < colorsAsStrings.length; i += 2) {
                GraphColor graphColor = new GraphColor(Long.parseLong(colorsAsStrings[i].substring(1), 16), Long.parseLong(colorsAsStrings[i + 1].substring(1), 16));
                availableGraphColors.add(graphColor);
            }
        }
        return availableGraphColors;
    }

    @NonNull
    GraphColor getColor() {
        if (currentGraphColors.isEmpty()) {
            for (GraphColor graphColor : getAvailableGraphColors()) {
                currentGraphColors.push(graphColor);
            }
        }
        return currentGraphColors.pop();
    }

    void addColor(long primaryColor) {
        GraphColor graphColor = IterableUtils.find(getAvailableGraphColors(), new ColorPredicate(primaryColor));
        if (graphColor == null || currentGraphColors.contains(graphColor)) {
            return;
        }
        currentGraphColors.push(graphColor);
    }

    private class ColorPredicate implements Predicate<GraphColor> {
        private final long primaryColor;

        private ColorPredicate(long primaryColor) {
            this.primaryColor = primaryColor;
        }

        @Override
        public boolean evaluate(GraphColor graphColor) {
            return primaryColor == graphColor.getPrimary();
        }
    }

}
