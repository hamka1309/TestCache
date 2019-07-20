package com.t3h.testcache;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;

import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.t3h.testcache.adapter.CacheAdapter;
import com.t3h.testcache.model.AppInfo;
import com.t3h.testcache.pref.AppPref;

import java.lang.reflect.Type;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = "TEST_CACHE_DATA";
    private String TESTCACHE = "testcache";
    private AppPref appPref;
    private CacheAdapter adapter;
    private RecyclerView lvCache;
    List<AppInfo> appInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidNetworking.initialize(getApplicationContext());
        appPref = new AppPref(this, TESTCACHE);
        if (appPref.inValidCache() || TextUtils.isEmpty(appPref.getCacheData())) {
            requestAPI();
            Log.i(TAG, "get data from API");
        } else {
            showData();
            Log.i(TAG, "get data from cache");
        }
        initView();
    }

    private void initView() {
        lvCache = findViewById(R.id.lv_cache);
        adapter = new CacheAdapter(MainActivity.this);
        lvCache.setAdapter(adapter);
        adapter.setData(appInfos);
    }

    private void requestAPI() {
        AndroidNetworking.get("https://pastebin.com/raw/smFRyHLd")
                .setTag(this)
                .setPriority(Priority.LOW)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        appPref.setCacheData(response);
                        appPref.setLastTimeCache(System.currentTimeMillis());
                        showData(response);
                        initView();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(MainActivity.this, "Request error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void showData(String data) {
        Type type = new TypeToken<List<AppInfo>>() {
        }.getType();
        appInfos = new Gson().fromJson(data, type);

        Log.i(TAG, "showData: " + appInfos.toString());
    }

    public void showData() {
        appInfos = appPref.getCacheData1();

        Log.i(TAG, "showData: array" + appInfos.get(1).getAppId());
    }


}


