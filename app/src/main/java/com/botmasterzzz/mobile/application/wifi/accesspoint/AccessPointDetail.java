package com.botmasterzzz.mobile.application.wifi.accesspoint;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.botmasterzzz.mobile.application.MainContext;
import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.application.wifi.model.Security;
import com.botmasterzzz.mobile.application.wifi.model.Strength;
import com.botmasterzzz.mobile.application.wifi.model.WiFiAdditional;
import com.botmasterzzz.mobile.application.wifi.model.WiFiDetail;
import com.botmasterzzz.mobile.application.wifi.model.WiFiSignal;

import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

public class AccessPointDetail {
    private static final int VENDOR_SHORT_MAX = 12;
    private static final int VENDOR_LONG_MAX = 30;

    View makeView(View convertView, ViewGroup parent, @NonNull WiFiDetail wiFiDetail, boolean isChild) {
        AccessPointViewType accessPointViewType = MainContext.INSTANCE.getSettings().getAccessPointView();
        return makeView(convertView, parent, wiFiDetail, isChild, accessPointViewType);
    }

    View makeView(View convertView, ViewGroup parent, @NonNull WiFiDetail wiFiDetail, boolean isChild, @NonNull AccessPointViewType accessPointViewType) {
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = MainContext.INSTANCE.getLayoutInflater();
            view = layoutInflater.inflate(accessPointViewType.getLayout(), parent, false);
        }
        setViewCompact(view, wiFiDetail, isChild);
        if (view.findViewById(R.id.capabilities) != null) {
            setViewExtra(view, wiFiDetail);
            setViewVendorShort(view, wiFiDetail.getWiFiAdditional());
        }

        return view;
    }

    public View makeViewDetailed(@NonNull WiFiDetail wiFiDetail) {
        View view = MainContext.INSTANCE.getLayoutInflater().inflate(R.layout.access_point_view_popup, null);

        setViewCompact(view, wiFiDetail, false);
        setViewExtra(view, wiFiDetail);
        setViewVendorLong(view, wiFiDetail.getWiFiAdditional());
        setView80211mc(view, wiFiDetail.getWiFiSignal());
        enableTextSelection(view);

        return view;
    }

    private void setView80211mc(View view, WiFiSignal wiFiSignal) {
        view.findViewById(R.id.flag80211mc).setVisibility(wiFiSignal.is80211mc() ? View.VISIBLE : View.GONE);
    }

    private void enableTextSelection(View view) {
        view.<TextView>findViewById(R.id.ssid).setTextIsSelectable(true);
        view.<TextView>findViewById(R.id.vendorLong).setTextIsSelectable(true);
    }

    private void setViewCompact(@NonNull View view, @NonNull WiFiDetail wiFiDetail, boolean isChild) {
        Context context = view.getContext();

        view.<TextView>findViewById(R.id.ssid).setText(wiFiDetail.getTitle());

        WiFiSignal wiFiSignal = wiFiDetail.getWiFiSignal();
        Strength strength = wiFiSignal.getStrength();

        Security security = wiFiDetail.getSecurity();
        ImageView securityImage = view.findViewById(R.id.securityImage);
        securityImage.setImageResource(security.getImageResource());

        TextView textLevel = view.findViewById(R.id.level);
        textLevel.setText(String.format(Locale.ENGLISH, "%ddBm", wiFiSignal.getLevel()));
        textLevel.setTextColor(ContextCompat.getColor(context, strength.colorResource()));

        view.<TextView>findViewById(R.id.channel)
            .setText(wiFiSignal.getChannelDisplay());
        view.<TextView>findViewById(R.id.primaryFrequency)
            .setText(String.format(Locale.ENGLISH, "%d%s",
                wiFiSignal.getPrimaryFrequency(), WiFiSignal.FREQUENCY_UNITS));
        view.<TextView>findViewById(R.id.distance).setText(wiFiSignal.getDistance());

        if (isChild) {
            view.findViewById(R.id.tab).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.tab).setVisibility(View.GONE);
        }
    }

    private void setViewExtra(@NonNull View view, @NonNull WiFiDetail wiFiDetail) {
        Context context = view.getContext();

        WiFiSignal wiFiSignal = wiFiDetail.getWiFiSignal();
        Strength strength = wiFiSignal.getStrength();
        ImageView imageView = view.findViewById(R.id.levelImage);
        imageView.setImageResource(strength.imageResource());
        imageView.setColorFilter(ContextCompat.getColor(context, strength.colorResource()));

        view.<TextView>findViewById(R.id.channel_frequency_range)
            .setText(wiFiSignal.getFrequencyStart() + " - " + wiFiSignal.getFrequencyEnd());
        view.<TextView>findViewById(R.id.width)
            .setText("(" + wiFiSignal.getWiFiWidth().getFrequencyWidth() + WiFiSignal.FREQUENCY_UNITS + ")");
        view.<TextView>findViewById(R.id.capabilities)
            .setText(wiFiDetail.getCapabilities());
    }

    private void setViewVendorShort(@NonNull View view, @NonNull WiFiAdditional wiFiAdditional) {
        TextView textVendorShort = view.findViewById(R.id.vendorShort);
        String vendor = wiFiAdditional.getVendorName();
        if (StringUtils.isBlank(vendor)) {
            textVendorShort.setVisibility(View.GONE);
        } else {
            textVendorShort.setVisibility(View.VISIBLE);
            textVendorShort.setText(vendor.substring(0, Math.min(VENDOR_SHORT_MAX, vendor.length())));
        }
    }

    private void setViewVendorLong(@NonNull View view, @NonNull WiFiAdditional wiFiAdditional) {
        TextView textVendor = view.findViewById(R.id.vendorLong);
        String vendor = wiFiAdditional.getVendorName();
        if (StringUtils.isBlank(vendor)) {
            textVendor.setVisibility(View.GONE);
        } else {
            textVendor.setVisibility(View.VISIBLE);
            textVendor.setText(vendor.substring(0, Math.min(VENDOR_LONG_MAX, vendor.length())));
        }
    }

}
