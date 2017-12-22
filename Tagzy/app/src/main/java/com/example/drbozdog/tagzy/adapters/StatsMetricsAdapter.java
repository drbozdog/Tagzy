package com.example.drbozdog.tagzy.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.drbozdog.tagzy.R;
import com.example.drbozdog.tagzy.entities.StatsMetric;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by drbozdog on 22/12/2017.
 */

public class StatsMetricsAdapter extends RecyclerView.Adapter<StatsMetricsAdapter.StatsMetricsViewHolder> {

    List<StatsMetric> mStatsMetrics = new ArrayList<>();


    @Override
    public StatsMetricsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.statsmetric_item, parent, false);
        StatsMetricsViewHolder holder = new StatsMetricsViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(StatsMetricsViewHolder holder, int position) {
        StatsMetric metric = mStatsMetrics.get(position);
        holder.mTxtName.setText(metric.getName());
        holder.mTxtValue.setText(String.valueOf(metric.getCount()));
    }

    @Override
    public int getItemCount() {
        return mStatsMetrics.size();
    }

    public void update(List<StatsMetric> statsMetricsList) {
        mStatsMetrics.clear();
        mStatsMetrics.addAll(statsMetricsList);
        notifyDataSetChanged();
    }

    public static class StatsMetricsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_name)
        TextView mTxtName;
        @BindView(R.id.txt_value)
        TextView mTxtValue;

        public StatsMetricsViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
