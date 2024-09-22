package com.example.spotlight_spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Apply CORS to all /api paths
                        .allowedOrigins("http://localhost:5173") // Allow requests from this origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow specific methods
                        .allowedHeaders("*") // Allow all headers
                        .allowCredentials(true); // If you need to send cookies or credentials
            }
        };
    }
}

