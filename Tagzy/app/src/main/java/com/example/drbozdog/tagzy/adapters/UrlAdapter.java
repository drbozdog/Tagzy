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
    TextCrawler mTextCrawler;
    List<String> mUrls;

    public UrlAdapter(TextCrawler textCrawler, List<String> urls) {
        mTextCrawler = textCrawler;
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
        String mediaUrl = mUrls.get(position);
        Log.d(TAG, "onBindViewHolder: crawling:" + mediaUrl);
        mTextCrawler.makePreview(new LinkPreviewCallback() {
            @Override
            public void onPre() {

            }

            @Override
            public void onPos(SourceContent sourceContent, boolean b) {
                String description = sourceContent.getDescription();
                String title = sourceContent.getTitle();
                HashMap<String, String> metaTags = sourceContent.getMetaTags();
                List<String> images = sourceContent.getImages();
                String finalUrl = sourceContent.getFinalUrl();
                String canonicalUrl = sourceContent.getCannonicalUrl();
                String url = sourceContent.getUrl();

                Log.d(TAG, "onPos: " + sourceContent.getUrl() + ";title=" + title);

                holder.mTxtTitle.setText(title);
                holder.mTxtDescription.setText(description);
                if (images.size() > 0) {
                    Picasso.with(holder.mImgMedia.getContext()).load(images.get(0)).into(holder.mImgMedia);
                }

                holder.mContainerPreview.setOnClickListener(view -> {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    holder.mContainerPreview.getContext().startActivity(browserIntent);
                });

            }
        }, mediaUrl);
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
