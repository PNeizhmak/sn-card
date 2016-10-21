package com.sncard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Inject;

/**
 * @author Pavel Neizhmak
 */
@Controller
@RequestMapping("/connect")
public class CustomConnectController extends ConnectController {

    @Inject
    public CustomConnectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
        super(connectionFactoryLocator, connectionRepository);
    }

    @Override
    protected RedirectView connectionStatusRedirect(String providerId, NativeWebRequest request) {
        return new RedirectView("/index.html#/connections", true);
    }
}
