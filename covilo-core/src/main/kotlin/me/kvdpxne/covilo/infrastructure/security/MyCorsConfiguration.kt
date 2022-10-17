package me.kvdpxne.covilo.infrastructure.security

import me.kvdpxne.covilo.ApplicationSettings
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Conditional
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import kotlin.time.Duration.Companion.minutes

@Configuration
class MyCorsConfiguration @Autowired constructor(
  private val settings: ApplicationSettings
) {

  @Bean(name = ["corsConfigurationSource"], autowireCandidate = false)
  @Conditional
  fun getCorsConfigurationSource(): CorsConfigurationSource =
    UrlBasedCorsConfigurationSource().apply {
      registerCorsConfiguration(
        "/**",
        CorsConfiguration().applyPermitDefaultValues().apply {
          allowedOrigins = settings.origins
          allowedMethods = HttpMethod.values().map { it.name }
          allowCredentials = true
          maxAge = 5.minutes.inWholeSeconds
        }
      )
    }
}