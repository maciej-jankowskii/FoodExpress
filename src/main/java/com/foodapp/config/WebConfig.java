package com.foodapp.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
@ComponentScan
public class WebConfig implements ErrorPageRegistrar {
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        registry.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/address-error"));
        registry.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/password-change-error"));
    }
}
