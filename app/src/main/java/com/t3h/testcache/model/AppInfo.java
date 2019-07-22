package com.t3h.testcache.model;

import com.google.gson.annotations.SerializedName;

public class AppInfo {

    @SerializedName("app_id")
    private String appId;

    @SerializedName("app_name")
    private String appName;

    @SerializedName("icon_url")
    private String iconUrl;

    @SerializedName("developer_name")
    private String developerName;

    public String getAppId() {
        return appId;
    }

    public String getAppName() {
        return appName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getDeveloperName() {
        return developerName;
    }

}
