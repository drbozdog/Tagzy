package com.example.drbozdog.tagzy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.drbozdog.tagzy.R;
import com.example.drbozdog.tagzy.TagzyApplication;
import com.example.drbozdog.tagzy.adapters.JobsAdapter;
import com.example.drbozdog.tagzy.adapters.StatsMetricsAdapter;
import com.example.drbozdog.tagzy.entities.StatsMetric;
import com.example.drbozdog.tagzy.entities.TagJob;
import com.example.drbozdog.tagzy.viewmodels.TagJobsViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by drbozdog on 16/12/17.
 */

public class TagJobsActivity extends AppCompatActivity {

    private static final String TAG = TagJobsActivity.class.getSimpleName();

    @Inject
    TagJobsViewModel mTagJobsViewModel;

    DisposableObserver<List<TagJob>> mJobsDisposableObserver;
    JobsAdapter mJobsAdapter;

    @BindView(R.id.list_jobs)
    RecyclerView mRecyclerViewJobs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);

        TagzyApplication.getGraph().inject(this);

        ButterKnife.bind(this);

        mJobsAdapter = new JobsAdapter();
        mJobsAdapter.setTagJobSelectedListener(new JobsAdapter.OnTagJobSelectedListener() {
            @Override
            public void onJobSelected(TagJob job) {
                Intent i = new Intent(TagJobsActivity.this, TagRecordActivity.class);
                i.putExtra(TagRecordActivity.EXTRA_TAGJOB, job);
                startActivity(i);
            }
        });
        mRecyclerViewJobs.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewJobs.setAdapter(mJobsAdapter);


        if (mJobsDisposableObserver == null) {
            mJobsDisposableObserver = new DisposableObserver<List<TagJob>>() {
                @Override
                public void onNext(List<TagJob> tagJobs) {
                    mJobsAdapter.update(tagJobs);
                }

                @Override
                public void onError(Throwable e) {
                    Log.e(TAG, "onError: ", e);
                }

                @Override
                public void onComplete() {
                    Log.d(TAG, "onComplete: ");
                }
            };

            mTagJobsViewModel.getJobs()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mJobsDisposableObserver);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mJobsDisposableObserver != null && !mJobsDisposableObserver.isDisposed()) {
            mJobsDisposableObserver.dispose();
            mJobsDisposableObserver = null;
        }
    }
}
