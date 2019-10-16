package com.botmasterzzz.mobile.application.wifi.graphutils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class GraphColor {
    public static final GraphColor TRANSPARENT = new GraphColor(0x009E9E9E, 0x009E9E9E);

    private final long primary;
    private final long background;

    GraphColor(long primary, long background) {
        this.primary = primary;
        this.background = background;
    }

    public long getPrimary() {
        return primary;
    }

    public long getBackground() {
        return background;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        GraphColor that = (GraphColor) o;

        return new EqualsBuilder()
            .append(getPrimary(), that.getPrimary())
            .append(getBackground(), that.getBackground())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(getPrimary())
            .append(getBackground())
            .toHashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
