package com.botmasterzzz.mobile.application.wifi.filter;

import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.application.wifi.filter.adapter.SSIDAdapter;
import com.botmasterzzz.mobile.util.TextUtils;

import org.apache.commons.lang3.StringUtils;

class SSIDFilter {
    SSIDFilter(@NonNull SSIDAdapter ssidAdapter, @NonNull Dialog dialog) {
        String value = TextUtils.join(ssidAdapter.getValues());

        EditText editText = dialog.findViewById(R.id.filterSSIDtext);
        editText.setText(value);
        editText.addTextChangedListener(new OnChange(ssidAdapter));

        dialog.findViewById(R.id.filterSSID).setVisibility(View.VISIBLE);
    }

    static class OnChange implements TextWatcher {
        private final SSIDAdapter ssidAdapter;

        OnChange(@NonNull SSIDAdapter ssidAdapter) {
            this.ssidAdapter = ssidAdapter;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //nothing
        }

        @Override
        public void afterTextChanged(Editable s) {
            ssidAdapter.setValues(TextUtils.split(s == null ? StringUtils.EMPTY : s.toString()));
        }
    }
}
