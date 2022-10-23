package me.kvdpxne.covilo.infrastructure.jwt

data class JwtResponse(
  val token: String,
  val refreshedToken: String
)