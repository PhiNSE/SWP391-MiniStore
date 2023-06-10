package com.sitesquad.ministore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")  // Specify the allowed origin(s)
                .allowedMethods("*")  // Specify the allowed HTTP methods
                .allowedHeaders("*")  // Specify the allowed request headers
                .maxAge(3600);  // Specify the maximum age of the CORS preflight request
    }
}
