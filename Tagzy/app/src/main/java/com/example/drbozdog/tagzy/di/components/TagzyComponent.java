package com.example.drbozdog.tagzy.di.components;

import com.example.drbozdog.tagzy.di.graph.Graph;
import com.example.drbozdog.tagzy.di.modules.AppModule;

import dagger.Component;

/**
 * Created by drbozdog on 16/12/17.
 */

@Component(modules = {AppModule.class})
public interface TagzyComponent extends Graph {

}
