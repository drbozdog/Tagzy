package com.example.drbozdog.tagzy.di.modules;

import android.content.Context;

import com.example.drbozdog.tagzy.managers.TagJobsManager;
import com.example.drbozdog.tagzy.managers.TagRecordManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by drbozdog on 16/12/17.
 */
@Module
public class AppModule {

    Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }


}
