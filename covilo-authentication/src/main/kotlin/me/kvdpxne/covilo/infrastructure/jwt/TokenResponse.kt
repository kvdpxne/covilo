package me.kvdpxne.covilo.infrastructure.jwt

import java.util.UUID

data class TokenResponse(
  val token: String,
  val refreshToken: String,
  val account: UUID,
  val email: String,
  val roles: Collection<String>
)