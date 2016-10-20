package com.sncard.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Pavel Neizhmak
 */

@SpringBootApplication
@EnableConfigurationProperties
@EnableZuulProxy
@ComponentScan(basePackages = {"com.sncard"})
public class SnCardApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnCardApplication.class, args);
    }
}
