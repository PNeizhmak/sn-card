package com.sncard.controller.facebook;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Pavel Neizhmak
 */
@RestController
@RequestMapping("/api/connect/facebook")
public class FacebookController {

    private final ConnectionRepository connectionRepository;
    private final Facebook facebook;
    private User profile;

    @Inject
    public FacebookController(ConnectionRepository connectionRepository, Facebook facebook) {
        this.connectionRepository = connectionRepository;
        this.facebook = facebook;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public User fetchProfile() {
        Connection<Facebook> connection = connectionRepository.findPrimaryConnection(Facebook.class);
        if (connection == null) {
            return null;
        }
        if (profile == null) {
            profile = connection.getApi().userOperations().getUserProfile();
        }
        return profile;
    }

    @RequestMapping(value = "/friends", method = RequestMethod.GET)
    public List<User> getFriends() {
        return facebook.friendOperations().getFriendProfiles();
    }

    @RequestMapping(value = "/albums", method = RequestMethod.GET)
    public PagedList<Album> albums() {
        return facebook.mediaOperations().getAlbums();
    }

    @RequestMapping(value = "/album/{albumId}", method = RequestMethod.GET)
    public PagedList<Photo> album(@PathVariable("albumId") String albumId) {
        return facebook.mediaOperations().getPhotos(albumId);
    }
}
