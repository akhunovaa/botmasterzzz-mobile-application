package com.botmasterzzz.mobile.application.navigation.options;

import com.botmasterzzz.mobile.application.MainContext;
import com.botmasterzzz.mobile.application.wifi.scanner.ScannerService;

class ScannerAction implements Action {
    @Override
    public void execute() {
        ScannerService scannerService = MainContext.INSTANCE.getScannerService();
        if (scannerService.isRunning()) {
            scannerService.pause();
        } else {
            scannerService.resume();
        }
    }
}
