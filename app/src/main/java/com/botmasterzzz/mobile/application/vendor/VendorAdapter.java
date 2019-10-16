package com.botmasterzzz.mobile.application.vendor;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.botmasterzzz.mobile.application.MainContext;
import com.botmasterzzz.mobile.application.R;

class VendorAdapter extends ArrayAdapter<String> {
    private final VendorService vendorService;

    VendorAdapter(@NonNull Context context, @NonNull VendorService vendorService) {
        super(context, R.layout.vendor_details, vendorService.findVendors());
        this.vendorService = vendorService;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = MainContext.INSTANCE.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.vendor_details, parent, false);
        }
        String vendorName = getItem(position);
        view.<TextView>findViewById(R.id.vendor_name)
            .setText(vendorName);
        view.<TextView>findViewById(R.id.vendor_macs)
            .setText(TextUtils.join(", ", vendorService.findMacAddresses(vendorName)));
        return view;
    }

    void update(@NonNull String filter) {
        clear();
        addAll(vendorService.findVendors(filter));
    }

}
