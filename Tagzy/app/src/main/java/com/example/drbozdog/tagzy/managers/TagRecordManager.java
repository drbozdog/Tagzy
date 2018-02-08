package com.example.drbozdog.tagzy.managers;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by drbozdog on 16/12/17.
 */

public class TagRecordManager {

    private static final String TAG = TagRecordManager.class.getSimpleName();
    RecordsRepository mRecordsRepository;

    ExecutorService mExecutorService = Executors.newFixedThreadPool(4);


    @Inject
    public TagRecordManager(RecordsRepository recordsRepository) {
        mRecordsRepository = recordsRepository;
    }


    public Observable<TagRecord> getRecords(int jobid, String type, int limit) {
        return mRecordsRepository.getRecords(jobid, limit).map(jsonObjects -> convertToTagRecords(jsonObjects, type))
                .flatMap(tagRecords -> {
                    if (type.equals("twitter_post")) {
                        return Observable.fromIterable(tagRecords)
                                .concatMap(tagRecord -> getPreviewForUrls((TwitterPostTagRecord) tagRecord));
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

    private Observable<TagRecord> getPreviewForUrls(TwitterPostTagRecord tagRecord) {
        if (tagRecord.getEntities().getUrls() != null) {
            final TextCrawler textCrawler = new TextCrawler();
            List<TwitterPostTagRecord.Url> urls = new ArrayList<>();
            return Observable.fromIterable(tagRecord.getEntities().getUrls())
                    .concatMap(url -> getPreviewForUrl(url, textCrawler))
                    .collectInto(urls, (u, url) -> urls.add(url))
                    .map((Function<List<TwitterPostTagRecord.Url>, TagRecord>) urls1 -> {
                        tagRecord.getEntities().setUrls(urls1);
                        return tagRecord;
                    }).toObservable().doFinally(() -> textCrawler.cancel());
        } else {
            return Observable.just(tagRecord);
        }
    }

    private ObservableSource<TwitterPostTagRecord.Url> getPreviewForUrl(TwitterPostTagRecord.Url url, TextCrawler textCrawler) {
        return Observable.create((ObservableEmitter<TwitterPostTagRecord.Url> e) -> {
            Log.d(TAG, "getPreviewForUrl: start crawling" + url.getExpanded_url());
            textCrawler.makePreview(new LinkPreviewCallback() {
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
                    Log.d(TAG, "onPos: ended crawling" + url.getExpanded_url());
                }
            }, url.getExpanded_url());
        }).timeout(6, TimeUnit.SECONDS)
                .doOnError(throwable -> Log.d(TAG, "getPreviewForUrl: ended crawling"))
                .onErrorReturnItem(new TwitterPostTagRecord.Url())
                .filter(u -> filterEmptyUrl(u));
    }

    private boolean filterEmptyUrl(TwitterPostTagRecord.Url url) {
        if (url.getExpanded_url() != null && url.getTitle() != null && url.getDescription() != null
                && url.getTitle() != "" && url.getDescription() != "") {
            return true;
        } else {
            return false;
        }
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
