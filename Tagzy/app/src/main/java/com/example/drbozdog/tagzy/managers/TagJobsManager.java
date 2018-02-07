package com.example.drbozdog.tagzy.managers;

import com.example.drbozdog.tagzy.entities.TagJob;
import com.example.drbozdog.tagzy.repository.JobsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by drbozdog on 16/12/17.
 */

public class TagJobsManager {

    JobsRepository mJobsRepository;

    @Inject
    public TagJobsManager(JobsRepository jobsRepository) {
        mJobsRepository = jobsRepository;
    }

    public Observable<List<TagJob>> getJobs() {
        return mJobsRepository.getJobs();
    }


}
