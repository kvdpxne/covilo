package me.kvdpxne.covilo.infrastructure.security

import me.kvdpxne.covilo.domain.models.ROLE_ADMINISTRATOR
import me.kvdpxne.covilo.domain.models.ROLE_USER
import me.kvdpxne.covilo.infrastructure.jwt.JwtConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfiguration(
  private val jwtConfiguration: JwtConfiguration,
  private val userDetailsService: MyUserDetailsService,
  private val authenticationManager: AuthenticationManager,
  private val authenticationSuccessHandler: AuthenticationSuccessHandler,
) {

  @Bean
  fun filterChain(http: HttpSecurity): SecurityFilterChain {
    return http
      .headers { it.cacheControl().disable() }
      .cors { }
      .csrf { it.disable() }
      .authorizeHttpRequests {
        it.mvcMatchers("/admin").hasRole(ROLE_ADMINISTRATOR)
        it.mvcMatchers("/user").hasRole(ROLE_USER)
        it.anyRequest().permitAll()
      }
      .addFilter(authenticationFilter())
      .addFilter(authorizationFilter())
      .sessionManagement { it.sessionCreationPolicy(STATELESS) }
      .httpBasic(Customizer.withDefaults())
      .build()
  }

  @Bean
  fun authenticationFilter(): AuthenticationFilter {
    val filter = AuthenticationFilter()
    filter.setAuthenticationManager(authenticationManager)
    filter.setAuthenticationSuccessHandler(authenticationSuccessHandler)
    return filter
  }

  @Bean
  fun authorizationFilter(): AuthorizationFilter {
    return AuthorizationFilter(
      jwtConfiguration, userDetailsService, authenticationManager
    )
  }
}