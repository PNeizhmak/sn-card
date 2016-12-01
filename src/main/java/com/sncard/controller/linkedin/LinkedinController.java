package com.sncard.controller.linkedin;

import javax.inject.Inject;

import lombok.val;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pavel Neizhmak
 */
@RestController
@RequestMapping("/api/connect/linkedin")
public class LinkedinController {

    private ConnectionRepository connectionRepository;
    private LinkedIn linkedIn;
    private LinkedInProfile inProfile;

    @Inject
    public LinkedinController(ConnectionRepository connectionRepository, LinkedIn linkedIn) {
        this.connectionRepository = connectionRepository;
        this.linkedIn = linkedIn;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public LinkedInProfile fetchProfile() {
        val connection = connectionRepository.findPrimaryConnection(LinkedIn.class);
        if (connection == null) {
            return null;
        }
        if (inProfile == null) {
            inProfile = connection.getApi().profileOperations().getUserProfile();
        }
        return inProfile;
    }
}
