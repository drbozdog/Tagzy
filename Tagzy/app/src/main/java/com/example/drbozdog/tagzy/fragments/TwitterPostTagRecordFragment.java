package com.example.drbozdog.tagzy.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.drbozdog.tagzy.R;
import com.example.drbozdog.tagzy.entities.TagJob;
import com.example.drbozdog.tagzy.entities.TagRecord;
import com.example.drbozdog.tagzy.entities.TwitterPostTagRecord;
import com.example.drbozdog.tagzy.entities.TwitterUserTagRecord;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by drbozdog on 04/02/18.
 */

public class TwitterPostTagRecordFragment extends Fragment {

    public static final String EXTRA_RECORD = "extra_record";
    private TagRecord mTagRecord;

    public static TwitterPostTagRecordFragment NewInstance(TagRecord tagRecord) {
        TwitterPostTagRecordFragment tagRecordFragment = new TwitterPostTagRecordFragment();
        Bundle b = new Bundle();
        b.putSerializable(EXTRA_RECORD, tagRecord);
        tagRecordFragment.setArguments(b);
        return tagRecordFragment;
    }

    @BindView(R.id.txt_user)
    TextView mTxtUserName;
    @BindView(R.id.txt_posttext)
    TextView mTxtPostText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTagRecord = (TagRecord) getArguments().getSerializable(EXTRA_RECORD);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.twitter_post_item, container, false);

        ButterKnife.bind(this, v);

        updateUI(mTagRecord);

        return v;
    }

    private void updateUI(TagRecord currentTagRecord) {
        TwitterPostTagRecord current = (TwitterPostTagRecord) currentTagRecord;
        mTxtUserName.setText(current.getUserName());
        mTxtPostText.setText(current.getText());
    }
}
