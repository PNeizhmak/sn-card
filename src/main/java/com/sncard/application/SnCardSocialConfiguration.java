package com.sncard.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.util.Assert;

import javax.sql.DataSource;

/**
 * @author Pavel Neizhmak
 */
@Configuration
@EnableSocial
@PropertySources({
        @PropertySource("classpath:application.yml")
})
@ComponentScan(basePackages = {"com.sncard"})
public class SnCardSocialConfiguration {

    private final Environment env;
    private final DataSource dataSource;

    @Autowired
    public SnCardSocialConfiguration(Environment env, DataSource dataSource) {
        Assert.notNull(env, "Environment must be not null !");
        this.env = env;
        this.dataSource = dataSource;
    }

    @Bean
    public ConnectionFactoryLocator connectionFactoryLocator() {

        ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
        registry.addConnectionFactory(new TwitterConnectionFactory(env.getProperty("twitter.appId"), env.getProperty("twitter.appSecret")));
        registry.addConnectionFactory(new FacebookConnectionFactory(env.getProperty("facebook.appId"), env.getProperty("facebook.appSecret")));
        registry.addConnectionFactory(new LinkedInConnectionFactory(env.getProperty("linkedin.appId"), env.getProperty("linkedin.appSecret")));
        registry.addConnectionFactory(new GoogleConnectionFactory(env.getProperty("google.appId"), env.getProperty("google.appSecret")));

        return registry;
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public UsersConnectionRepository usersConnectionRepository() {
        return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator(), Encryptors.noOpText());
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public ConnectionRepository connectionRepository() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
        }
        return usersConnectionRepository().createConnectionRepository(authentication.getName());
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public Facebook facebook(ConnectionRepository repository) {
        Connection<Facebook> connection = repository.findPrimaryConnection(Facebook.class);
        return connection != null ? connection.getApi() : null;
    }
}