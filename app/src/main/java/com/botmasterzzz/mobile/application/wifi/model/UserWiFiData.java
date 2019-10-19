package com.botmasterzzz.mobile.application.wifi.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class UserWiFiData {

    @SerializedName("ssid")
    private String ssid;

    @SerializedName("bssid")
    private String bssid;

    @SerializedName("channel")
    private String channel;

    @SerializedName("rssi")
    private String rssi;

    @SerializedName("cc")
    private String cc;

    @SerializedName("security")
    private String security;

    @SerializedName("distance")
    private String distance;

    @SerializedName("center_frequency")
    private int centerFrequency;

    @SerializedName("primary_frequency")
    private int primaryFrequency;

    @SerializedName("end_frequency")
    private int endFrequency;

    @SerializedName("is80211mc")
    private boolean is80211mc;

    @SerializedName("created_time")
    private Timestamp createdTime;

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getCenterFrequency() {
        return centerFrequency;
    }

    public void setCenterFrequency(int centerFrequency) {
        this.centerFrequency = centerFrequency;
    }

    public int getPrimaryFrequency() {
        return primaryFrequency;
    }

    public void setPrimaryFrequency(int primaryFrequency) {
        this.primaryFrequency = primaryFrequency;
    }

    public int getEndFrequency() {
        return endFrequency;
    }

    public void setEndFrequency(int endFrequency) {
        this.endFrequency = endFrequency;
    }

    public boolean isIs80211mc() {
        return is80211mc;
    }

    public void setIs80211mc(boolean is80211mc) {
        this.is80211mc = is80211mc;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "UserWiFiData{" +
                "ssid='" + ssid + '\'' +
                ", bssid='" + bssid + '\'' +
                ", channel='" + channel + '\'' +
                ", rssi='" + rssi + '\'' +
                ", cc='" + cc + '\'' +
                ", security='" + security + '\'' +
                ", distance='" + distance + '\'' +
                ", centerFrequency=" + centerFrequency +
                ", primaryFrequency=" + primaryFrequency +
                ", endFrequency=" + endFrequency +
                ", is80211mc=" + is80211mc +
                ", createdTime=" + createdTime +
                '}';
    }
}
