package com.example.drbozdog.tagzy.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by drbozdog on 16/12/17.
 */

public class TagJob implements Serializable {
    String mName;
    String mId;
    List<String> mTags;
    String mTagSourceName;
    String mTagSourceQuery;
    List<TagSourceField> mTagsSourceFields;

    public TagJob(String name, String id, List<String> tags, String tagSourceName, String tagSourceQuery, List<TagSourceField> tagsSourceFields) {
        mName = name;
        mId = id;
        mTags = tags;
        mTagSourceName = tagSourceName;
        mTagSourceQuery = tagSourceQuery;
        mTagsSourceFields = tagsSourceFields;
    }

    public String getName() {
        return mName;
    }

    public String getId() {
        return mId;
    }

    public List<String> getTags() {
        return mTags;
    }

    public String getTagSourceName() {
        return mTagSourceName;
    }

    public String getTagSourceQuery() {
        return mTagSourceQuery;
    }

    public List<TagSourceField> getTagsSourceFields() {
        return mTagsSourceFields;
    }
}
