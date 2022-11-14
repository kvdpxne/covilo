package me.kvdpxne.covilo.infrastructure.security.authentication

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration("basic-authentication-configuration")
class BasicAuthenticationConfiguration(
  authenticationConfiguration: AuthenticationConfiguration,
  userDetailsService: UserDetailsService,
  passwordEncoder: PasswordEncoder
) {

  @get:Throws(Exception::class)
  @get:Bean
  val authenticationManager: AuthenticationManager by lazy {
    authenticationConfiguration.authenticationManager
  }

  @get:Bean
  val authenticationProvider: AuthenticationProvider by lazy {
    DaoAuthenticationProvider().apply {
      setUserDetailsService { userDetailsService.loadUserByUsername(it) }
      setPasswordEncoder(passwordEncoder)
    }
  }
}