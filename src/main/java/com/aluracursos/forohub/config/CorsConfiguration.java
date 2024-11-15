package com.aluracursos.forohub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:3000")
//                .allowedOrigins("http://192.168.0.16:8080")
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS",
                        "HEAD","TRACE","CONNECT")
                .allowedHeaders("Authorization", "Content-Type")
                .allowCredentials(true)
                .exposedHeaders("Access-Control-Allow-Origin", "Authorization");
    }
}
