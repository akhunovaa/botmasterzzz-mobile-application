package com.botmasterzzz.mobile.application.vendor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.botmasterzzz.mobile.application.MainContext;
import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.util.TextUtils;

public class VendorFragment extends ListFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vendor_content, container, false);
        VendorAdapter vendorAdapter = new VendorAdapter(getActivity(), MainContext.INSTANCE.getVendorService());
        setListAdapter(vendorAdapter);

        SearchView searchView = view.findViewById(R.id.vendorSearchText);
        searchView.setOnQueryTextListener(new Listener(vendorAdapter));

        return view;
    }

    static class Listener implements OnQueryTextListener {
        private final VendorAdapter vendorAdapter;

        Listener(@NonNull VendorAdapter vendorAdapter) {
            this.vendorAdapter = vendorAdapter;
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            vendorAdapter.update(TextUtils.trim(newText));
            return true;
        }
    }

}
