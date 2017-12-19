package com.example.drbozdog.tagzy.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.drbozdog.tagzy.R;
import com.example.drbozdog.tagzy.TagzyApplication;
import com.example.drbozdog.tagzy.adapters.RecordsAdapter;
import com.example.drbozdog.tagzy.adapters.RecordsViewPagerAdapter;
import com.example.drbozdog.tagzy.entities.TagRecord;
import com.example.drbozdog.tagzy.fragments.TagRecordFragment;
import com.example.drbozdog.tagzy.viewmodels.TagRecordViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by drbozdog on 16/12/17.
 */

public class TagRecordActivity extends AppCompatActivity implements TagRecordFragment.OnStatusChangeListener {

    private static final String TAG = TagRecordActivity.class.getSimpleName();

    @Inject
    TagRecordViewModel mTagRecordViewModel;

    @BindView(R.id.pager)
    ViewPager mRecyclerView;

    DisposableObserver<List<TagRecord>> mDisposableSubscriber;
    RecordsViewPagerAdapter mRecordsAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagrecords);

        ButterKnife.bind(this);

        ((TagzyApplication) getApplicationContext()).getGraph().inject(this);

        mRecordsAdapter = new RecordsViewPagerAdapter(getSupportFragmentManager());
        mRecyclerView.setAdapter(mRecordsAdapter);

        if (mDisposableSubscriber == null || mDisposableSubscriber.isDisposed()) {
            mDisposableSubscriber = new DisposableObserver<List<TagRecord>>() {
                @Override
                public void onNext(List<TagRecord> tagRecords) {
                    mRecordsAdapter.update(tagRecords, mTagRecordViewModel.getJob());
                }

                @Override
                public void onError(Throwable t) {
                    Log.d(TAG, "onError: Loading records");
                }

                @Override
                public void onComplete() {
                    Log.d(TAG, "onComplete: Loading records");
                }
            };

            mTagRecordViewModel.getRecords().doFinally(() -> mDisposableSubscriber = null).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(mDisposableSubscriber);
        }
    }

    @Override
    protected void onDestroy() {
        if (mDisposableSubscriber != null && !mDisposableSubscriber.isDisposed()) {
            mDisposableSubscriber.dispose();
            mDisposableSubscriber = null;
        }
        super.onDestroy();
    }

    @Override
    public void OnTagSelected(TagRecord record) {
        mRecordsAdapter.updateItem(record, mRecyclerView.getCurrentItem());
        mRecyclerView.setCurrentItem(mRecyclerView.getCurrentItem() + 1);

        //TODO load other
        //TODO create stats objects

        mTagRecordViewModel.save(record).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                Log.d(TAG, "onNext: " + o);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: Update Error");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: Update completed");
            }
        });
    }
}
