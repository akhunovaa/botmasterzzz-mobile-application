package com.botmasterzzz.mobile.application.vendor;

import androidx.annotation.NonNull;

import java.util.List;

public interface VendorService {
    @NonNull
    String findVendorName(String macAddress);

    @NonNull
    List<String> findMacAddresses(String vendorName);

    @NonNull
    List<String> findVendors();

    @NonNull
    List<String> findVendors(String filter);
}
