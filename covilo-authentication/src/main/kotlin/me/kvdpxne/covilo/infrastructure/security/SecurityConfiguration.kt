package me.kvdpxne.covilo.infrastructure.security

import me.kvdpxne.covilo.infrastructure.jwt.JwtConfiguration
import me.kvdpxne.covilo.infrastructure.security.authentication.AuthenticationFilter
import me.kvdpxne.covilo.infrastructure.security.authentication.BasicUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration(
  private val jwtConfiguration: JwtConfiguration,
  private val userDetailsService: BasicUserDetailsService
) {

  @Bean(name = ["securityFilterChain"])
  fun getSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
    return http
      .headers { it.cacheControl().disable() }
      .cors { }
      .csrf { it.disable() }
      .authorizeHttpRequests {

        it.mvcMatchers(
          "/",
          "/index.html"
        ).permitAll()



        it.mvcMatchers("/api/**").permitAll()
      }
      .addFilterBefore(
        AuthenticationFilter(jwtConfiguration, userDetailsService),
        UsernamePasswordAuthenticationFilter::class.java
      )
      .sessionManagement {
        it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      }
      .build()
  }
}