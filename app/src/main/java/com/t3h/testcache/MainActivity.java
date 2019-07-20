package com.t3h.testcache;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
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

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private String TAG = "TEST_CACHE_DATA";
    private String TESTCACHE = "testcache";
    private AppPref appPref;
    private CacheAdapter adapter;
    private RecyclerView lvCache;
    private List<AppInfo> appInfos;
    private SwipeRefreshLayout mSrlLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidNetworking.initialize(getApplicationContext());
        mSrlLayout = findViewById(R.id.swiperefresh);
        mSrlLayout.setOnRefreshListener(this);
        appPref = new AppPref(this, TESTCACHE);
        if (appPref.inValidCache() || TextUtils.isEmpty(appPref.getCacheData())) {
            requestAPI();

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
                        showData(response);
                        initView();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(MainActivity.this, "Request error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showData(String data) {
        Type type = new TypeToken<List<AppInfo>>() {
        }.getType();
        appInfos = new Gson().fromJson(data, type);

        Log.i(TAG, "showData: " + appInfos.toString());
    }

    private void showData() {
        appInfos = appPref.getCacheData1();
        Log.i(TAG, "showData: array" + appInfos.get(1).getAppId());
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                requestAPI();
                mSrlLayout.setRefreshing(false);
            }
        }, 1000);
    }
}


