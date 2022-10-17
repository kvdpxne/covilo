package me.kvdpxne.covilo.infrastructure.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class PasswordEncoderConfiguration {

  @Bean(name = ["passwordEncoder"])
  fun getPasswordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}