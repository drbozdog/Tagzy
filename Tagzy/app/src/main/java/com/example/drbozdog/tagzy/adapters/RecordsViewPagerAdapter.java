package com.example.drbozdog.tagzy.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.drbozdog.tagzy.entities.TagJob;
import com.example.drbozdog.tagzy.entities.TagRecord;
import com.example.drbozdog.tagzy.fragments.TagRecordFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drbozdog on 19/12/2017.
 */

public class RecordsViewPagerAdapter extends FragmentStatePagerAdapter {

    List<TagRecord> mTagRecordList = new ArrayList<>();
    TagJob mTagJob;

    public RecordsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void update(TagRecord tagRecord, TagJob tagJob) {
        mTagRecordList.add(tagRecord);
        mTagJob = tagJob;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return TagRecordFragment.NewInstance(mTagRecordList.get(position), mTagJob);
    }

    @Override
    public int getCount() {
        return mTagRecordList.size();
    }

    public void updateItem(TagRecord record, int currentItem) {
        mTagRecordList.set(currentItem, record);
    }
}
