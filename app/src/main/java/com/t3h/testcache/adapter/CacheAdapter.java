package com.t3h.testcache.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.t3h.testcache.R;
import com.t3h.testcache.model.AppInfo;

import java.util.List;

public class CacheAdapter extends RecyclerView.Adapter<CacheAdapter.CacheHolder> {
    private LayoutInflater inflater;
    private List<AppInfo> data;


    public CacheAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<AppInfo> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CacheHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        View v = inflater.inflate(R.layout.item_cache, viewGroup, false);
        CacheHolder holder = new CacheHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CacheHolder CacheHolder, final int position) {
        AppInfo f = data.get(position);
        CacheHolder.bindData(f);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class CacheHolder extends RecyclerView.ViewHolder {
        private ImageView imCache;
        private TextView tvNameCache;
        private TextView tvNameDeveloper;

        public CacheHolder(@NonNull View itemView) {
            super(itemView);
            imCache = itemView.findViewById(R.id.im_icon);
            tvNameCache = itemView.findViewById(R.id.tv_app_name);
            tvNameDeveloper = itemView.findViewById(R.id.tv_developer_name);

        }

        public void bindData(AppInfo cache) {
            Glide.with(imCache)
                    .load(cache.getIconUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imCache);
            tvNameCache.setText(cache.getAppName());
            tvNameDeveloper.setText(cache.getDeveloperName());
        }
    }


}
