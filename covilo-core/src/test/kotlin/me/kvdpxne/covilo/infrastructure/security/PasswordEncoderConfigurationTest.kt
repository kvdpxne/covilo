package me.kvdpxne.covilo.infrastructure.security

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootTest
class PasswordEncoderConfigurationTest @Autowired(required = true) constructor(
  private val encoder: PasswordEncoder
) {

  @Test
  fun test() {
    val password = "P@ssw0rd"
    val encodedPassword = encoder.encode(password)
    val isMatch = encoder.matches(password, encodedPassword)
    assertTrue(isMatch)
  }
}