package com.example.drbozdog.tagzy.repository;

import com.example.drbozdog.tagzy.entities.StatsMetric;
import com.example.drbozdog.tagzy.entities.TagRecord;
import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;


import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by drbozdog on 17/12/17.
 */

public class RecordsRepository {


    private final TagzyApi mTagzyApi;

    @Inject
    public RecordsRepository() {
        //10.0.3.2
        Retrofit service = new Retrofit.Builder().baseUrl("http://78.96.152.221:8001")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mTagzyApi = service.create(TagzyApi.class);
    }


    public interface TagzyApi {
        @GET("/records/{limit}")
        Observable<List<TagRecord>> getRecords(@Path("limit") int limit);

        @POST("/record")
        Observable<JsonObject> updateRecord(@Body TagRecord record);

        @GET("/stats")
        Observable<List<StatsMetric>> getStats();
    }

    public Observable<List<TagRecord>> getRecords(int limit) {
        return mTagzyApi.getRecords(limit);
    }

    public Observable<JsonObject> update(TagRecord record) {
        return mTagzyApi.updateRecord(record);
    }

    public Observable<List<StatsMetric>> getStats() {
        return mTagzyApi.getStats();
    }
}
