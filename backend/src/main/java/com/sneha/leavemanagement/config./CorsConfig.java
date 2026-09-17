package com.sneha.leavemanagement.config;
import org.springframework.context.annotation.*; import org.springframework.web.servlet.config.annotation.*;
@Configuration public class CorsConfig implements WebMvcConfigurer{ @Override public void addCorsMappings(CorsRegistry r){r.addMapping("/api/**").allowedOrigins("*").allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS").allowedHeaders("*").allowCredentials(false);} }
