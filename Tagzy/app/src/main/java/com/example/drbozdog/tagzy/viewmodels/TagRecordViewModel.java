package com.example.drbozdog.tagzy.viewmodels;

import com.example.drbozdog.tagzy.entities.TagJob;
import com.example.drbozdog.tagzy.entities.TagRecord;
import com.example.drbozdog.tagzy.managers.TagRecordManager;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by drbozdog on 16/12/17.
 */

public class TagRecordViewModel {

    TagRecordManager mTagRecordManager;
    private TagJob mJob;

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
        return mTagRecordManager.getRecords();
    }

    public TagJob getJob() {
        return mJob;
    }

    public Observable<JsonObject> save(TagRecord record) {
        return mTagRecordManager.save(record);
    }
}
