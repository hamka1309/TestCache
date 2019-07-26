package com.t3h.testcache.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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

        private CacheHolder(@NonNull View itemView) {
            super(itemView);
            this.imCache = itemView.findViewById(R.id.im_icon);
            this.tvNameCache = itemView.findViewById(R.id.tv_app_name);
            this.tvNameDeveloper = itemView.findViewById(R.id.tv_developer_name);

        }

        private void bindData(AppInfo cache) {
            Glide.with(imCache)
                    .load(cache.getIconUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imCache);
            this.tvNameCache.setText(cache.getAppName());
            this.tvNameDeveloper.setText(cache.getDeveloperName());
        }
    }


}
