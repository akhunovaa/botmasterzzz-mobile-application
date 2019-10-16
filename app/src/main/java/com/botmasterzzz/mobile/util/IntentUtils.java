package com.botmasterzzz.mobile.util;

import android.content.Intent;

import androidx.annotation.NonNull;

public class IntentUtils {
    private IntentUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Intent makeIntent(@NonNull String action) {
        return new Intent(action);
    }

}
