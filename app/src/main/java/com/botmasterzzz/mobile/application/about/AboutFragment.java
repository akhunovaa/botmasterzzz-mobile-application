package com.botmasterzzz.mobile.application.about;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.botmasterzzz.mobile.application.BuildConfig;
import com.botmasterzzz.mobile.application.Configuration;
import com.botmasterzzz.mobile.application.MainContext;
import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.util.FileUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AboutFragment extends Fragment {
    private static final String YEAR_FORMAT = "yyyy";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_content, container, false);
        setCopyright(view);
        setExtraInformation(view);
        setOnClicks(view);
        return view;
    }

    private void setOnClicks(View view) {
        view.findViewById(R.id.writeReview).setOnClickListener(new WriteReviewClickListener(getActivity()));
    }

    private void setCopyright(View view) {
        Locale locale = Locale.getDefault();
        String year = new SimpleDateFormat(YEAR_FORMAT, locale).format(new Date());
        String message = getResources().getString(R.string.app_copyright);
        setText(view, R.id.about_copyright, message + year);
    }

    private void setExtraInformation(View view) {
        String text = BuildConfig.VERSION_NAME + " - " + BuildConfig.VERSION_CODE;
        Configuration configuration = MainContext.INSTANCE.getConfiguration();
        if (configuration != null) {
            if (configuration.isSizeAvailable()) {
                text += "S";
            }
            if (configuration.isLargeScreen()) {
                text += "L";
            }
        }
        text += " (" + Build.VERSION.RELEASE + "-" + Build.VERSION.SDK_INT + ")";
        setText(view, R.id.about_version_info, text);
        setText(view, R.id.about_package_name, BuildConfig.APPLICATION_ID);
    }

    private void setText(View view, int id, String text) {
        TextView version = view.findViewById(id);
        if (version != null) {
            version.setText(text);
        }
    }

    private static class WriteReviewClickListener implements OnClickListener {
        private final Activity activity;

        private WriteReviewClickListener(@NonNull Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onClick(View view) {
            String url = "market://details?id=" + BuildConfig.APPLICATION_ID;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            try {
                activity.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(view.getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private static class AlertDialogClickListener implements OnClickListener {
        private final Activity activity;
        private final int titleId;
        private final int resourceId;
        private final boolean isSmallFont;

        AlertDialogClickListener(@NonNull Activity activity, int titleId, int resourceId) {
            this(activity, titleId, resourceId, true);
        }

        AlertDialogClickListener(@NonNull Activity activity, int titleId, int resourceId, boolean isSmallFont) {
            this.activity = activity;
            this.titleId = titleId;
            this.resourceId = resourceId;
            this.isSmallFont = isSmallFont;
        }

        @Override
        public void onClick(View view) {
            if (!activity.isFinishing()) {
                String text = FileUtils.readFile(activity.getResources(), resourceId);
                AlertDialog alertDialog = new AlertDialog
                    .Builder(view.getContext())
                    .setTitle(titleId)
                    .setMessage(text)
                    .setNeutralButton(android.R.string.ok, new Close())
                    .create();
                alertDialog.show();
                if (isSmallFont) {
                    TextView textView = alertDialog.findViewById(android.R.id.message);
                    textView.setTextSize(8);
                }
            }
        }

        private static class Close implements DialogInterface.OnClickListener {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }
    }

}
