package com.botmasterzzz.mobile.util;

import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.annotation.RawRes;

import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;

public class FileUtils {
    private FileUtils() {
        throw new IllegalStateException("Utility class");
    }

    @NonNull
    public static String readFile(@NonNull Resources resources, @RawRes int id) {
        try (InputStream inputStream = resources.openRawResource(id)) {
            int size = inputStream.available();
            byte[] bytes = new byte[size];
            int count = inputStream.read(bytes);
            if (count != size) {
                return StringUtils.EMPTY;
            }
            return new String(bytes);
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }
}
