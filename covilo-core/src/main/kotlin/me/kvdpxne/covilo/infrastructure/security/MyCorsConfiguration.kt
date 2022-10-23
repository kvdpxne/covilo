package me.kvdpxne.covilo.infrastructure.security

import me.kvdpxne.covilo.ApplicationSettings
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import kotlin.time.Duration.Companion.minutes

@Configuration
class MyCorsConfiguration(private val settings: ApplicationSettings) {

  @Bean("corsConfigurationSource")
  fun getCorsConfiguration(): UrlBasedCorsConfigurationSource {
    val configuration = CorsConfiguration().applyPermitDefaultValues().apply {
      allowedOrigins = settings.origins
      allowedMethods = HttpMethod.values().map { it.name }
      allowCredentials = true
      maxAge = 5.minutes.inWholeSeconds
    }
    return UrlBasedCorsConfigurationSource().apply {
      registerCorsConfiguration("/**", configuration)
    }
  }
}