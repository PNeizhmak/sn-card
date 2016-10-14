package com.sncard.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.config.annotation.EnableSocial;

/**
 * @author Pavel Neizhmak
 */

@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties
@EnableZuulProxy
@EnableSocial
@ComponentScan(basePackages = {"com.sncard"})
public class SnCardApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnCardApplication.class, args);
    }
}
