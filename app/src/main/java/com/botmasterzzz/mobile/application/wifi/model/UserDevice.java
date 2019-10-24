package com.botmasterzzz.mobile.application.wifi.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserDevice {

    @SerializedName("model_name")
    private String modelName;

    @SerializedName("os_version")
    private String osVersion;

    @SerializedName("mac_address")
    private String macAddress;

    @SerializedName("ip_address")
    private String ipAddress;

    @SerializedName("link_speed")
    private int linkSpeed;

    @SerializedName("wifi_data")
    private List<UserWiFiData> userWiFiDataList;

    public void addUserWifiData(UserWiFiData userWiFiData) {
        if (null == userWiFiDataList){
            userWiFiDataList = new ArrayList<>();
        }
        userWiFiDataList.add(userWiFiData);
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public List<UserWiFiData> getUserWiFiDataList() {
        return userWiFiDataList;
    }

    public void setUserWiFiDataList(List<UserWiFiData> userWiFiDataList) {
        this.userWiFiDataList = userWiFiDataList;
    }

    public int getLinkSpeed() {
        return linkSpeed;
    }

    public void setLinkSpeed(int linkSpeed) {
        this.linkSpeed = linkSpeed;
    }
}
