package com.example.drbozdog.tagzy.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by drbozdog on 16/12/17.
 */

public class TagJob implements Serializable {
    String name;
    int id;
    String type;
    List<String> tags;
    String collection;

    public TagJob(String name, int id, String type, List<String> tags, String tagSourceName, String tagSourceQuery) {
        this.name = name;
        this.id = id;
        type = type;
        this.tags = tags;
        collection = tagSourceName;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getCollection() {
        return collection;
    }

    public String getType() {
        return type;
    }
}
