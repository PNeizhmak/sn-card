package com.sncard.model.twitter;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.social.twitter.api.Tweet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Neizhmak
 */
public class TimeLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private List<Tweet> tweets;

    @Getter
    @Setter
    private String type;

    public TimeLine() {
        tweets = new ArrayList<>();
    }

    public TimeLine(List<Tweet> tweets, String type) {
        this.type = type;
        addTweets(tweets);
    }

    public void addTweets(List<Tweet> tweets) {
        if (CollectionUtils.isNotEmpty(tweets)) {
            this.tweets.addAll(tweets);
        }
    }

}
