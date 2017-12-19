package com.example.drbozdog.tagzy.repository;

import com.example.drbozdog.tagzy.entities.TagRecord;
import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.json.JSONObject;

import java.util.List;


import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by drbozdog on 17/12/17.
 */

public class RecordsRepository {


    private final TagzyApi mTagzyApi;

    @Inject
    public RecordsRepository() {
        Retrofit service = new Retrofit.Builder().baseUrl("http://10.0.3.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mTagzyApi = service.create(TagzyApi.class);
    }


    public interface TagzyApi {
        @GET("/records")
        Observable<List<TagRecord>> getRecords();

        @POST("/record")
        Observable<JsonObject> updateRecord(@Body TagRecord record);
    }

    public Observable<List<TagRecord>> getRecords() {
        return mTagzyApi.getRecords();
    }

    public Observable<JsonObject> update(TagRecord record) {
        return mTagzyApi.updateRecord(record);
    }
}
