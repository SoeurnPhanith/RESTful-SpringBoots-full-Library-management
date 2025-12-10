package com.example.library_management.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //tell spring this is config class
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) { //ResourceHandlerRegistry registry → object ដែល Spring Boot ផ្តល់, ដើម្បី register resource handlers ថ្មី
        registry.addResourceHandler("/uploads/**")//បង្ហាញ pattern នៃ URL ដែល client អាច access image ban doy pderm pii uploads
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/uploads/");//បង្ហាញ ទីតាំង file system ដែល resource ត្រូវបានរក្សាtuk
    }
}
