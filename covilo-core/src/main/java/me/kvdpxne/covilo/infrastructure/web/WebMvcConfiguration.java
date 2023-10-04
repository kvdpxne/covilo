package me.kvdpxne.covilo.infrastructure.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration
  implements WebMvcConfigurer {

  /**
   * Locations of static resources.
   */
  @Value("${spring.web.resources.static-locations}")
  private String[] staticLocations;

  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/**")
      .addResourceLocations(this.staticLocations);
  }
}
