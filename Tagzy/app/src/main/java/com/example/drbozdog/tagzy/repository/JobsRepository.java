package com.example.drbozdog.tagzy.repository;

import com.example.drbozdog.tagzy.BuildConfig;
import com.example.drbozdog.tagzy.entities.StatsMetric;
import com.example.drbozdog.tagzy.entities.TagJob;
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
 * Created by drbozdog on 04/02/18.
 */

public class JobsRepository {

    private final TagzyApi mTagzyApi;

    @Inject
    public JobsRepository() {
        Retrofit service = new Retrofit.Builder().baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mTagzyApi = service.create(TagzyApi.class);
    }

    public Observable<List<TagJob>> getJobs() {
       return  mTagzyApi.getJobs();
    }


    public interface TagzyApi {
        @GET("jobs")
        Observable<List<TagJob>> getJobs();
    }

}
