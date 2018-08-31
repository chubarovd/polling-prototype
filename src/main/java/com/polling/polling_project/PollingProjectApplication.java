package com.polling.polling_project;

import com.polling.polling_project.config.MyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class PollingProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(PollingProjectApplication.class, args);
    }
}
