package me.kvdpxne.covilo.infrastructure.jdbc

import me.kvdpxne.covilo.ACCOUNT_COLUMN
import me.kvdpxne.covilo.EXPIRATION_COLUMN
import me.kvdpxne.covilo.IDENTIFIER_COLUMN
import me.kvdpxne.covilo.REFRESH_TOKEN_TABLE
import me.kvdpxne.covilo.TOKEN_COLUMN
import me.kvdpxne.covilo.USER_TABLE
import me.kvdpxne.covilo.domain.models.Account
import me.kvdpxne.covilo.domain.models.RefreshToken
import me.kvdpxne.covilo.domain.models.RefreshTokens
import me.kvdpxne.covilo.domain.repositories.RefreshTokenRepository
import me.kvdpxne.covilo.expiration
import me.kvdpxne.covilo.identifier
import me.kvdpxne.covilo.token
import me.kvdpxne.covilo.utils.sql.ResultSetParser
import me.kvdpxne.covilo.utils.sql.SqlCallBuilder
import me.kvdpxne.covilo.utils.sql.SqlDeleteBuilder
import me.kvdpxne.covilo.utils.sql.SqlInsertBuilder
import me.kvdpxne.covilo.utils.sql.SqlRelation
import me.kvdpxne.covilo.utils.sql.sqlColumnArrayOf
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.RowCountCallbackHandler
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.sql.ResultSet
import java.util.UUID

/**
 * Shortcut to the current table.
 */
private val TABLE: String
  get() = REFRESH_TOKEN_TABLE

@Component
class RefreshTokenMapper(
  private val accountRowMapper: AccountRowMapper<out Account>
) : RowMapper<RefreshToken> {

  override fun mapRow(result: ResultSet, row: Int): RefreshToken {
    val parser = ResultSetParser(TABLE, result)

    // Parse the entity identifier
    val identifier = parser.identifier()

    // Parse relational entities to the entity
    val account = accountRowMapper.mapRow(result, row) as Account

    // Parse remaining fields
    val token = parser.token()
    val expiration = parser.expiration()

    // Initialize
    return RefreshToken(
      identifier = identifier,
      token = token,
      expiration = expiration,
      account = account
    )
  }
}

@Component
class RefreshTokenDao(
  private val namedParameterOperations: NamedParameterJdbcOperations,
  private val refreshTokenMapper: RefreshTokenMapper
) : RefreshTokenRepository {

  companion object {

    var COLUMNS = sqlColumnArrayOf(
      TABLE,
      IDENTIFIER_COLUMN,
      TOKEN_COLUMN,
      EXPIRATION_COLUMN,
      ACCOUNT_COLUMN
    )

    internal val RELATIONS = arrayOf(
      SqlRelation(USER_TABLE, TABLE, ACCOUNT_COLUMN)
    )
  }

  /**
   * Shortcut to the most used query builder based on SELECT query.
   */
  private val callBuilder: SqlCallBuilder
    get() = SqlCallBuilder().select(COLUMNS).from(TABLE).join(RELATIONS)

  /**
   *
   */
  private fun findByAny(column: String, key: Any): RefreshToken? {
    val builder = callBuilder.where(column, key)
    val parameters = builder.parameters
    val query = builder.build()
    return runCatching {
      namedParameterOperations.queryForObject(
        query,
        parameters,
        refreshTokenMapper
      )
    }.onFailure {
      if (it is EmptyResultDataAccessException) {
        return null
      }
    }.getOrNull()
  }

  override fun findByIdentifier(identifier: UUID): RefreshToken? {
    return findByAny(IDENTIFIER_COLUMN, identifier)
  }

  override fun findByToken(token: String): RefreshToken? {
    return findByAny(TOKEN_COLUMN, token)
  }

  override fun findAll(): RefreshTokens {
    val query = callBuilder.build()
    return namedParameterOperations.query(query, refreshTokenMapper)
  }

  @Transactional
  override fun insert(refreshToken: RefreshToken) {
    val query = SqlInsertBuilder(TABLE).also {
      it[IDENTIFIER_COLUMN] = refreshToken.identifier
      it[TOKEN_COLUMN] = refreshToken.token
      it[EXPIRATION_COLUMN] = refreshToken.expiration
      it[ACCOUNT_COLUMN] = refreshToken.account.identifier
    }.build()
    namedParameterOperations.operations().update(query)
  }

  @Transactional
  override fun update(refreshToken: RefreshToken) {
    TODO("Not yet implemented")
  }

  @Transactional
  override fun delete(identifier: UUID): Boolean {
    val builder = SqlDeleteBuilder(TABLE).where(IDENTIFIER_COLUMN, identifier)
    val parameters = builder.parameters
    val query = builder.build()
    return 0 != namedParameterOperations.update(query, parameters)
  }

  @Transactional
  override fun deleteByAccount(account: Account): Boolean {
    return delete(account.identifier)
  }

  @Transactional
  override fun deleteAll(): Int {
    val query = SqlDeleteBuilder(TABLE).build()
    return namedParameterOperations.operations().update(query)
  }

  override fun count(): Int {
    val counter = RowCountCallbackHandler()
    val query = SqlCallBuilder().count().from(TABLE).build()
    namedParameterOperations.query(query, counter)
    return counter.rowCount
  }
}