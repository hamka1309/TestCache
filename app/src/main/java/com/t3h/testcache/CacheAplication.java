package com.t3h.testcache;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

public class CacheAplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
    }
}
