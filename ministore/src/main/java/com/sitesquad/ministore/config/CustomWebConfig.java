package com.sitesquad.ministore.config;

import com.sitesquad.ministore.dto.RequestMeta;
import com.sitesquad.ministore.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@CrossOrigin

public class CustomWebConfig implements WebMvcConfigurer {
    @Autowired
    JwtInterceptor jwtInterceptor;



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor);
    }



    @Bean
    @RequestScope
    public RequestMeta getRequestMeta(){
        return new RequestMeta();
    }

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor(getRequestMeta());
    }

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
