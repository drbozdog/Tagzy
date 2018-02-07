package com.example.drbozdog.tagzy.viewmodels;

import com.example.drbozdog.tagzy.entities.TagJob;
import com.example.drbozdog.tagzy.managers.TagJobsManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by drbozdog on 16/12/17.
 */

public class TagJobsViewModel {

    TagJobsManager mTagJobsManager;

    List<TagJob> mTagJobs;

    @Inject
    public TagJobsViewModel(TagJobsManager tagJobsManager) {
        mTagJobsManager = tagJobsManager;
    }

    public Observable<List<TagJob>> getJobs(){
        return mTagJobsManager.getJobs().doOnNext(tagJobs -> mTagJobs = tagJobs);
    }

    public List<TagJob> getTagJobs() {
        return mTagJobs;
    }
}
