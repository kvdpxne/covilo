package me.kvdpxne.covilo.domain.persistence

import me.kvdpxne.covilo.domain.models.AuthenticationToken
import me.kvdpxne.covilo.domain.models.AuthenticationTokens
import java.util.UUID

interface AuthenticationTokenRepository {

  fun findByIdentifier(identifier: UUID): AuthenticationToken?

  fun findByToken(token: String): AuthenticationToken?

  fun findAll(): AuthenticationTokens

  fun insert(authenticationToken: AuthenticationToken)

  fun update(authenticationToken: AuthenticationToken)

  fun delete(identifier: UUID)

  fun deleteAll()

  fun count(): Int
}