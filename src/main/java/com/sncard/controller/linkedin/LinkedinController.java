package com.sncard.controller.linkedin;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * @author Pavel Neizhmak
 */
@RestController
@RequestMapping("/api/connect/linkedin")
public class LinkedinController {

    private ConnectionRepository connectionRepository;
    private LinkedInProfile inProfile;

    @Inject
    public LinkedinController(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public LinkedInProfile fetchProfile() {
        Connection<LinkedIn> connection = connectionRepository.findPrimaryConnection(LinkedIn.class);
        if (connection == null) {
            return null;
        }
        if (inProfile == null) {
            inProfile = connection.getApi().profileOperations().getUserProfile();
        }
        return inProfile;
    }
}
