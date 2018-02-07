package com.example.drbozdog.tagzy.entities;

/**
 * Created by drbozdog on 04/02/18.
 */

public class TwitterUserTagRecord extends TagRecord {

    String description;
    String name;
    String profile_background_image_url;
    String profile_banner_url;
    String profile_image_url;
    String screen_name;
    String verified;
    String created_at;
    String url;

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

}
