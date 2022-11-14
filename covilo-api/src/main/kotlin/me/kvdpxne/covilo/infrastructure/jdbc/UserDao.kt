package me.kvdpxne.covilo.infrastructure.jdbc

import me.kvdpxne.covilo.BIRTH_DATE_COLUMN
import me.kvdpxne.covilo.CREATED_DATE_COLUMN
import me.kvdpxne.covilo.EMAIL_COLUMN
import me.kvdpxne.covilo.ENABLED_COLUMN
import me.kvdpxne.covilo.FIRST_NAME_COLUMN
import me.kvdpxne.covilo.IDENTIFIER_COLUMN
import me.kvdpxne.covilo.LAST_MODIFIED_DATE_COLUMN
import me.kvdpxne.covilo.LAST_NAME_COLUMN
import me.kvdpxne.covilo.PASSWORD_COLUMN
import me.kvdpxne.covilo.USER_TABLE
import me.kvdpxne.covilo.birthDate
import me.kvdpxne.covilo.createdDate
import me.kvdpxne.covilo.domain.models.User
import me.kvdpxne.covilo.domain.models.Users
import me.kvdpxne.covilo.domain.repositories.UserRepository
import me.kvdpxne.covilo.email
import me.kvdpxne.covilo.enabled
import me.kvdpxne.covilo.firstName
import me.kvdpxne.covilo.identifier
import me.kvdpxne.covilo.lastModifiedDate
import me.kvdpxne.covilo.lastName
import me.kvdpxne.covilo.password
import me.kvdpxne.covilo.utils.sql.ResultSetParser
import me.kvdpxne.covilo.utils.sql.SqlCallBuilder
import me.kvdpxne.covilo.utils.sql.SqlDeleteBuilder
import me.kvdpxne.covilo.utils.sql.SqlInsertBuilder
import me.kvdpxne.covilo.utils.sql.sqlColumnArrayOf
import org.springframework.dao.DataAccessException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.RowCountCallbackHandler
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.sql.ResultSet
import java.sql.SQLException
import java.util.UUID

/**
 * Shortcut to the current table.
 */
private val TABLE: String
  get() = USER_TABLE

@Component
object UserMapper : AccountRowMapper<User> {

  @Throws(SQLException::class)
  override fun mapRow(result: ResultSet, row: Int): User {
    val parser = ResultSetParser(TABLE, result)

    // Parse the entity identifier
    val identifier = parser.identifier()

    // Parse remaining fields
    val email = parser.email()
    val password = parser.password()
    val firstName = parser.firstName()
    val lastName = parser.lastName()
    val birthDate = parser.birthDate()
    val createdDate = parser.createdDate()
    val lastModifiedDate = parser.lastModifiedDate()
    val enabled = parser.enabled()

    // Initialize
    return User(
      identifier = identifier,
      email = email,
      password = password,
      firstName = firstName,
      lastName = lastName,
      birthDate = birthDate,
      createdDate = createdDate,
      lastModifiedDate = lastModifiedDate,
      enabled = enabled
    )
  }
}

@Component
class UserDao(
  private val operations: NamedParameterJdbcOperations
) : UserRepository {

  companion object {

    internal val COLUMNS = sqlColumnArrayOf(
      TABLE,
      IDENTIFIER_COLUMN,
      EMAIL_COLUMN,
      PASSWORD_COLUMN,
      FIRST_NAME_COLUMN,
      LAST_NAME_COLUMN,
      BIRTH_DATE_COLUMN,
      CREATED_DATE_COLUMN,
      LAST_MODIFIED_DATE_COLUMN,
      ENABLED_COLUMN
    )
  }

  /**
   * Shortcut to the most used query builder based on SELECT query.
   */
  private val callBuilder: SqlCallBuilder
    get() = SqlCallBuilder().select(COLUMNS).from(TABLE)

  init {
    val currentColumnArray = RefreshTokenDao.COLUMNS
    RefreshTokenDao.COLUMNS = sqlColumnArrayOf(
      currentColumnArray.table,
      *currentColumnArray.extend(),
      *COLUMNS.extend()
    )
  }

  @Throws(DataAccessException::class)
  private fun findByAny(column: String, key: Any): User? {
    val builder = callBuilder.where(column, key)
    val parameters = builder.parameters
    val query = builder.build()
    return runCatching {
      operations.queryForObject(
        query,
        parameters,
        UserMapper
      )
    }.onFailure {
      if (it is EmptyResultDataAccessException) {
        return null
      }
    }.getOrThrow()
  }

  override fun findByIdentifier(identifier: UUID): User? {
    return findByAny(IDENTIFIER_COLUMN, identifier)
  }

  override fun findByEmail(email: String): User? {
    return findByAny(EMAIL_COLUMN, email)
  }

  override fun findAll(): Users {
    val query = callBuilder.build()
    return operations.query(
      query,
      UserMapper
    )
  }

  @Transactional
  override fun insert(user: User) {
    operations.jdbcOperations.update(
      SqlInsertBuilder(TABLE).also {
        it[IDENTIFIER_COLUMN] = user.identifier
        it[EMAIL_COLUMN] = user.email
        it[PASSWORD_COLUMN] = user.password
        it[FIRST_NAME_COLUMN] = user.firstName
        it[LAST_NAME_COLUMN] = user.lastName
        it[BIRTH_DATE_COLUMN] = user.birthDate
        it[CREATED_DATE_COLUMN] = user.createdDate
        it[LAST_MODIFIED_DATE_COLUMN] = user.lastModifiedDate
        it[ENABLED_COLUMN] = user.enabled
      }.build()
    )
  }

  @Transactional
  override fun update(user: User) {
    TODO("Not yet implemented")
  }

  @Transactional
  override fun delete(identifier: UUID) {
    val builder = SqlDeleteBuilder(TABLE).where(IDENTIFIER_COLUMN, identifier)
    val parameters = builder.parameters
    val query = builder.build()
    operations.update(
      query,
      parameters
    )
  }

  @Transactional
  override fun deleteAll() {
    val query = SqlDeleteBuilder(TABLE).build()
    operations.jdbcOperations.update(query)
  }

  override fun count(): Int {
    val counter = RowCountCallbackHandler()
    val query = SqlCallBuilder().count().from(TABLE).build()
    operations.jdbcOperations.query(query, counter)
    return counter.rowCount
  }
}