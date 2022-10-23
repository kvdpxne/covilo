package me.kvdpxne.covilo.domain.models

import java.time.LocalDateTime
import java.util.UUID

data class AuthenticationToken(
  val identifier: UUID = UUID.randomUUID(),
  val token: String,
  var expiration: LocalDateTime,
  val user: User
)

typealias AuthenticationTokens = Collection<AuthenticationToken>
