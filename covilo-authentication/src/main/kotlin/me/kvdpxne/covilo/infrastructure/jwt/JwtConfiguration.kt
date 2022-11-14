package me.kvdpxne.covilo.infrastructure.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.TokenExpiredException
import me.kvdpxne.covilo.ApplicationProperties
import me.kvdpxne.covilo.utils.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.time.Instant
import java.time.temporal.ChronoUnit

@Configuration
class JwtConfiguration(
  private val properties: ApplicationProperties,
  @Value("\${jwt.secret}") val secret: String,
  @Value("\${jwt.experience}") val expirationTime: Long
) {

  companion object {
    private val logger by logger()
  }

  private val algorithm: Algorithm
    get() = Algorithm.HMAC256(secret)

  private val verifier: JWTVerifier
    get() = JWT.require(algorithm).withIssuer(properties.name).build()

  private val expiration: Instant
    get() = Instant.now().plus(expirationTime, ChronoUnit.MINUTES)

  fun create(email: String): String {
    return JWT.create()
      .withIssuer(properties.name)
      .withSubject(email)
      .withExpiresAt(expiration)
      .sign(algorithm)
  }

  fun decode(token: String): String {
    val decodedToken = verifier.verify(token.trim())
    return decodedToken.subject
  }

  fun verify(token: String): Boolean {
    return try {
      verifier.verify(token.trim())
      true
    } catch (exception: TokenExpiredException) {
      logger.error("JWT token is expired ${exception.message}")
      false
    }
  }
}