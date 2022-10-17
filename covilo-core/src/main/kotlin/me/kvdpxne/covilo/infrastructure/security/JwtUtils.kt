package me.kvdpxne.covilo.infrastructure.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Value
import java.util.Date

object JwtUtils {

  @Value("\${jwt_secret}")
  private val secret: String = "test"

  @Throws(IllegalArgumentException::class, JWTCreationException::class)
  fun generateToken(email: String): String {
    return JWT.create()
      .withSubject("User Details")
      .withClaim("email", email)
      .withIssuedAt(Date())
      .withIssuer("Covilo")
      .sign(Algorithm.HMAC256(secret))
  }

  @Throws(JWTVerificationException::class)
  fun validateTokenAndRetrieveSubject(token: String?): String? {
    val verifier: JWTVerifier = JWT.require(Algorithm.HMAC256(secret))
      .withSubject("User Details")
      .withIssuer("Covilo")
      .build()
    val jwt: DecodedJWT = verifier.verify(token)
    return jwt.getClaim("email").asString()
  }
}