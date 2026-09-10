package com.wms.mini.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
//                .allowedOrigins("http://localhost:9000")
                .allowedOriginPatterns("http://localhost:*") // 어떤 경로로든 접근 가능하도록 포트를 느슨하게 열어두기
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}