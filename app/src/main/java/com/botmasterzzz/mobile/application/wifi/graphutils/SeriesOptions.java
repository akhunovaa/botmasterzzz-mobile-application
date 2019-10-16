package com.botmasterzzz.mobile.application.wifi.graphutils;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.util.TitleLineGraphSeries;
import com.jjoe64.graphview.series.BaseSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

class SeriesOptions {
    private GraphColors graphColors;

    SeriesOptions() {
        setGraphColors(new GraphColors());
    }

    void setGraphColors(@NonNull GraphColors graphColors) {
        this.graphColors = graphColors;
    }

    void highlightConnected(@NonNull BaseSeries<DataPoint> series, boolean connected) {
        int thickness = connected ? GraphConstants.THICKNESS_CONNECTED : GraphConstants.THICKNESS_REGULAR;
        if (series instanceof LineGraphSeries) {
            ((LineGraphSeries<DataPoint>) series).setThickness(thickness);
        } else if (series instanceof TitleLineGraphSeries) {
            TitleLineGraphSeries<DataPoint> titleLineGraphSeries = (TitleLineGraphSeries<DataPoint>) series;
            titleLineGraphSeries.setThickness(thickness);
            titleLineGraphSeries.setTextBold(connected);
        }
    }

    void setSeriesColor(@NonNull BaseSeries<DataPoint> series) {
        GraphColor graphColor = graphColors.getColor();
        series.setColor((int) graphColor.getPrimary());
        if (series instanceof LineGraphSeries) {
            ((LineGraphSeries<DataPoint>) series).setBackgroundColor((int) graphColor.getBackground());
        } else if (series instanceof TitleLineGraphSeries) {
            ((TitleLineGraphSeries<DataPoint>) series).setBackgroundColor((int) graphColor.getBackground());
        }
    }

    void drawBackground(@NonNull BaseSeries<DataPoint> series, boolean drawBackground) {
        if (series instanceof LineGraphSeries) {
            ((LineGraphSeries<DataPoint>) series).setDrawBackground(drawBackground);
        } else if (series instanceof TitleLineGraphSeries) {
            ((TitleLineGraphSeries<DataPoint>) series).setDrawBackground(drawBackground);
        }
    }

    void removeSeriesColor(@NonNull BaseSeries<DataPoint> series) {
        graphColors.addColor(series.getColor());
    }

}
