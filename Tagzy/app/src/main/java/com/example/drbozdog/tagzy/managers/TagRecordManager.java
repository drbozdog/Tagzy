package com.example.drbozdog.tagzy.managers;

import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.util.Log;

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
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by drbozdog on 16/12/17.
 */

public class TagRecordManager {

    private static final String TAG = TagRecordManager.class.getSimpleName();
    RecordsRepository mRecordsRepository;


    @Inject
    public TagRecordManager(RecordsRepository recordsRepository) {
        mRecordsRepository = recordsRepository;
    }


    public Observable<TagRecord> getRecords(int jobid, String type, int limit) {
        return mRecordsRepository.getRecords(jobid, limit).map(jsonObjects -> convertToTagRecords(jsonObjects, type))
                .flatMap(tagRecords -> {
                    if (type.equals("twitter_post")) {
                        return Observable.fromIterable(tagRecords)
                                .concatMap(tagRecord -> getUrlPreview((TwitterPostTagRecord) tagRecord));
                    } else {
                        return Observable.fromIterable(tagRecords);
                    }
                });
    }

    public Observable<JsonObject> save(int jobid, TagRecord record) {
        return mRecordsRepository.update(jobid, record);
    }

    public Observable<List<StatsMetric>> getStats(int jobid) {
        return mRecordsRepository.getStats(jobid);
    }

    private Observable<TagRecord> getUrlPreview(TwitterPostTagRecord tagRecord) {
        Log.d(TAG, "getUrlPreview:  " + tagRecord.getEntities().getUrls());
        if (tagRecord.getExtended_tweet() != null) {
            Log.d(TAG, "getUrlPreview extended tweet: " + tagRecord.getExtended_tweet().getEntities().getUrls());
        }
        if (tagRecord.getEntities().getUrls() != null) {
            final TextCrawler textCrawler = new TextCrawler();
            return Observable.fromIterable(tagRecord.getEntities().getUrls())
                    .concatMap((Function<TwitterPostTagRecord.Url, ObservableSource<TwitterPostTagRecord.Url>>) url -> Observable.create(e -> textCrawler.makePreview(new LinkPreviewCallback() {
                        @Override
                        public void onPre() {

                        }

                        @Override
                        public void onPos(SourceContent sourceContent, boolean b) {
                            url.setTitle(sourceContent.getTitle());
                            url.setDescription(sourceContent.getDescription());
                            url.setImages(sourceContent.getImages());
                            e.onNext(url);
                            e.onComplete();
                        }
                    }, url.getExpanded_url()))).timeout(3, TimeUnit.SECONDS).onErrorReturnItem(new TwitterPostTagRecord.Url()).filter(url -> url.getExpanded_url() != null)
                    .collectInto(tagRecord, (u, url) -> {
                    }).map((Function<TwitterPostTagRecord, TagRecord>) twitterPostTagRecord -> twitterPostTagRecord).toObservable().doFinally(() -> textCrawler.cancel());
        } else {
            return Observable.just(tagRecord);
        }

//        if (tagRecord.getExtended_tweet() != null && tagRecord.getExtended_tweet().getEntities().getUrls() != null) {
//
//        }
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
