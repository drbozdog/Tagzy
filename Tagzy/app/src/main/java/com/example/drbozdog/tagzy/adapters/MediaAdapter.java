package com.example.drbozdog.tagzy.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drbozdog.tagzy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by drbozdog on 05/02/18.
 */

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder> {

    private static final String TAG = MediaAdapter.class.getSimpleName();
    private final List<String> mMediaUrls;

    public MediaAdapter(List<String> mediaUrls) {
        mMediaUrls = mediaUrls;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_item, parent, false);
        MediaAdapter.ViewHolder holder = new MediaAdapter.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String mediaUrl = mMediaUrls.get(position);
        Log.d(TAG, "onBindViewHolder: loading media:" + mediaUrl);
        Picasso.with(holder.mImgMedia.getContext()).load(mediaUrl).into(holder.mImgMedia);
    }

    @Override
    public int getItemCount() {
        return mMediaUrls.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_media)
        ImageView mImgMedia;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
