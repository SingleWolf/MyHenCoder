package com.example.walker.myhencoder.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.walker.myhencoder.R;
import com.example.walker.myhencoder.model.SummaryBean;

import java.util.List;

/**
 * 导向适配器
 *
 * Created by Walker on 2017/7/10.
 */

public class GuideAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<SummaryBean> mData;

    public GuideAdapter(List<SummaryBean> data) {
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent,
                false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ItemViewHolder) {
            SummaryBean guide = mData.get(position);
            ((ItemViewHolder) holder).summary.setText(guide.getDesc());
        }
    }

    @Override
    public int getItemCount() {
        return mData.size() == 0 ? 0 : mData.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView summary;

        public ItemViewHolder(View view) {
            super(view);
            summary = (TextView) view.findViewById(R.id.tv_desc);
        }
    }
}
