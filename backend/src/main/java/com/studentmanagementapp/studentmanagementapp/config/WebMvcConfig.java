package com.studentmanagementapp.studentmanagementapp.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configures Spring MVC to serve uploaded profile images as static resources.
 * Maps HTTP path /uploads/** → file-system directory uploads/.
 *
 * This is only relevant for the local storage profile.
 * In production (GCS), images are served directly from the cloud bucket.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Value("${app.storage.upload-dir:uploads/}")
  private String uploadDir;

  @Override
  public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("http://localhost:5173") // Allow Vue dev server
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(true);
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    Path uploadPath = Paths.get(uploadDir).toAbsolutePath();
    registry.addResourceHandler("/uploads/**")
        .addResourceLocations("file:" + uploadPath + "/");
  }
}
