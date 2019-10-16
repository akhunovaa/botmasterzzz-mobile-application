package com.botmasterzzz.mobile.application.wifi.graphutils;

import com.jjoe64.graphview.LegendRenderer;

public enum GraphLegend {
    LEFT(new DisplayLeft()),
    RIGHT(new DisplayRight()),
    HIDE(new DisplayNone());

    private final Display display;

    GraphLegend(Display display) {
        this.display = display;
    }

    public void display(LegendRenderer legendRenderer) {
        display.display(legendRenderer);
    }

    Display getDisplay() {
        return display;
    }

    private interface Display {
        void display(LegendRenderer legendRenderer);
    }

    protected static class DisplayNone implements Display {
        @Override
        public void display(LegendRenderer legendRenderer) {
            legendRenderer.setVisible(false);
        }
    }

    protected static class DisplayLeft implements Display {
        @Override
        public void display(LegendRenderer legendRenderer) {
            legendRenderer.setVisible(true);
            legendRenderer.setFixedPosition(0, 0);
        }
    }

    protected static class DisplayRight implements Display {
        @Override
        public void display(LegendRenderer legendRenderer) {
            legendRenderer.setVisible(true);
            legendRenderer.setAlign(LegendRenderer.LegendAlign.TOP);
        }
    }

}
