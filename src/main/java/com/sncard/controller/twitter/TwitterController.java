package com.sncard.controller.twitter;

/**
 * @author Pavel Neizhmak
 */

import com.sncard.model.twitter.TimeLine;
import lombok.Data;
import lombok.val;
import org.springframework.http.MediaType;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.io.Serializable;
import java.security.Principal;

@RestController
@RequestMapping("/api/connect/twitter")
public class TwitterController {

    // Yahoo Where On Earth ID representing the entire world
    private static final long WORLDWIDE_WOE = 1L;

    private final ConnectionRepository connectionRepository;
    private final Twitter twitter;
    private TwitterProfile profile;

    @Inject
    public TwitterController(ConnectionRepository connectionRepository, Twitter twitter) {
        this.twitter = twitter;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public TwitterProfile fetchProfile(Principal currentUser, Model model) {
        val connection = connectionRepository.findPrimaryConnection(Twitter.class);
        if (connection == null) {
            return null;
        }
        if (profile == null) {
            profile = connection.getApi().userOperations().getUserProfile();
        }
        return profile;
    }

    @RequestMapping(value = "/tweets", method = RequestMethod.GET)
    public TimeLine fetchTimeLine() {
        val timeline = new TimeLine();
        val timelineOperations = twitter.timelineOperations();

        timeline.addTweets(timelineOperations.getHomeTimeline());
        timeline.addTweets(timelineOperations.getUserTimeline());
        timeline.addTweets(timelineOperations.getMentions());
        timeline.addTweets(timelineOperations.getFavorites());

        return timeline;
    }

    @RequestMapping(value = "/tweet", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void postTweet(@RequestBody TweetContent content) {
        if (content != null) {
            twitter.timelineOperations().updateStatus(content.getText());
        }
    }

    @RequestMapping(value = "/friends", method = RequestMethod.GET)
    public CursoredList<TwitterProfile> friends(Model model) {
        return twitter.friendOperations().getFriends();
    }

    @RequestMapping(value = "/followers", method = RequestMethod.GET)
    public CursoredList<TwitterProfile> followers(Model model) {
        return twitter.friendOperations().getFollowers();
    }

    @RequestMapping(value = "/trends", method = RequestMethod.GET)
    public Trends showTrends(Model model) {
        return twitter.searchOperations().getLocalTrends(WORLDWIDE_WOE);
    }

    @Data
    private static class TweetContent implements Serializable {
        private static final long serialVersionUID = 1L;
        private String text;
    }
}
