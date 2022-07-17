package com.elca.internship.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.elca.internship.server")
public class PimToolProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(PimToolProjectApplication.class, args);
    }

}
