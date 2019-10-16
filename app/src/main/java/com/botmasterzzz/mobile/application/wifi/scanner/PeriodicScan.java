package com.botmasterzzz.mobile.application.wifi.scanner;

import android.os.Handler;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.settings.Settings;

class PeriodicScan implements Runnable {
    static final long DELAY_INITIAL = 1L;
    static final long DELAY_INTERVAL = 1000L;

    private final ScannerService scanner;
    private final Handler handler;
    private final Settings settings;
    private boolean running;

    PeriodicScan(@NonNull ScannerService scanner, @NonNull Handler handler, @NonNull Settings settings) {
        this.scanner = scanner;
        this.handler = handler;
        this.settings = settings;
        start();
    }

    void stop() {
        handler.removeCallbacks(this);
        running = false;
    }

    void start() {
        nextRun(DELAY_INITIAL);
    }

    private void nextRun(long delayInitial) {
        stop();
        handler.postDelayed(this, delayInitial);
        running = true;
    }

    @Override
    public void run() {
        scanner.update();
        nextRun(settings.getScanSpeed() * DELAY_INTERVAL);
    }

    boolean isRunning() {
        return running;
    }
}
