package com.example.drbozdog.tagzy.entities;

import java.io.Serializable;

/**
 * Created by drbozdog on 22/12/2017.
 */

public class StatsMetric implements Serializable {
    String name;
    int count;

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }
}
