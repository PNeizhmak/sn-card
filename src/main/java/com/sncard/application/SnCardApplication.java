package com.sncard.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Pavel Neizhmak
 */
@SpringBootApplication
@EnableZuulProxy
public class SnCardApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnCardApplication.class, args);
    }
}
