package me.kvdpxne.covilo.infrastructure.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
class SecurityConfiguration {

  @Bean
  fun filterChain(http: HttpSecurity): SecurityFilterChain {
    return http
      // by default uses a Bean by the name of corsConfigurationSource
      .cors { }
      .csrf { it.disable() }
      .authorizeRequests {

        it.mvcMatchers(
          "/api/v1/crime",
          "/api/v1/location"
        ).permitAll()

      }
      .httpBasic {

      }
      .build()
  }
}