package com.botmasterzzz.mobile.util;

import androidx.annotation.NonNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.PredicateUtils;
import org.apache.commons.collections4.Transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EnumUtils {

    private EnumUtils() {
        throw new IllegalStateException("Utility class");
    }

    @NonNull
    public static <T extends Enum> T find(@NonNull Class<T> enumType, int index, @NonNull T defaultValue) {
        T[] values = enumType.getEnumConstants();
        if (index < 0 || index >= values.length) {
            return defaultValue;
        }
        return values[index];
    }

    @NonNull
    public static <T extends Enum> T find(@NonNull Class<T> enumType, @NonNull Predicate<T> predicate, @NonNull T defaultValue) {
        List<T> results = new ArrayList<>(CollectionUtils.select(values(enumType), predicate));
        return results.isEmpty() ? defaultValue : results.get(0);
    }

    @NonNull
    public static <T extends Enum> Set<T> find(@NonNull Class<T> enumType, @NonNull Set<String> ordinals, @NonNull T defaultValue) {
        Set<T> results = new HashSet<>(CollectionUtils.collect(ordinals, new ToEnum<>(enumType, defaultValue)));
        return results.isEmpty() ? values(enumType) : results;
    }

    @NonNull
    public static <T extends Enum> Set<String> find(@NonNull Set<T> values) {
        return new HashSet<>(CollectionUtils.collect(values, new ToOrdinal<>()));
    }

    @NonNull
    public static <T extends Enum> Set<String> ordinals(@NonNull Class<T> enumType) {
        return new HashSet<>(CollectionUtils.collect(values(enumType), new ToOrdinal<>()));
    }

    @NonNull
    public static <T extends Enum> Set<T> values(@NonNull Class<T> enumType) {
        return new HashSet<>(Arrays.asList(enumType.getEnumConstants()));
    }

    @NonNull
    public static <T extends Enum, U> Predicate<U> predicate(@NonNull Class<T> enumType, @NonNull Collection<T> input, @NonNull Transformer<T, Predicate<U>> transformer) {
        if (input.size() >= values(enumType).size()) {
            return PredicateUtils.truePredicate();
        }
        return PredicateUtils.anyPredicate(CollectionUtils.collect(input, transformer));
    }

    private static class ToEnum<T extends Enum> implements Transformer<String, T> {
        private final T defaultValue;
        private final Class<T> enumType;

        private ToEnum(@NonNull Class<T> enumType, @NonNull T defaultValue) {
            this.enumType = enumType;
            this.defaultValue = defaultValue;
        }

        @Override
        public T transform(String input) {
            try {
                return EnumUtils.find(enumType, Integer.parseInt(input), defaultValue);
            } catch (Exception e) {
                return defaultValue;
            }
        }
    }

    private static class ToOrdinal<T extends Enum> implements Transformer<T, String> {
        @Override
        public String transform(T input) {
            return Integer.toString(input.ordinal());
        }
    }

}