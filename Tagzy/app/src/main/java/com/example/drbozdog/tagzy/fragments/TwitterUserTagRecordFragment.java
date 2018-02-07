package com.example.drbozdog.tagzy.fragments;

import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drbozdog.tagzy.R;
import com.example.drbozdog.tagzy.TagzyApplication;
import com.example.drbozdog.tagzy.entities.TagRecord;
import com.example.drbozdog.tagzy.entities.TwitterUserTagRecord;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by drbozdog on 04/02/18.
 */

public class TwitterUserTagRecordFragment extends Fragment {

    public static final String EXTRA_RECORD = "extra_record";

    @BindView(R.id.txt_name)
    TextView mTxtName;
    @BindView(R.id.txt_description)
    TextView mTxtDescription;
    @BindView(R.id.txt_url)
    Button mBtnUrl;
    @BindView(R.id.img_profile_background_image_url)
    ImageView mImgProfileBackgroundImageUrl;
    @BindView(R.id.img_profile_banner_url)
    ImageView mImgProfileBannerUrl;
    @BindView(R.id.img_profile_image_url)
    ImageView mImgProfileImage;
    private TagRecord mTagRecord;

    public static TwitterUserTagRecordFragment NewInstance(TagRecord tagRecord) {
        TwitterUserTagRecordFragment tagRecordFragment = new TwitterUserTagRecordFragment();
        Bundle b = new Bundle();
        b.putSerializable(EXTRA_RECORD, tagRecord);
        tagRecordFragment.setArguments(b);
        return tagRecordFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTagRecord = (TagRecord) getArguments().getSerializable(EXTRA_RECORD);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.twitter_user_item, container, false);
        ButterKnife.bind(this, v);

        updateUI(mTagRecord);

        return v;
    }


    private void updateUI(TagRecord currentTagRecord) {
        TwitterUserTagRecord current = (TwitterUserTagRecord) currentTagRecord;
        mTxtName.setText(current.getName() + "(" + current.getScreen_name() + ")");
        mTxtDescription.setText(current.getDescription());

        if (current.getProfile_background_image_url() != null && current.getProfile_background_image_url() != "") {
            Picasso.with(mImgProfileBackgroundImageUrl.getContext()).load(current.getProfile_background_image_url()).into(mImgProfileBackgroundImageUrl);
        }
        if (current.getProfile_banner_url() != null && current.getProfile_banner_url() != "") {
            Picasso.with(mImgProfileBannerUrl.getContext()).load(current.getProfile_banner_url()).into(mImgProfileBannerUrl);
        }
        if (current.getProfile_image_url() != null && current.getProfile_image_url() != "") {
            String profile_image_url = current.getProfile_image_url().replace("_normal", "_bigger");
            Picasso.with(mImgProfileImage.getContext()).load(profile_image_url).into(mImgProfileImage);
        }

        if (current.getUrl() != null && current.getUrl() != "") {
            mBtnUrl.setText(current.getUrl());
            mBtnUrl.setOnClickListener(view -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(current.getUrl()));
                startActivity(browserIntent);
            });
        } else {
            mBtnUrl.setVisibility(View.INVISIBLE);
        }

    }
}
