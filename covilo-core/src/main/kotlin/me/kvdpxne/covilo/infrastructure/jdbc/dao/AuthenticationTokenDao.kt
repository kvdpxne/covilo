package me.kvdpxne.covilo.infrastructure.jdbc.dao

import me.kvdpxne.covilo.domain.models.AuthenticationToken
import me.kvdpxne.covilo.domain.models.AuthenticationTokens
import me.kvdpxne.covilo.domain.persistence.AuthenticationTokenRepository
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_EXPIRATION
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_TOKEN
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_USER
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_AUTHENTICATION_TOKEN
import me.kvdpxne.covilo.infrastructure.jdbc.callback.RowCounterCallbackHandler
import me.kvdpxne.covilo.infrastructure.jdbc.mapping.AuthenticationTokenMapper
import me.kvdpxne.covilo.util.sql.SqlCallBuilder
import me.kvdpxne.covilo.util.sql.SqlDeleteBuilder
import me.kvdpxne.covilo.util.sql.SqlInsertBuilder
import me.kvdpxne.covilo.util.sql.sqlColumnArrayOf
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Component
class AuthenticationTokenDao(
  private val operations: NamedParameterJdbcOperations
) : AuthenticationTokenRepository {

  companion object {

    /**
     * Shortcut to the current table.
     */
    private val TABLE: String
      get() = TABLE_AUTHENTICATION_TOKEN

    internal val COLUMNS = sqlColumnArrayOf(
      TABLE,
      COLUMN_IDENTIFIER,
      COLUMN_TOKEN,
      COLUMN_EXPIRATION,
      COLUMN_USER
    )
  }

  /**
   * Shortcut to the most used query builder based on SELECT query.
   */
  private val callBuilder: SqlCallBuilder
    get() = SqlCallBuilder().select(COLUMNS).from(TABLE)

  private fun findByAny(column: String, any: Any): AuthenticationToken? {
    val builder = callBuilder.where(column, any)
    val parameters = builder.parameters
    return runCatching {
      operations.queryForObject(
        builder.build(),
        parameters,
        AuthenticationTokenMapper
      )
    }.getOrNull()
  }

  override fun findByIdentifier(identifier: UUID): AuthenticationToken? {
    return findByAny(COLUMN_IDENTIFIER, identifier)
  }

  override fun findByToken(token: String): AuthenticationToken? {
    return findByAny(COLUMN_TOKEN, token)
  }

  override fun findAll(): AuthenticationTokens {
    val query = callBuilder.build()
    return operations.query(
      query,
      AuthenticationTokenMapper
    )
  }

  @Transactional
  override fun insert(authenticationToken: AuthenticationToken) {
    val builder = SqlInsertBuilder(TABLE)
    builder[COLUMN_IDENTIFIER] = authenticationToken.identifier
    builder[COLUMN_TOKEN] = authenticationToken.token
    builder[COLUMN_EXPIRATION] = authenticationToken.expiration
    builder[COLUMN_USER] = authenticationToken.user.identifier
    val query = builder.build()
    operations.jdbcOperations.update(query)
  }

  @Transactional
  override fun update(authenticationToken: AuthenticationToken) {
    TODO("Not yet implemented")
  }

  @Transactional
  override fun delete(identifier: UUID) {
    val builder = SqlDeleteBuilder(TABLE).where(COLUMN_IDENTIFIER, identifier)
    val query = builder.build()
    operations.update(
      query,
      builder.parameters
    )
  }

  @Transactional
  override fun deleteAll() {
    val query = SqlDeleteBuilder(TABLE).build()
    operations.jdbcOperations.update(query)
  }

  override fun count(): Int {
    val counter = RowCounterCallbackHandler()
    val query = SqlCallBuilder().count().from(TABLE).build()
    operations.query(query, counter)
    return counter.count
  }
}