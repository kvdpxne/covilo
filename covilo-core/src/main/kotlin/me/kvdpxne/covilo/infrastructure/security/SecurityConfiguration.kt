package me.kvdpxne.covilo.infrastructure.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfiguration {

  @Bean
  fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
    return http
      .cors { }
      .csrf { it.disable() }
      .authorizeRequests {

        it.mvcMatchers(
          "/api/v1/crime",
          "/api/v1/location"
        ).permitAll()

      }
      .formLogin(Customizer.withDefaults())
      .build()
  }
}