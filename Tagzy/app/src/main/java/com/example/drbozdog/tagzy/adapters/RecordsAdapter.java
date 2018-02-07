package com.example.drbozdog.tagzy.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drbozdog.tagzy.R;
import com.example.drbozdog.tagzy.entities.TagRecord;
import com.example.drbozdog.tagzy.entities.TwitterUserTagRecord;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by drbozdog on 17/12/17.
 */

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.ViewHolder> {

    private static final String TAG = RecordsAdapter.class.getSimpleName();
    List<TagRecord> mTagRecords = new ArrayList<>();

    public RecordsAdapter() {
    }

    @Override
    public RecordsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.twitter_user_item, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecordsAdapter.ViewHolder holder, int position) {
        TwitterUserTagRecord current = (TwitterUserTagRecord) mTagRecords.get(position);
        holder.mTxtName.setText(current.getName());
        holder.mTxtDescription.setText(current.getDescription());
        holder.mTxtUrl.setText(current.getUrl());
        if (current.getProfile_background_image_url() != null && current.getProfile_background_image_url() != "") {
            Picasso.with(holder.mImgProfileBackgroundImageUrl.getContext()).load(current.getProfile_background_image_url()).into(holder.mImgProfileBackgroundImageUrl);
        }
        if (current.getProfile_banner_url() != null && current.getProfile_banner_url() != "") {
            Picasso.with(holder.mImgProfileBannerUrl.getContext()).load(current.getProfile_banner_url()).into(holder.mImgProfileBannerUrl);
        }
        if (current.getProfile_image_url() != null && current.getProfile_image_url() != "") {
            Picasso.with(holder.mImgProfileImage.getContext()).load(current.getProfile_image_url()).into(holder.mImgProfileImage);
        }
    }

    @Override
    public int getItemCount() {
        return mTagRecords.size();
    }

    public void update(List<TagRecord> tagRecords) {
        mTagRecords.clear();
        mTagRecords.addAll(tagRecords);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_name)
        TextView mTxtName;
        @BindView(R.id.txt_description)
        TextView mTxtDescription;
        @BindView(R.id.txt_url)
        TextView mTxtUrl;
        @BindView(R.id.img_profile_background_image_url)
        ImageView mImgProfileBackgroundImageUrl;
        @BindView(R.id.img_profile_banner_url)
        ImageView mImgProfileBannerUrl;
        @BindView(R.id.img_profile_image_url)
        ImageView mImgProfileImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
