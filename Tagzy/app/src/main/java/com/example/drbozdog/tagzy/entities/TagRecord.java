package com.example.drbozdog.tagzy.entities;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by drbozdog on 16/12/17.
 */

public class TagRecord implements Serializable {
    String id;
    String description;
    String name;
    String profile_background_image_url;
    String profile_banner_url;
    String profile_image_url;
    String screen_name;
    String verified;
    String created_at;
    String url;
    MiningMetaData mining_metadata;


    public TagRecord() {

    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getProfile_background_image_url() {
        return profile_background_image_url;
    }

    public String getProfile_banner_url() {
        return profile_banner_url;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public String getVerified() {
        return verified;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUrl() {
        return url;
    }

    public MiningMetaData getMining_metadata() {
        if (mining_metadata == null) {
            mining_metadata = new MiningMetaData();
        }
        return mining_metadata;
    }

    public void setTag(String tag, String job) {
        getMining_metadata().getTags().put(job, tag);
    }

    public String getTag(String job) {
        if (getMining_metadata().getTags().containsKey(job)) {
            return getMining_metadata().getTags().get(job);
        } else {
            return null;
        }
    }

    public float getPrediction(String job, String tag) {
        if (getMining_metadata().getPredictionsProbabilities().containsKey(job) && getMining_metadata().getPredictionsProbabilities().get(job).containsKey(tag)) {
            return getMining_metadata().getPredictionsProbabilities().get(job).get(tag);
        } else {
            return -1f;
        }
    }

    public class MiningMetaData implements Serializable {
        public HashMap<String, String> getTags() {
            if (tags == null) {
                tags = new HashMap<>();
            }
            return tags;
        }

        HashMap<String, String> tags = new HashMap<>();

        public HashMap<String, HashMap<String, Float>> getPredictionsProbabilities() {
            return predictions_probabilities;
        }

        HashMap<String, HashMap<String, Float>> predictions_probabilities = new HashMap<>();
        HashMap<String, String> predictions = new HashMap<>();
    }
}
