package com.botmasterzzz.mobile.application.wifi.accesspoint;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.application.wifi.model.WiFiDetail;

public class AccessPointPopup {

    public Dialog show(@NonNull View view) {
        try {
            Dialog dialog = new Dialog(view.getContext());
            dialog.setContentView(view);
            dialog.findViewById(R.id.popupButtonClose).setOnClickListener(new PopupDialogCloseListener(dialog));
            dialog.show();
            return dialog;
        } catch (Exception e) {
            // ignore: unable to show details
            return null;
        }
    }

    void attach(@NonNull View view, @NonNull WiFiDetail wiFiDetail) {
        view.setOnClickListener(new PopupDialogOpenListener(wiFiDetail));
    }

    private class PopupDialogCloseListener implements OnClickListener {
        private final Dialog dialog;

        PopupDialogCloseListener(@NonNull Dialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void onClick(View view) {
            dialog.dismiss();
        }
    }

    private class PopupDialogOpenListener implements OnClickListener {
        private final WiFiDetail wiFiDetail;

        PopupDialogOpenListener(@NonNull WiFiDetail wiFiDetail) {
            this.wiFiDetail = wiFiDetail;
        }

        @Override
        public void onClick(View view) {
            show(new AccessPointDetail().makeViewDetailed(wiFiDetail));
        }
    }

}
