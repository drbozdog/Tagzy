package com.example.drbozdog.tagzy;

import android.app.Application;

import com.example.drbozdog.tagzy.di.components.DaggerTagzyComponent;
import com.example.drbozdog.tagzy.di.graph.Graph;
import com.example.drbozdog.tagzy.di.modules.AppModule;

/**
 * Created by drbozdog on 16/12/17.
 */

public class TagzyApplication extends Application {


    static Graph mGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        mGraph = DaggerTagzyComponent.builder().appModule(new AppModule(getApplicationContext())).build();
    }

    public static Graph getGraph() {
        return mGraph;
    }
}
