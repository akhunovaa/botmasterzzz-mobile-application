package com.botmasterzzz.mobile.application.navigation.options;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.util.EnumUtils;

import org.apache.commons.collections4.Predicate;

enum OptionAction {
    NO_ACTION(-1, new NoAction()),
    SCANNER(R.id.action_scanner, new ScannerAction()),
    FILTER(R.id.action_filter, new FilterAction()),
    WIFI_BAND(R.id.action_wifi_band, new WiFiBandAction());

    private final int key;
    private final Action action;

    OptionAction(int key, @NonNull Action action) {
        this.key = key;
        this.action = action;
    }

    @NonNull
    public static OptionAction findOptionAction(int key) {
        return EnumUtils.find(OptionAction.class, new ActionPredicate(key), NO_ACTION);
    }

    int getKey() {
        return key;
    }

    @NonNull
    Action getAction() {
        return action;
    }

    private static class ActionPredicate implements Predicate<OptionAction> {
        private final int key;

        private ActionPredicate(int key) {
            this.key = key;
        }

        @Override
        public boolean evaluate(OptionAction object) {
            return key == object.key;
        }
    }

    static class NoAction implements Action {
        @Override
        public void execute() {
            // do nothing
        }
    }

}
