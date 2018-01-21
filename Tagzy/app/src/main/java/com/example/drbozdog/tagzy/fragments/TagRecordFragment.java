package com.example.drbozdog.tagzy.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.drbozdog.tagzy.R;
import com.example.drbozdog.tagzy.TagzyApplication;
import com.example.drbozdog.tagzy.entities.TagJob;
import com.example.drbozdog.tagzy.entities.TagRecord;
import com.example.drbozdog.tagzy.viewmodels.TagRecordFragmentViewModel;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by drbozdog on 19/12/2017.
 */

public class TagRecordFragment extends android.support.v4.app.Fragment {

    private static final String TAG = TagRecordFragment.class.getSimpleName();
    public static String EXTRA_RECORD = "extra_tag_record";
    public static String EXTRA_JOB = "extra_job_record";

    public static TagRecordFragment NewInstance(TagRecord tagRecord, TagJob tagJob) {
        TagRecordFragment tagRecordFragment = new TagRecordFragment();
        Bundle b = new Bundle();
        b.putSerializable(EXTRA_RECORD, tagRecord);
        b.putSerializable(EXTRA_JOB, tagJob);
        tagRecordFragment.setArguments(b);
        return tagRecordFragment;
    }

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
    @BindView(R.id.tags_container)
    LinearLayout mTagsContainer;

    @Inject
    TagRecordFragmentViewModel mViewModel;
    OnStatusChangeListener mStatusChangeListener;


    public TagRecordFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TagzyApplication.getGraph().inject(this);

        TagRecord tagRecord = (TagRecord) getArguments().getSerializable(EXTRA_RECORD);
        TagJob tagJob = (TagJob) getArguments().getSerializable(EXTRA_JOB);
        mViewModel.init(tagRecord, tagJob);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.record_item, container, false);
        ButterKnife.bind(this, v);

        updateUI(mViewModel.getTagRecord(), mViewModel.getTagJob());

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mStatusChangeListener = (OnStatusChangeListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mStatusChangeListener = null;
    }

    private void updateUI(TagRecord current, TagJob job) {
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

        for (int r = 0; r < mViewModel.getRows(); r++) {
            LinearLayout layout = new LinearLayout(getContext());

            mTagsContainer.addView(layout);
            for (int c = 0; c < mViewModel.getColumns(); c++) {
                int i = r * mViewModel.getColumns() + c;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                layout.setLayoutParams(params);
                Button btnTag = (Button) getActivity().getLayoutInflater().inflate(R.layout.tag_button, null);
                String predictedValue = String.valueOf(mViewModel.getTagRecord().getPrediction(job.getName(), job.getTags().get(i)));
                btnTag.setText(job.getTags().get(i) + ":" + predictedValue);
                btnTag.setOnClickListener(view -> {
                    mViewModel.setSelectedTagIndex(i);
                    updateSelectedTag();
                    if (mStatusChangeListener != null) {
                        mStatusChangeListener.OnTagSelected(mViewModel.getTagRecord());
                    }

                });
                btnTag.setLayoutParams(params);
                layout.addView(btnTag);
            }

            updateSelectedTag();
        }
    }

    private void updateSelectedTag() {
        for (int r = 0; r < mTagsContainer.getChildCount(); r++) {
            LinearLayout layout = (LinearLayout) mTagsContainer.getChildAt(r);
            for (int c = 0; c < layout.getChildCount(); c++) {
                int i = r * mViewModel.getColumns() + c;
                boolean selected = false;
                if (mViewModel.getSelectedTagIndex() == i) {
                    selected = true;
                }
                layout.getChildAt(c).setSelected(selected);
            }
        }
    }

    public interface OnStatusChangeListener {
        void OnTagSelected(TagRecord record);
    }
}
