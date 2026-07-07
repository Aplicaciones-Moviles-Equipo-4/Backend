package com.eventify.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main application entry point for the Eventify Platform.
 * Configures the Spring Boot application context and enables JPA auditing.
 */
@SpringBootApplication
@EnableJpaAuditing
public class EventifyPlatformApplication {

    /**
     * Starts the Spring Boot application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(EventifyPlatformApplication.class, args);
    }

}
