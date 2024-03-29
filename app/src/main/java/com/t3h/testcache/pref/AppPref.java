package com.t3h.testcache.pref;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.t3h.testcache.model.AppInfo;

import java.lang.reflect.Type;
import java.util.List;

public class AppPref {

    private SharedPreferences pref;
    private final Context context;
    private final String KEY_CACHE_DATA = "key_cache_data";
    private final String KEY_CACHE_TIME = "key_cache_time";

    public AppPref(Context context, String cacheName) {
        this.context = context;
        this.pref = context.getSharedPreferences(cacheName, Context.MODE_PRIVATE);
    }

    public List<AppInfo> getDataFromCache() {
        Type type = new TypeToken<List<AppInfo>>() {
        }.getType();
        return new Gson().fromJson(pref.getString(KEY_CACHE_DATA, ""), type);
    }

    public void setCacheData(String data) {
        this.pref.edit().putString(KEY_CACHE_DATA, data).apply();
    }

    public void setLastTimeCache(long cacheTime) {
        this.pref.edit().putLong(KEY_CACHE_TIME, cacheTime).apply();
    }

    private long getLastTimeCache() {
        return pref.getLong(KEY_CACHE_TIME, 0);
    }

    public boolean inValidCache() {
        return System.currentTimeMillis() - getLastTimeCache() > 5*60 * 1000;
    }


}
