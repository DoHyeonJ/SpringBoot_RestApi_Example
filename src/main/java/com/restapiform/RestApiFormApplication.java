package com.restapiform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RestApiFormApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestApiFormApplication.class, args);
    }

}
