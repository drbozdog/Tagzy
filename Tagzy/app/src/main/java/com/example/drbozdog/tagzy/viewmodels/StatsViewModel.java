package com.example.drbozdog.tagzy.viewmodels;

import com.example.drbozdog.tagzy.entities.StatsMetric;
import com.example.drbozdog.tagzy.entities.TagJob;
import com.example.drbozdog.tagzy.managers.TagRecordManager;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by drbozdog on 22/12/2017.
 */

public class StatsViewModel {

    private final TagRecordManager mTagRecordManager;

    TagJob mJob;


    @Inject
    public StatsViewModel(TagRecordManager tagRecordManager) {
        mTagRecordManager = tagRecordManager;
    }

    public void init(TagJob job) {
        mJob = job;
    }

    public Observable<List<StatsMetric>> getStats() {
        return mTagRecordManager.getStats(mJob.getId());
    }
}
