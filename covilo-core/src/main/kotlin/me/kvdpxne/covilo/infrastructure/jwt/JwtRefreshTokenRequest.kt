package me.kvdpxne.covilo.infrastructure.jwt

data class JwtRefreshTokenRequest(
  val refreshedToken: String
)