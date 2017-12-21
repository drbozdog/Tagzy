package com.example.drbozdog.tagzy.viewmodels;

import android.util.Log;
import android.widget.BaseExpandableListAdapter;

import com.example.drbozdog.tagzy.entities.TagJob;
import com.example.drbozdog.tagzy.entities.TagRecord;
import com.example.drbozdog.tagzy.managers.TagRecordManager;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by drbozdog on 16/12/17.
 */

public class TagRecordViewModel {

    private static final String TAG = TagRecordViewModel.class.getSimpleName();
    TagRecordManager mTagRecordManager;
    private TagJob mJob;

    private final int records = 100;
    private int delta = 10;

    private List<TagRecord> mFailedSaves = new ArrayList<>();
    private List<TagRecord> mSuccessfulSaves = new ArrayList<>();

    BehaviorSubject<TagRecord> mRecordBehaviorSubject;

    @Inject
    public TagRecordViewModel(TagRecordManager tagRecordManager) {
        mTagRecordManager = tagRecordManager;
        mJob = new TagJob("Ridgid Professionals",
                "1",
                Arrays.asList("Professional", "Shop", "Manufacturer", "Blogger", "Jobs", "None"),
                null,
                null,
                null
        );
    }

    public Observable<List<TagRecord>> getRecords() {
        return mTagRecordManager.getRecords(records);
    }

    public TagJob getJob() {
        return mJob;
    }

    public Observable<JsonObject> save(TagRecord record) {
        return mTagRecordManager.save(record).retry(3).doOnNext(jsonObject -> {
            if (!mSuccessfulSaves.contains(record)) {
                Log.d(TAG, "save: " + jsonObject.toString());
                mSuccessfulSaves.add(record);
            }
        }).doOnError(throwable -> {
            Log.e(TAG, "save: Error", throwable);
            if (!mFailedSaves.contains(record)) {
                mFailedSaves.add(record);
            }
        });
    }

    public List<TagRecord> getFailedSaves() {
        return mFailedSaves;
    }

    public List<TagRecord> getSuccessfulSaves() {
        return mSuccessfulSaves;
    }

    public boolean needToLoadRecords(int currentItemIndex) {
        if (currentItemIndex % records == records - delta) {
            return true;
        } else {
            return false;
        }
    }
}
