package com.example.drbozdog.tagzy.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.drbozdog.tagzy.R;
import com.example.drbozdog.tagzy.TagzyApplication;
import com.example.drbozdog.tagzy.adapters.StatsMetricsAdapter;
import com.example.drbozdog.tagzy.entities.StatsMetric;
import com.example.drbozdog.tagzy.entities.TagJob;
import com.example.drbozdog.tagzy.viewmodels.StatsViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class StatsActivity extends AppCompatActivity {

    private static final String TAG = StatsActivity.class.getSimpleName();
    public static String EXTRA_SUCCESSFUL = "extra_successful";
    public static String EXTRA_ERRORS = "extra_errors";
    public static String EXTRA_JOB = "extra_job";

    @BindView(R.id.txt_tagged)
    TextView mTxtTaggedCount;

    @BindView(R.id.txt_errors)
    TextView mTxtErrors;

    @BindView(R.id.list_statsmetrics)
    RecyclerView mRecyclerViewMetrics;

    @Inject
    StatsViewModel mStatsViewModel;

    DisposableObserver<List<StatsMetric>> mStatsDisposableObserver;
    private StatsMetricsAdapter mStatsMetricsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        int uploadSuccessfulCount = getIntent().getIntExtra(EXTRA_SUCCESSFUL, 0);
        int uploadErrorsCount = getIntent().getIntExtra(EXTRA_ERRORS, 0);
        TagJob job = (TagJob) getIntent().getSerializableExtra(EXTRA_JOB);

        ButterKnife.bind(this);

        TagzyApplication.getGraph().inject(this);

        mStatsViewModel.init(job);

        mTxtTaggedCount.setText("Successful uploaded tagged records: " + String.valueOf(uploadSuccessfulCount));
        mTxtErrors.setText("Errors when uploading tagged records: " + String.valueOf(uploadErrorsCount));

        mStatsMetricsAdapter = new StatsMetricsAdapter();
        mRecyclerViewMetrics.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewMetrics.setAdapter(mStatsMetricsAdapter);

        if (mStatsDisposableObserver == null) {
            mStatsDisposableObserver = new DisposableObserver<List<StatsMetric>>() {
                @Override
                public void onNext(List<StatsMetric> stats) {
                    mStatsMetricsAdapter.update(stats);
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

            mStatsViewModel.getStats().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(mStatsDisposableObserver);
        }

    }

    @Override
    protected void onDestroy() {
        if (mStatsDisposableObserver != null && !mStatsDisposableObserver.isDisposed()) {
            mStatsDisposableObserver.dispose();
        }
        super.onDestroy();
    }
}
