package com.example.drbozdog.tagzy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.drbozdog.tagzy.R;
import com.example.drbozdog.tagzy.TagzyApplication;
import com.example.drbozdog.tagzy.adapters.RecordsViewPagerAdapter;
import com.example.drbozdog.tagzy.entities.TagJob;
import com.example.drbozdog.tagzy.entities.TagRecord;
import com.example.drbozdog.tagzy.fragments.TagRecordFragment;
import com.example.drbozdog.tagzy.viewmodels.TagRecordViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by drbozdog on 16/12/17.
 */

public class TagRecordActivity extends AppCompatActivity implements TagRecordFragment.OnStatusChangeListener {

    private static final String TAG = TagRecordActivity.class.getSimpleName();
    public static final String EXTRA_TAGJOB = "extra_job";

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

        TagJob job = (TagJob) getIntent().getSerializableExtra(EXTRA_TAGJOB);

        mTagRecordViewModel.init(job);

        mRecordsAdapter = new RecordsViewPagerAdapter(getSupportFragmentManager());
        mRecyclerView.setAdapter(mRecordsAdapter);

        loadRecords();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tags_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_status:
                openStatus();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openStatus() {
        Intent i = new Intent(this, StatsActivity.class);
        i.putExtra(StatsActivity.EXTRA_SUCCESSFUL, mTagRecordViewModel.getSuccessfulSaves().size());
        i.putExtra(StatsActivity.EXTRA_ERRORS, mTagRecordViewModel.getFailedSaves().size());
        i.putExtra(StatsActivity.EXTRA_JOB, mTagRecordViewModel.getJob());
        startActivity(i);
    }

    private void loadRecords() {
        if (mDisposableSubscriber == null || mDisposableSubscriber.isDisposed()) {
            mDisposableSubscriber = new DisposableObserver<List<TagRecord>>() {
                @Override
                public void onNext(List<TagRecord> tagRecords) {
                    mRecordsAdapter.update(tagRecords, mTagRecordViewModel.getJob());
                }

                @Override
                public void onError(Throwable t) {
                    Log.e(TAG, "onError: Loading records", t);
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
    public void OnTagSelected(TagRecord record) {
        mRecordsAdapter.updateItem(record, mRecyclerView.getCurrentItem());
        mRecyclerView.setCurrentItem(mRecyclerView.getCurrentItem() + 1);

        if (mTagRecordViewModel.needToLoadRecords(mRecyclerView.getCurrentItem())) {
            loadRecords();
        }

        if (mTagRecordViewModel.showProgress()) {
            Toast.makeText(this, "Progress: " + String.valueOf(mTagRecordViewModel.getSuccessfulSaves().size()), Toast.LENGTH_SHORT).show();
        }


        mTagRecordViewModel.save(record).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                Log.d(TAG, "onNext: " + o);

                if (mTagRecordViewModel.showProgress()) {
                    Toast.makeText(TagRecordActivity.this
                            , "Progress" + String.valueOf(mTagRecordViewModel.getSuccessfulSaves().size()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: Update Error", e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: Update completed");
            }
        });
    }
}
