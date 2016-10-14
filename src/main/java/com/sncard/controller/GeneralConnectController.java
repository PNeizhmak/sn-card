package com.sncard.controller;

import com.sncard.model.ConnectionInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.connect.web.ConnectSupport;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Pavel Neizhmak
 */
@Controller
@RequestMapping(value = "/api/connect")
public class GeneralConnectController {

    private final ConnectionFactoryLocator connectionFactoryLocator;
    private final ConnectionRepository connectionRepository;

    @Inject
    public GeneralConnectController(
            ConnectionFactoryLocator connectionFactoryLocator,
            ConnectionRepository connectionRepository) {
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    @ResponseBody
    public Set<ConnectionInfo> connect() {
        final Set<ConnectionInfo> response = new HashSet<>();
        final Set<String> registeredProviderIds = connectionFactoryLocator.registeredProviderIds();
        final Map<String, List<Connection<?>>> connections = connectionRepository.findAllConnections();
        if (CollectionUtils.isNotEmpty(registeredProviderIds)) {
            for (String providerId : registeredProviderIds) {
                ConnectionInfo connectionInfo = new ConnectionInfo(providerId, "Alias");
                List<Connection<?>> connectionList = connections.get(providerId);
                if (CollectionUtils.isNotEmpty(connectionList)) {
                    String displayName = connectionList.get(0).getDisplayName();
                    if (StringUtils.isNotBlank(displayName)) {
                        connectionInfo.setDisplayName(displayName);
                    }
                    connectionInfo.markAsConfigured();
                }

                response.add(connectionInfo);
            }
        }
        return response;
    }
}
