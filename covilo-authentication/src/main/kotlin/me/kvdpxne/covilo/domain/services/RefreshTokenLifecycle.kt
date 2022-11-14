package me.kvdpxne.covilo.domain.services

import me.kvdpxne.covilo.domain.models.Account
import me.kvdpxne.covilo.domain.models.RefreshToken
import me.kvdpxne.covilo.domain.repositories.AccountRepository
import me.kvdpxne.covilo.domain.repositories.RefreshTokenRepository
import me.kvdpxne.covilo.infrastructure.jwt.JwtConfiguration
import me.kvdpxne.covilo.utils.logger
import org.slf4j.Logger
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID

@Service("basic-refresh-token-lifecycle")
class RefreshTokenLifecycle(
  private val jwtConfiguration: JwtConfiguration,
  private val accountRepository: AccountRepository,
  private val refreshTokenRepository: RefreshTokenRepository
) {

  companion object {
    private val logger: Logger by logger()
  }

  fun find(token: String): RefreshToken {
    return refreshTokenRepository.findByToken(token)!!
  }

  fun create(account: Account): RefreshToken {
    val existsAccount =
      accountRepository.findByEmail(account.email) ?: throw RuntimeException("")


    val token = UUID.randomUUID().toString()
    val expiration = Instant.now().plusMillis(jwtConfiguration.expirationTime)
    val refreshToken = RefreshToken(
      token = token,
      expiration = expiration,
      account = existsAccount
    )
    refreshTokenRepository.insert(refreshToken)
    return refreshToken
  }

  fun delete(identifier: Account) {
    val result = refreshTokenRepository.deleteByAccount(identifier)
  }
}