package com.botmasterzzz.mobile.application.wifi.model;

import com.google.gson.annotations.SerializedName;

public class UserDeviceTest {

    @SerializedName("sent")
    private String sent;

    @SerializedName("rate")
    private String rate;

    @SerializedName("mac_address")
    private String macAddress;

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
