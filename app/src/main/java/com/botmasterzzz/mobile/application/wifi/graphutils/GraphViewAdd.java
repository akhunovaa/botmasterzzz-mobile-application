package com.botmasterzzz.mobile.application.wifi.graphutils;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.jjoe64.graphview.GraphView;

import org.apache.commons.collections4.Closure;

public class GraphViewAdd implements Closure<GraphView> {
    private final ViewGroup viewGroup;

    public GraphViewAdd(@NonNull ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
    }

    @Override
    public void execute(GraphView graphView) {
        viewGroup.addView(graphView);
    }
}

