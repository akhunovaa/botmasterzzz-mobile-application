package com.botmasterzzz.mobile.application.navigation.options;

import com.botmasterzzz.mobile.application.MainContext;

class WiFiBandAction implements Action {
    @Override
    public void execute() {
        MainContext.INSTANCE.getSettings().toggleWiFiBand();
    }
}
