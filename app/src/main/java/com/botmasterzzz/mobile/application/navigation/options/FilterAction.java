package com.botmasterzzz.mobile.application.navigation.options;

import com.botmasterzzz.mobile.application.wifi.filter.Filter;

class FilterAction implements Action {
    @Override
    public void execute() {
        Filter.build().show();
    }
}

