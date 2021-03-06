package com.example.drbozdog.tagzy.repository;

import com.example.drbozdog.tagzy.BuildConfig;
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
        Retrofit service = new Retrofit.Builder().baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mTagzyApi = service.create(TagzyApi.class);
    }


    public interface TagzyApi {
        @GET("{jobid}/records/{limit}")
        Observable<List<JsonObject>> getRecords(@Path("jobid") int jobid, @Path("limit") int limit);

        @POST("{jobid}/record")
        Observable<JsonObject> updateRecord(@Path("jobid") int jobid, @Body TagRecord record);

        @GET("{jobid}/stats")
        Observable<List<StatsMetric>> getStats(@Path("jobid") int jobid);
    }

    public Observable<List<JsonObject>> getRecords(int jobid, int limit) {
        return mTagzyApi.getRecords(jobid, limit);
    }

    public Observable<JsonObject> update(int jobid, TagRecord record) {
        return mTagzyApi.updateRecord(jobid, record);
    }

    public Observable<List<StatsMetric>> getStats(int jobid) {
        return mTagzyApi.getStats(jobid);
    }
}
