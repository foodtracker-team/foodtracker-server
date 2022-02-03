package com.foodtracker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import com.foodtracker.AppConfig;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
  @Autowired
  AppConfig appConfig;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    String path = "file:" + appConfig.STORAGE_ABSOLUTE_PATH + "/";
    System.out.println(path);
    registry.addResourceHandler("/static/**")
      .addResourceLocations(path);
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/static/**")
      .allowedOrigins("*");
  }

}