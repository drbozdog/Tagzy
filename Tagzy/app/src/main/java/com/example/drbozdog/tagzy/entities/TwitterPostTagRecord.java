package com.example.drbozdog.tagzy.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by drbozdog on 04/02/18.
 */

public class TwitterPostTagRecord extends TagRecord {

    String text;
    Entities entities;
    User user;
    ExtendedTweet extended_tweet;

    public class Entities implements Serializable {
        List<Url> urls;
        List<Media> media;

        public List<Url> getUrls() {
            return urls;
        }

        public List<Media> getMedia() {
            return media;
        }
    }

    public Entities getEntities() {
        return entities;
    }

    public User getUser() {
        return user;
    }

    public ExtendedTweet getExtended_tweet() {
        return extended_tweet;
    }

    public static class Url implements Serializable {
        String url;
        String expanded_url;
        String display_url;

        String title;
        String description;
        List<String> images;

        public String getUrl() {
            return url;
        }

        public String getExpanded_url() {
            return expanded_url;
        }

        public String getDisplay_url() {
            return display_url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public class Media implements Serializable {
        String media_url;
        String media_url_https;
        String type;

        public String getMedia_url() {
            return media_url;
        }

        public String getMedia_url_https() {
            return media_url_https;
        }

        public String getType() {
            return type;
        }
    }

    public class User implements Serializable {
        String screen_name;
    }

    public class ExtendedTweet implements Serializable {
        String full_text;
        Entities entities;

        public String getFull_text() {
            return full_text;
        }

        public Entities getEntities() {
            return entities;
        }
    }

    public String getText() {
        if (extended_tweet != null) {
            return extended_tweet.full_text;
        }
        return text;
    }

    public String getUserName() {
        return user.screen_name;
    }
}
