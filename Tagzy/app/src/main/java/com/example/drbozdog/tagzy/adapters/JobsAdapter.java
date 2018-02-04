package com.example.drbozdog.tagzy.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.drbozdog.tagzy.R;
import com.example.drbozdog.tagzy.entities.TagJob;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by drbozdog on 04/02/18.
 */

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder> {

    List<TagJob> mTagJobs = new ArrayList<>();
    OnTagJobSelectedListener mTagJobSelectedListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item, parent, false);
        JobsAdapter.ViewHolder holder = new JobsAdapter.ViewHolder(v);
        return holder;
    }

    public void setTagJobSelectedListener(OnTagJobSelectedListener tagJobSelectedListener) {
        mTagJobSelectedListener = tagJobSelectedListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TagJob job = mTagJobs.get(position);
        holder.mTxtName.setText(job.getName());
        holder.mView.setOnClickListener(view -> mTagJobSelectedListener.onJobSelected(job));
    }

    @Override
    public int getItemCount() {
        return mTagJobs.size();
    }

    public void update(List<TagJob> tagJobs) {
        mTagJobs.clear();
        mTagJobs.addAll(tagJobs);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_name)
        TextView mTxtName;

        @BindView(R.id.item)
        View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnTagJobSelectedListener {
        void onJobSelected(TagJob job);
    }
}
