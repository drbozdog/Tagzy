package com.example.drbozdog.tagzy.di.graph;

import com.example.drbozdog.tagzy.activities.TagRecordActivity;
import com.example.drbozdog.tagzy.fragments.TagRecordFragment;

/**
 * Created by drbozdog on 17/12/17.
 */

public interface Graph {

    void inject(TagRecordActivity activity);

    void inject(TagRecordFragment fragment);
}
