package com.sncard.controller;

import com.sncard.model.ConnectionInfo;
import lombok.val;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.stereotype.Controller;
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
        val response = new HashSet<ConnectionInfo>();
        val registeredProviderIds = connectionFactoryLocator.registeredProviderIds();
        val connections = connectionRepository.findAllConnections();
        val builder = ConnectionInfo.builder();
        if (CollectionUtils.isNotEmpty(registeredProviderIds)) {
            for (val providerId : registeredProviderIds) {
                builder.provider(providerId).displayName("Alias");
                val connectionList = connections.get(providerId);
                if (CollectionUtils.isNotEmpty(connectionList)) {
                    val displayName = connectionList.get(0).getDisplayName();
                    if (StringUtils.isNotBlank(displayName)) {
                        builder.displayName(displayName);
                    }
                    builder.isConfigured(true);
                }

                response.add(builder.build());
            }
        }
        return response;
    }
}
