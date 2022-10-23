package me.kvdpxne.covilo.infrastructure.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.time.Instant
import java.time.temporal.ChronoUnit

@Configuration
class JwtConfiguration(
  @Value("\${jwt.secret}") val secret: String,
  @Value("\${jwt.experience}") val expirationTime: Long
) {

  private val algorithm: Algorithm
    get() = Algorithm.HMAC256(secret)

  private val expiration: Instant
    get() = Instant.now().plus(expirationTime, ChronoUnit.MINUTES)

  fun createToken(email: String): String {
    return JWT.create()
      .withIssuer("Covilo")
      .withSubject(email)
      .withExpiresAt(expiration)
      .sign(algorithm)
  }

  fun decodeToken(token: String): String {
    val verifier = JWT.require(algorithm).withIssuer("Covilo").build()
    val decodedToken = verifier.verify(token.trim())
    return decodedToken.subject
  }
}