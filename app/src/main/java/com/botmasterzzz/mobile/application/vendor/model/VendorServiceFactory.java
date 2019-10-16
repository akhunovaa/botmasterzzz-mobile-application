package com.botmasterzzz.mobile.application.vendor.model;

import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.vendor.VendorService;

public class VendorServiceFactory {
    private VendorServiceFactory() {
        throw new IllegalStateException("Factory class");
    }

    public static VendorService makeVendorService(@NonNull Resources resources) {
        return new VendorDB(resources);
    }
}
