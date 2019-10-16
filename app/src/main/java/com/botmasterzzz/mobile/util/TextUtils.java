package com.botmasterzzz.mobile.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import androidx.annotation.NonNull;

import com.botmasterzzz.mobile.application.util.BuildUtils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TextUtils {
    private static final String SEPARATOR = " ";

    private TextUtils() {
        throw new IllegalStateException("Utility class");
    }

    @NonNull
    public static Set<String> split(String source) {
        return StringUtils.isBlank(source)
            ? new HashSet<>()
            : new HashSet<>(Arrays.asList(trim(source).split(SEPARATOR)));
    }

    @NonNull
    public static String join(Set<String> source) {
        return source == null
            ? StringUtils.EMPTY
            : trim(android.text.TextUtils.join(SEPARATOR, source.toArray()));
    }

    @NonNull
    public static String trim(String source) {
        return StringUtils.isBlank(source)
            ? StringUtils.EMPTY
            : source.trim().replaceAll(" +", " ");
    }

    @NonNull
    public static String textToHtml(@NonNull String text, int color, boolean small) {
        return "<font color='" + color + "'><" + (small ? "small" : "strong") +
            ">" + text + "</" + (small ? "small" : "strong") + "></font>";
    }

    @NonNull
    public static Spanned fromHtml(@NonNull String text) {
        return BuildUtils.isMinVersionN() ? fromHtmlAndroidN(text) : fromHtmlLegacy(text);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @NonNull
    private static Spanned fromHtmlAndroidN(@NonNull String text) {
        return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
    }

    @SuppressWarnings("deprecation")
    @NonNull
    private static Spanned fromHtmlLegacy(@NonNull String text) {
        return Html.fromHtml(text);
    }

}
