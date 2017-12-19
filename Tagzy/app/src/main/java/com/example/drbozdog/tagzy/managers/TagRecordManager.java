package com.example.drbozdog.tagzy.managers;

import com.example.drbozdog.tagzy.entities.TagRecord;
import com.example.drbozdog.tagzy.repository.RecordsRepository;
import com.google.gson.JsonObject;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by drbozdog on 16/12/17.
 */

public class TagRecordManager {

    RecordsRepository mRecordsRepository;

    @Inject
    public TagRecordManager(RecordsRepository recordsRepository) {
        mRecordsRepository = recordsRepository;
    }


    public Observable<List<TagRecord>> getRecords() {
        return mRecordsRepository.getRecords();
    }

    public Observable<JsonObject> save(TagRecord record) {
        return mRecordsRepository.update(record);
    }
}
