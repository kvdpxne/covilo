package me.kvdpxne.covilo.infrastructure.security;

import me.kvdpxne.covilo.CoviloProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsSourceConfiguration {

  @Bean
  public CorsConfigurationSource corsConfigurationSource(final CoviloProperties properties) {
    // Initializes a new CorsConfiguration instance with no cross-origin
    // requests allowed by default and then allows default/built-in requests
    // to be used.
    final var configuration = new CorsConfiguration().applyPermitDefaultValues();

    // Overwrites the previously set default values with those that the
    // application will use.
    configuration.setAllowedMethods(List.of(CorsConfiguration.ALL));
    configuration.setAllowCredentials(true);

    // Changes permission to origin requests from all available sources primary
    // to origin stored in the configuration file of the application.
    configuration.setAllowedOrigins(properties.getAllowedOrigins());

    //
    final var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    //
    return source;
  }
}
