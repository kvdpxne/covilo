package me.kvdpxne.covilo.domain.models

import me.kvdpxne.covilo.utils.Recognizable
import java.time.Instant
import java.util.UUID

/**
 *
 */
data class RefreshToken(
  override val identifier: UUID = UUID.randomUUID(),
  val token: String,
  var expiration: Instant,
  val account: Account
) : Recognizable

/**
 *
 */
typealias RefreshTokens = Collection<RefreshToken>
