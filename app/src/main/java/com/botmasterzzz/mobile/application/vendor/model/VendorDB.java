package com.botmasterzzz.mobile.application.vendor.model;

import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.vendor.VendorService;
import com.botmasterzzz.mobile.util.FileUtils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.PredicateUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

class VendorDB implements VendorService {
    private final Resources resources;
    private final Map<String, List<String>> vendors;
    private final Map<String, String> macs;
    private boolean loaded;

    VendorDB(@NonNull Resources resources) {
        this.resources = resources;
        this.vendors = new TreeMap<>();
        this.macs = new TreeMap<>();
        this.loaded = false;
    }

    @NonNull
    @Override
    public String findVendorName(String address) {
        String result = getMacs().get(VendorUtils.clean(address));
        return result == null ? StringUtils.EMPTY : result;
    }

    @NonNull
    @Override
    public List<String> findMacAddresses(String vendorName) {
        if (StringUtils.isBlank(vendorName)) {
            return new ArrayList<>();
        }
        Locale locale = Locale.getDefault();
        List<String> results = getVendors().get(vendorName.toUpperCase(locale));
        return results == null ? new ArrayList<>() : results;
    }

    @NonNull
    @Override
    public List<String> findVendors() {
        return findVendors(StringUtils.EMPTY);
    }

    @NonNull
    @Override
    public List<String> findVendors(@NonNull String filter) {
        if (StringUtils.isBlank(filter)) {
            return new ArrayList<>(getVendors().keySet());
        }
        Locale locale = Locale.getDefault();
        String filterToUpperCase = filter.toUpperCase(locale);
        List<Predicate<String>> predicates = Arrays.asList(new StringContains(filterToUpperCase), new MacContains(filterToUpperCase));
        return new ArrayList<>(CollectionUtils.select(getVendors().keySet(), PredicateUtils.anyPredicate(predicates)));
    }

    @NonNull
    Map<String, List<String>> getVendors() {
        load(resources);
        return vendors;
    }

    @NonNull
    Map<String, String> getMacs() {
        load(resources);
        return macs;
    }

    private void load(@NonNull Resources resources) {

    }

    private class StringContains implements Predicate<String> {
        private final String filter;

        private StringContains(@NonNull String filter) {
            this.filter = filter;
        }

        @Override
        public boolean evaluate(String object) {
            return object.contains(filter);
        }
    }

    private class MacContains implements Predicate<String> {
        private final String filter;

        private MacContains(@NonNull String filter) {
            this.filter = filter;
        }

        @Override
        public boolean evaluate(String object) {
            return IterableUtils.matchesAny(findMacAddresses(object), new StringContains(filter));
        }
    }

}
