package me.kvdpxne.covilo.infrastructure.jwt

data class RefreshTokenRenewResponse(
  val identifier: String,
  val token: String
)