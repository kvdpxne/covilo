package me.kvdpxne.covilo.domain.repositories

import me.kvdpxne.covilo.domain.models.Account
import me.kvdpxne.covilo.domain.models.RefreshToken
import me.kvdpxne.covilo.domain.models.RefreshTokens
import java.util.UUID

interface RefreshTokenRepository {

  fun findByIdentifier(identifier: UUID): RefreshToken?

  fun findByToken(token: String): RefreshToken?

  fun findAll(): RefreshTokens

  fun insert(refreshToken: RefreshToken)

  fun update(refreshToken: RefreshToken)

  fun delete(identifier: UUID): Boolean

  fun deleteByAccount(account: Account): Boolean

  fun deleteAll(): Int

  fun count(): Int
}