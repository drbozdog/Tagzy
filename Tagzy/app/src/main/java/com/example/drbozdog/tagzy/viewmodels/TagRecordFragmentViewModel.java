package com.example.drbozdog.tagzy.viewmodels;

import com.example.drbozdog.tagzy.entities.TagJob;
import com.example.drbozdog.tagzy.entities.TagRecord;

import javax.inject.Inject;

/**
 * Created by drbozdog on 19/12/2017.
 */

public class TagRecordFragmentViewModel {

    private TagRecord mTagRecord;
    private TagJob mTagJob;

    private int mSelectedTagIndex = -1;
    private String mSelectedTag;

    private int columns = 3;
    private int rows = 2;

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    @Inject
    public TagRecordFragmentViewModel() {
    }

    public void init(TagRecord tagRecord, TagJob tagJob) {
        mTagRecord = tagRecord;
        mTagJob = tagJob;
        String tag = mTagRecord.getTag(mTagJob.getName());
        if (tag == null) {
            mSelectedTagIndex = -1;
        } else {
            for (int i = 0; i < mTagJob.getTags().size(); i++) {
                if (tag.toLowerCase().equals(mTagJob.getTags().get(i).toLowerCase())) {
                    mSelectedTagIndex = i;
                    break;
                }
            }
        }
    }

    public TagRecord getTagRecord() {
        return mTagRecord;
    }

    public TagJob getTagJob() {
        return mTagJob;
    }

    public void setSelectedTagIndex(int selectedTagIndex) {
        mSelectedTagIndex = selectedTagIndex;
        mTagRecord.setTag(mTagJob.getTags().get(selectedTagIndex), mTagJob.getName());
    }

    public int getSelectedTagIndex() {
        return mSelectedTagIndex;
    }
}
