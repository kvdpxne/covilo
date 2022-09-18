package me.kvdpxne.covilo.infrastructure.jdbc.dao

import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_CREATED_DATE
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_EMAIL
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_FIRST_NAME
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_LAST_MODIFIED_DATE
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_LAST_NAME
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_PASSWORD
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_USER
import me.kvdpxne.covilo.domain.model.User
import me.kvdpxne.covilo.domain.model.Users
import me.kvdpxne.covilo.domain.persistence.UserRepository
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_CRIME_CLASSIFICATION
import me.kvdpxne.covilo.infrastructure.jdbc.callback.RowCounterCallback
import me.kvdpxne.covilo.infrastructure.jdbc.mapping.UserMapper
import me.kvdpxne.covilo.util.sql.QueryBuilder
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
     *
     */
    val FIELD_ARRAY = arrayOf(
      "$TABLE_USER.$COLUMN_IDENTIFIER",
      "$TABLE_USER.$COLUMN_EMAIL",
      "$TABLE_USER.$COLUMN_PASSWORD",
      "$TABLE_USER.$COLUMN_FIRST_NAME",
      "$TABLE_USER.$COLUMN_LAST_NAME",
      "$TABLE_USER.$COLUMN_CREATED_DATE",
      "$TABLE_USER.$COLUMN_LAST_MODIFIED_DATE"
    )
  }

  override fun findByIdentifier(identifier: UUID): User? {
    val stringIdentifier = identifier.toString()
    val query = QueryBuilder()
      .select(*FIELD_ARRAY)
      .from(TABLE_USER)
      .where(COLUMN_IDENTIFIER, stringIdentifier)
      .end()
    return runCatching {
      operations.queryForObject(
        query,
        mapOf(COLUMN_IDENTIFIER to stringIdentifier),
        UserMapper
      )
    }.getOrNull()
  }

  override fun findByName(name: String): User? {
    TODO("Not yet implemented")
  }

  override fun findAll(): Users {
    val query = QueryBuilder()
      .select(*FIELD_ARRAY)
      .from(TABLE_USER)
      .end()
    return operations.query(
      query,
      UserMapper
    )
  }

  @Transactional
  override fun insert(user: User) {
    TODO("Not yet implemented")
  }

  @Transactional
  override fun update(user: User) {
    TODO("Not yet implemented")
  }

  @Transactional
  override fun delete(identifier: UUID) {
    TODO("Not yet implemented")
  }

  @Transactional
  override fun deleteAll() {
    TODO("Not yet implemented")
  }

  override fun count(): Int {
    val counter = RowCounterCallback()
    val query = QueryBuilder()
      .count(FIELD_ARRAY.first())
      .from(TABLE_USER)
      .end()
    operations.query(query, counter)
    return counter.count
  }
}