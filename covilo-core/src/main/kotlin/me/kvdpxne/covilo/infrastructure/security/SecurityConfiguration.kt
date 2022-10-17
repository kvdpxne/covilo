package me.kvdpxne.covilo.infrastructure.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfigurationSource
import javax.servlet.http.HttpServletResponse

@EnableWebSecurity
class SecurityConfiguration @Autowired constructor(
  private val cors: CorsConfigurationSource,
  private val userDetails: MyUserDetailsService,
  private val filter: JwtOncePerRequestFilter,
) {

  @Bean
  fun filterChain(http: HttpSecurity): SecurityFilterChain = http
    .headers {
      // Headers are set in WebMvcConfiguration
      it.cacheControl().disable()
    }
    .cors {
      it.configurationSource(cors)
    }
    .csrf { it.disable() }
    .authorizeHttpRequests {
      it.mvcMatchers(
        "/api/authentication/",
        "/api/v1/crime/",
        "/api/v1/location/"
      ).permitAll()
    }
    .userDetailsService(userDetails)
    .addFilterBefore(filter, UsernamePasswordAuthenticationFilter::class.java)
    .sessionManagement {
      it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }
    .logout {
      it.logoutUrl("/api/logout")
    }
    .exceptionHandling {
      it.authenticationEntryPoint { _, response, m ->
        response.sendError(
          HttpServletResponse.SC_UNAUTHORIZED,
          m.message
        )
      }
    }
    .build()
}