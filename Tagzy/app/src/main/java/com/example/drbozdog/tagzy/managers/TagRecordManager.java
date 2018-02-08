package com.example.drbozdog.tagzy.managers;

import android.support.annotation.NonNull;

import com.example.drbozdog.tagzy.entities.StatsMetric;
import com.example.drbozdog.tagzy.entities.TagRecord;
import com.example.drbozdog.tagzy.entities.TwitterPostTagRecord;
import com.example.drbozdog.tagzy.entities.TwitterUserTagRecord;
import com.example.drbozdog.tagzy.repository.RecordsRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.leocardz.link.preview.library.LinkPreviewCallback;
import com.leocardz.link.preview.library.SourceContent;
import com.leocardz.link.preview.library.TextCrawler;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Function;

/**
 * Created by drbozdog on 16/12/17.
 */

public class TagRecordManager {

    RecordsRepository mRecordsRepository;



    @Inject
    public TagRecordManager(RecordsRepository recordsRepository) {
        mRecordsRepository = recordsRepository;
    }


    public Observable<List<TagRecord>> getRecords(int jobid,String type, int limit) {
        return mRecordsRepository.getRecords(jobid, limit).map(jsonObjects -> convertToTagRecords(jsonObjects,type))
                .flatMap(tagRecords -> {
                    if (type.equals("twitter_post")) {
                        List<TagRecord> collection = new ArrayList<>();
                        final TextCrawler textCrawler = new TextCrawler();
                        return Observable.fromIterable(tagRecords).collectInto(collection, (u, tagRecord) -> u.add(tagRecord)).toObservable().doFinally(() -> {
                            textCrawler.cancel();
                        });
                    }else{
                        return Observable.just(tagRecords);
                    }
                });
    }

    public Observable<JsonObject> save(int jobid, TagRecord record) {
        return mRecordsRepository.update(jobid, record);
    }

    public Observable<List<StatsMetric>> getStats(int jobid) {
        return mRecordsRepository.getStats(jobid);
    }

    @NonNull
    private List<TagRecord> convertToTagRecords(List<JsonObject> jsonObjects, String type) throws Exception {
        List<TagRecord> records = new ArrayList<>();
        Gson convertor = new Gson();
        for (JsonObject json : jsonObjects) {
            TagRecord record;
            if (type.equals("twitter_user")) {
                record = convertor.fromJson(json, TwitterUserTagRecord.class);
            } else if (type.equals("twitter_post")) {
                record = convertor.fromJson(json, TwitterPostTagRecord.class);
            } else {
                throw new Exception("Tag Record Type not recodnized");
            }
            records.add(record);
        }
        return records;
    }
}
