package com.S2T.Share_2_Teach.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {

    // Defines a bean to configure CORS (Cross-Origin Resource Sharing) settings
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {

            // Configures CORS mappings for the application
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Allows CORS for all paths in the application
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Specifies allowed HTTP methods
                        .allowedOrigins("*"); // Allows requests from any origin
            }
        };
    }
}
