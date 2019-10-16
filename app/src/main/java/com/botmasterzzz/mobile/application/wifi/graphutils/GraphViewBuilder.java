package com.botmasterzzz.mobile.application.wifi.graphutils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.settings.ThemeStyle;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.Viewport;

public class GraphViewBuilder {
    private final Context context;
    private final int numHorizontalLabels;
    private final int maximumY;
    private final ThemeStyle themeStyle;
    private final LayoutParams layoutParams;
    private LabelFormatter labelFormatter;
    private String verticalTitle;
    private String horizontalTitle;
    private boolean horizontalLabelsVisible;

    public GraphViewBuilder(@NonNull Context context, int numHorizontalLabels, int maximumY, @NonNull ThemeStyle themeStyle) {
        this.context = context;
        this.numHorizontalLabels = numHorizontalLabels;
        this.maximumY = (maximumY > GraphConstants.MAX_Y || maximumY < GraphConstants.MIN_Y_HALF)
            ? GraphConstants.MAX_Y_DEFAULT : maximumY;
        this.themeStyle = themeStyle;
        this.layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.horizontalLabelsVisible = true;
    }

    public GraphViewBuilder setLabelFormatter(@NonNull LabelFormatter labelFormatter) {
        this.labelFormatter = labelFormatter;
        return this;
    }

    public GraphViewBuilder setVerticalTitle(@NonNull String verticalTitle) {
        this.verticalTitle = verticalTitle;
        return this;
    }

    public GraphViewBuilder setHorizontalTitle(@NonNull String horizontalTitle) {
        this.horizontalTitle = horizontalTitle;
        return this;
    }

    public GraphViewBuilder setHorizontalLabelsVisible(boolean horizontalLabelsVisible) {
        this.horizontalLabelsVisible = horizontalLabelsVisible;
        return this;
    }

    public GraphView build() {
        GraphView graphView = new GraphView(context);
        setGraphView(graphView);
        setGridLabelRenderer(graphView);
        setViewPortY(graphView);
        return graphView;
    }

    LayoutParams getLayoutParams() {
        return layoutParams;
    }

    void setGraphView(@NonNull GraphView graphView) {
        graphView.setLayoutParams(layoutParams);
        graphView.setVisibility(View.GONE);
    }

    void setViewPortY(@NonNull GraphView graphView) {
        Viewport viewport = graphView.getViewport();
        viewport.setScrollable(true);
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(GraphConstants.MIN_Y);
        viewport.setMaxY(getMaximumY());
        viewport.setXAxisBoundsManual(true);
    }

    void setGridLabelRenderer(@NonNull GraphView graphView) {
        GridLabelRenderer gridLabelRenderer = graphView.getGridLabelRenderer();
        gridLabelRenderer.setHumanRounding(false);
        gridLabelRenderer.setHighlightZeroLines(false);
        gridLabelRenderer.setNumVerticalLabels(getNumVerticalLabels());
        gridLabelRenderer.setNumHorizontalLabels(numHorizontalLabels);
        gridLabelRenderer.setVerticalLabelsVisible(true);
        gridLabelRenderer.setHorizontalLabelsVisible(horizontalLabelsVisible);
        gridLabelRenderer.setTextSize(gridLabelRenderer.getTextSize() * GraphConstants.TEXT_SIZE_ADJUSTMENT);

        gridLabelRenderer.reloadStyles();
        if (labelFormatter != null) {
            gridLabelRenderer.setLabelFormatter(labelFormatter);
        }
        if (verticalTitle != null) {
            gridLabelRenderer.setVerticalAxisTitle(verticalTitle);
            gridLabelRenderer.setVerticalAxisTitleTextSize(
                gridLabelRenderer.getVerticalAxisTitleTextSize() * GraphConstants.AXIS_TEXT_SIZE_ADJUSTMENT);
        }
        if (horizontalTitle != null) {
            gridLabelRenderer.setHorizontalAxisTitle(horizontalTitle);
            gridLabelRenderer.setHorizontalAxisTitleTextSize(
                gridLabelRenderer.getHorizontalAxisTitleTextSize() * GraphConstants.AXIS_TEXT_SIZE_ADJUSTMENT);
        }

        setGridLabelRenderColors(gridLabelRenderer);
    }

    private void setGridLabelRenderColors(@NonNull GridLabelRenderer gridLabelRenderer) {
        int color = ThemeStyle.DARK.equals(themeStyle) ? Color.WHITE : Color.BLACK;
        gridLabelRenderer.setGridColor(Color.GRAY);
        gridLabelRenderer.setVerticalLabelsColor(color);
        gridLabelRenderer.setVerticalAxisTitleColor(color);
        gridLabelRenderer.setHorizontalLabelsColor(color);
        gridLabelRenderer.setHorizontalAxisTitleColor(color);
    }

    int getNumVerticalLabels() {
        return (getMaximumY() - GraphConstants.MIN_Y) / 10 + 1;
    }

    int getMaximumY() {
        return maximumY;
    }

}
