package com.example.drbozdog.tagzy.viewmodels;

import com.example.drbozdog.tagzy.entities.StatsMetric;
import com.example.drbozdog.tagzy.managers.TagRecordManager;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by drbozdog on 22/12/2017.
 */

public class StatsViewModel {

    private final TagRecordManager mTagRecordManager;


    @Inject
    public StatsViewModel(TagRecordManager tagRecordManager) {
        mTagRecordManager = tagRecordManager;
    }

    public void init() {

    }

    public Observable<List<StatsMetric>> getStats() {
        return mTagRecordManager.getStats();
    }
}
