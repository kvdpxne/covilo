package me.kvdpxne.covilo.infrastructure.security

import me.kvdpxne.covilo.Settings
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import kotlin.time.Duration.Companion.minutes

@Configuration
class CommonCorsConfiguration {

  @Primary
  @Bean(name = ["corsConfigurationSource"])
  fun getCorsConfigurationSource(settings: Settings): CorsConfigurationSource {
    return UrlBasedCorsConfigurationSource().apply {
      registerCorsConfiguration(
        "/**",
        CorsConfiguration().applyPermitDefaultValues().apply {
          allowedOrigins = settings.origins
          allowedMethods = listOf(CorsConfiguration.ALL)
          allowCredentials = true
          maxAge = 5.minutes.inWholeSeconds
        }
      )
    }
  }
}