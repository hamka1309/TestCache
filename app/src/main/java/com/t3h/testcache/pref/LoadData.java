package com.t3h.testcache.pref;

import android.content.Context;

import android.text.TextUtils;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.t3h.testcache.model.AppInfo;

import java.lang.reflect.Type;

import java.util.List;

public class LoadData {
    private String TAG = "TEST_CACHE_DATA";
    private String TESTCACHE = "testcache";
    private AppPref appPref;
    private List<AppInfo> appInfos;

    private OnDataSuccessListener onDataSuccessListener;

    public LoadData(OnDataSuccessListener onDataSuccessListener) {
        this.onDataSuccessListener = onDataSuccessListener;

    }

    public void load(Context context) {

        this.appPref = new AppPref(context, TESTCACHE);
        if (this.appPref.inValidCache() || TextUtils.isEmpty(this.appPref.getCacheData())) {
            requestAPI();
            return;
        }
        this.appInfos = this.appPref.getCacheData1();
        if (onDataSuccessListener != null) {
            onDataSuccessListener.onDataSuccess(this.appInfos);
            Log.i(TAG, "get data from cache");
        }
    }

    public void requestAPI() {
        Log.i(TAG, "get data from API");
        AndroidNetworking.get("https://pastebin.com/raw/qWGmGVmG")
                .setTag(this)
                .setPriority(Priority.LOW)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        appPref.setCacheData(response);
                        appPref.setLastTimeCache(System.currentTimeMillis());
                        Type type = new TypeToken<List<AppInfo>>() {
                        }.getType();
                        appInfos = new Gson().fromJson(response, type);
                        if (onDataSuccessListener != null) {
                            onDataSuccessListener.onDataSuccess(appInfos);
                        }
                        Log.i(TAG, "request API: " + appInfos.toString());
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "onError: ");
                    }
                });
    }

    public interface OnDataSuccessListener {
        void onDataSuccess(List<AppInfo> appInfos);
    }
}
