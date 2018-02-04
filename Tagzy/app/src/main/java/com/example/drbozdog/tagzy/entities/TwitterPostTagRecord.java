package com.example.drbozdog.tagzy.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by drbozdog on 04/02/18.
 */

public class TwitterPostTagRecord extends TagRecord {

    String text;
    //    Entities entities;
    User user;
    ExtendedTweet extended_tweet;

//    public class Entities {
//        List<String> urls;
//    }

    public class User implements Serializable {
        String screen_name;
    }

    public class ExtendedTweet implements Serializable {
        String full_text;
//        Entities entities;
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
