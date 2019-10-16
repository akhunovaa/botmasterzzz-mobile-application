package com.botmasterzzz.mobile.application.settings;

import androidx.annotation.NonNull;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

class Data implements Comparable<Data> {
    private final String code;
    private final String name;

    Data(@NonNull String code, @NonNull String name) {
        this.code = code;
        this.name = name;
    }

    @NonNull
    String getCode() {
        return code;
    }

    @NonNull
    String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Data that = (Data) o;

        return new EqualsBuilder()
            .append(getName(), that.getName())
            .append(getCode(), that.getCode())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(getName())
            .append(getCode())
            .toHashCode();
    }

    @Override
    public int compareTo(@NonNull Data another) {
        return new CompareToBuilder()
            .append(getName(), another.getName())
            .append(getCode(), another.getCode())
            .toComparison();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
