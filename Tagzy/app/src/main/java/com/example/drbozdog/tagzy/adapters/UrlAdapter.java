package com.example.drbozdog.tagzy.adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drbozdog.tagzy.R;
import com.example.drbozdog.tagzy.entities.TwitterPostTagRecord;
import com.leocardz.link.preview.library.LinkPreviewCallback;
import com.leocardz.link.preview.library.SourceContent;
import com.leocardz.link.preview.library.TextCrawler;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by drbozdog on 06/02/18.
 */

public class UrlAdapter extends RecyclerView.Adapter<UrlAdapter.ViewHolder> {

    private static final String TAG = UrlAdapter.class.getSimpleName();
    List<TwitterPostTagRecord.Url> mUrls;

    public UrlAdapter(List<TwitterPostTagRecord.Url> urls) {
        mUrls = urls;
    }

    @Override
    public UrlAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.url_preview_item, parent, false);
        UrlAdapter.ViewHolder holder = new UrlAdapter.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(UrlAdapter.ViewHolder holder, int position) {
        TwitterPostTagRecord.Url currentUrl = mUrls.get(position);
        String description = currentUrl.getDescription();
        String title = currentUrl.getTitle();
        List<String> images = currentUrl.getImages();
        String url = currentUrl.getUrl();

        holder.mTxtTitle.setText(title);
        holder.mTxtDescription.setText(description);
        if (images != null && images.size() > 0) {
            Picasso.with(holder.mImgMedia.getContext()).load(images.get(0)).into(holder.mImgMedia);
        }

        holder.mContainerPreview.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            holder.mContainerPreview.getContext().startActivity(browserIntent);
        });

    }

    @Override
    public int getItemCount() {
        return mUrls.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_title)
        TextView mTxtTitle;

        @BindView(R.id.txt_description)
        TextView mTxtDescription;

        @BindView(R.id.img_image)
        ImageView mImgMedia;

        @BindView(R.id.container_preview)
        View mContainerPreview;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
