package com.polling.polling_project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "my")
public class MyProperties {
    @Value("authorized")
    private String authorized;

    public String getAuthorized() {
        return authorized;
    }

    public MyProperties setAuthorized(String authorized) {
        this.authorized = authorized;
        return this;
    }
}
