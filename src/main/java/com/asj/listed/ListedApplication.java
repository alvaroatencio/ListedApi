package com.asj.listed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
public class ListedApplication {

    public static void main(String[] args) {
        SpringApplication.run(ListedApplication.class, args);
    }

}
