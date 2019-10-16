package com.botmasterzzz.mobile.application.wifi.band;

import androidx.annotation.NonNull;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class WiFiChannel implements Comparable<WiFiChannel> {
    public static final WiFiChannel UNKNOWN = new WiFiChannel();

    private static final int ALLOWED_RANGE = WiFiChannels.FREQUENCY_SPREAD / 2;

    private final int channel;
    private final int frequency;

    private WiFiChannel() {
        channel = frequency = 0;
    }

    public WiFiChannel(int channel, int frequency) {
        this.channel = channel;
        this.frequency = frequency;
    }

    public int getChannel() {
        return channel;
    }

    public int getFrequency() {
        return frequency;
    }

    public boolean isInRange(int frequency) {
        return frequency >= this.frequency - ALLOWED_RANGE && frequency <= this.frequency + ALLOWED_RANGE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        WiFiChannel that = (WiFiChannel) o;

        return new EqualsBuilder()
            .append(getChannel(), that.getChannel())
            .append(getFrequency(), that.getFrequency())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(getChannel())
            .append(getFrequency())
            .toHashCode();
    }

    @Override
    public int compareTo(@NonNull WiFiChannel another) {
        return new CompareToBuilder()
            .append(getChannel(), another.getChannel())
            .append(getFrequency(), another.getFrequency())
            .toComparison();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
