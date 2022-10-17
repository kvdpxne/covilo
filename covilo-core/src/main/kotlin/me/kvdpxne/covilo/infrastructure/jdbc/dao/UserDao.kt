package me.kvdpxne.covilo.infrastructure.jdbc.dao

import me.kvdpxne.covilo.domain.models.User
import me.kvdpxne.covilo.domain.models.Users
import me.kvdpxne.covilo.domain.persistence.UserRepository
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_BIRTH_DATE
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_CREATED_DATE
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_EMAIL
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_FIRST_NAME
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_LAST_MODIFIED_DATE
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_LAST_NAME
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_PASSWORD
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_USER
import me.kvdpxne.covilo.infrastructure.jdbc.callback.RowCounterCallbackHandler
import me.kvdpxne.covilo.infrastructure.jdbc.mapping.UserMapper
import me.kvdpxne.covilo.util.sql.SqlCallBuilder
import me.kvdpxne.covilo.util.sql.SqlDeleteBuilder
import me.kvdpxne.covilo.util.sql.SqlInsertBuilder
import me.kvdpxne.covilo.util.sql.sqlColumnArrayOf
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Component
class UserDao @Autowired(required = true) constructor(
  private val operations: NamedParameterJdbcOperations
) : UserRepository {

  companion object {

    /**
     * Shortcut to the current table.
     */
    private val TABLE: String
      get() = TABLE_USER

    internal val COLUMNS = sqlColumnArrayOf(
      TABLE,
      COLUMN_IDENTIFIER,
      COLUMN_EMAIL,
      COLUMN_PASSWORD,
      COLUMN_FIRST_NAME,
      COLUMN_LAST_NAME,
      COLUMN_BIRTH_DATE,
      COLUMN_CREATED_DATE,
      COLUMN_LAST_MODIFIED_DATE
    )
  }

  /**
   * Shortcut to the most used query builder based on SELECT query.
   */
  private val callBuilder: SqlCallBuilder
    get() = SqlCallBuilder().select(COLUMNS).from(TABLE)

  override fun findByIdentifier(identifier: UUID): User? {
    val builder = callBuilder.where(COLUMN_IDENTIFIER, identifier)
    val query = builder.build()
    return runCatching {
      operations.queryForObject(
        query,
        builder.parameters,
        UserMapper
      )
    }.getOrNull()
  }

  override fun findByEmail(email: String): User? {
    val builder = callBuilder.where(COLUMN_EMAIL, email)
    val query = builder.build()
    return runCatching {
      operations.queryForObject(
        query,
        builder.parameters,
        UserMapper
      )
    }.getOrNull()
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
    val builder = SqlInsertBuilder(TABLE)
    builder[COLUMN_IDENTIFIER] = user.identifier
    builder[COLUMN_EMAIL] = user.email
    builder[COLUMN_PASSWORD] = user.password
    builder[COLUMN_FIRST_NAME] = user.firstName
    builder[COLUMN_LAST_NAME] = user.lastName
    builder[COLUMN_BIRTH_DATE] = user.birthDate
    builder[COLUMN_CREATED_DATE] = user.createdDate
    builder[COLUMN_LAST_MODIFIED_DATE] = user.lastModifiedDate
    val query = builder.build()
    operations.jdbcOperations.update(query)
  }

  @Transactional
  override fun update(user: User) {
    TODO("Not yet implemented")
  }

  @Transactional
  override fun delete(identifier: UUID) {
    val builder = SqlDeleteBuilder(TABLE).where(COLUMN_IDENTIFIER, identifier)
    operations.update(
      builder.build(),
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
    operations.jdbcOperations.query(query, counter)
    return counter.count
  }
}