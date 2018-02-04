package com.example.drbozdog.tagzy.viewmodels;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.drbozdog.tagzy.entities.TagJob;
import com.example.drbozdog.tagzy.entities.TagRecord;
import com.example.drbozdog.tagzy.entities.TwitterPostTagRecord;
import com.example.drbozdog.tagzy.entities.TwitterUserTagRecord;
import com.example.drbozdog.tagzy.managers.TagRecordManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

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


    @Inject
    public TagRecordViewModel(TagRecordManager tagRecordManager) {
        mTagRecordManager = tagRecordManager;
    }

    public Observable<List<TagRecord>> getRecords() {
        return mTagRecordManager.getRecords(mJob.getId(), records).map(jsonObjects -> convertToTagRecords(jsonObjects));
    }

    @NonNull
    private List<TagRecord> convertToTagRecords(List<JsonObject> jsonObjects) throws Exception {
        List<TagRecord> records = new ArrayList<>();
        Gson convertor = new Gson();
        for (JsonObject json : jsonObjects) {
            TagRecord record;
            if (mJob.getType().equals("twitter_user")) {
                record = convertor.fromJson(json, TwitterUserTagRecord.class);
            }else if (mJob.getType().equals("twitter_post")){
                record = convertor.fromJson(json, TwitterPostTagRecord.class);
            }else{
                throw new Exception("Tag Record Type not recodnized");
            }
            records.add(record);
        }
        return records;
    }


    public TagJob getJob() {
        return mJob;
    }

    public Observable<JsonObject> save(TagRecord record) {
        return mTagRecordManager.save(mJob.getId(), record).retry(3).doOnNext(jsonObject -> {
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

    public boolean showProgress() {
        return mSuccessfulSaves.size() % 20 == 0;
    }

    public void init(TagJob job) {
        mJob = job;
    }
}
