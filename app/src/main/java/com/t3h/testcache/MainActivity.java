package com.t3h.testcache;

import android.os.Handler;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;

import com.t3h.testcache.adapter.CacheAdapter;
import com.t3h.testcache.model.AppInfo;

import com.t3h.testcache.pref.LoadData;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private CacheAdapter adapter;
    private RecyclerView lvCache;
    private SwipeRefreshLayout mSrlLayout;
    private LoadData loadData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        this.loadData = new LoadData(new LoadData.OnDataSuccessListener() {
            @Override
            public void onDataSuccess(List<AppInfo> appInfos) {
                adapter.setData(appInfos);
            }
        });
        this.loadData.load(this);
    }

    public void initView() {
        this.lvCache = findViewById(R.id.lv_cache);
        this.adapter = new CacheAdapter(MainActivity.this);
        this.lvCache.setAdapter(adapter);
        this.mSrlLayout = findViewById(R.id.swiperefresh);
        this.mSrlLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData.requestAPI(MainActivity.this);
                mSrlLayout.setRefreshing(false);
            }
        }, 1000);
    }
}


