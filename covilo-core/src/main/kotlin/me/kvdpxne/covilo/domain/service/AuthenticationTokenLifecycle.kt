package me.kvdpxne.covilo.domain.service

import me.kvdpxne.covilo.domain.models.AuthenticationToken
import me.kvdpxne.covilo.domain.models.User
import me.kvdpxne.covilo.domain.persistence.AuthenticationTokenRepository
import me.kvdpxne.covilo.infrastructure.jwt.JwtConfiguration
import me.kvdpxne.covilo.infrastructure.jwt.JwtRefreshTokenRequest
import me.kvdpxne.covilo.infrastructure.jwt.JwtResponse
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AuthenticationTokenLifecycle(
  private val jwtConfiguration: JwtConfiguration,
  private val authenticationTokenRepository: AuthenticationTokenRepository
) {

  fun create(user: User): AuthenticationToken {
    val token = jwtConfiguration.createToken(user.email)
    val expiration = LocalDateTime.now().plusMinutes(jwtConfiguration.expirationTime)
    val authenticationToken = AuthenticationToken(
      token = token, expiration = expiration, user = user
    )
    authenticationTokenRepository.insert(authenticationToken)
    return authenticationToken
  }

  // TODO fix
  fun refresh(request: JwtRefreshTokenRequest): JwtResponse {
    val token = request.refreshedToken
    val authenticationToken = authenticationTokenRepository.findByToken(token)!!
    val expiration = authenticationToken.expiration
    val current = LocalDateTime.now()
    if (expiration.isBefore(current)) {
      authenticationTokenRepository.delete(authenticationToken.identifier)
      throw RuntimeException("Token $token was expired!")
    }
    val jsonToken = jwtConfiguration.createToken(authenticationToken.user.email)
    authenticationToken.expiration = current.plusMinutes(jwtConfiguration.expirationTime)
    authenticationTokenRepository.update(authenticationToken)
    return JwtResponse(jsonToken, authenticationToken.token)
  }
}