package me.kvdpxne.covilo.infrastructure.jdbc.dao

import me.kvdpxne.covilo.domain.models.CrimePerpetrator
import me.kvdpxne.covilo.domain.models.CrimePerpetrators
import me.kvdpxne.covilo.domain.persistence.CrimePerpetratorRepository
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_AGE
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_FIRST_NAME
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_IS_CAUGHT
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_LAST_NAME
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_CRIME_PERPETRATOR
import me.kvdpxne.covilo.infrastructure.jdbc.callback.RowCounterCallbackHandler
import me.kvdpxne.covilo.infrastructure.jdbc.mapping.CrimePerpetratorMapper
import me.kvdpxne.covilo.util.sql.SqlCallBuilder
import me.kvdpxne.covilo.util.sql.SqlDeleteBuilder
import me.kvdpxne.covilo.util.sql.SqlInsertBuilder
import me.kvdpxne.covilo.util.sql.sqlColumnArrayOf
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

/**
 * @see CrimePerpetratorRepository
 */
@Component
class CrimePerpetratorDao @Autowired(required = true) constructor(
  private val operations: NamedParameterJdbcOperations
) : CrimePerpetratorRepository {

  companion object {

    /**
     * Shortcut to the current table.
     */
    private val TABLE: String
      get() = TABLE_CRIME_PERPETRATOR

    internal val COLUMNS = sqlColumnArrayOf(
      TABLE,
      COLUMN_IDENTIFIER,
      COLUMN_FIRST_NAME,
      COLUMN_LAST_NAME,
      COLUMN_AGE,
      COLUMN_IS_CAUGHT
    )
  }

  /**
   * Shortcut to the most used query builder based on SELECT query.
   */
  private val callBuilder: SqlCallBuilder
    get() = SqlCallBuilder().select(COLUMNS).from(TABLE)

  override fun findByIdentifier(identifier: UUID): CrimePerpetrator? {
    val builder = callBuilder.where(COLUMN_IDENTIFIER, identifier)
    val query = builder.build()
    return runCatching {
      operations.queryForObject(
        query,
        builder.parameters,
        CrimePerpetratorMapper
      )
    }.getOrNull()
  }

  override fun findAll(): CrimePerpetrators {
    val query = callBuilder.build()
    return operations.query(
      query,
      CrimePerpetratorMapper
    )
  }

  @Transactional
  override fun insert(perpetrator: CrimePerpetrator) {
    val builder = SqlInsertBuilder(TABLE)
    builder[COLUMN_IDENTIFIER] = perpetrator.identifier
    builder[COLUMN_FIRST_NAME] = perpetrator.firstName
    builder[COLUMN_LAST_NAME] = perpetrator.lastName
    builder[COLUMN_AGE] = perpetrator.age
    builder[COLUMN_IS_CAUGHT] = perpetrator.isCaught
    val query = builder.build()
    operations.jdbcOperations.update(query)
  }

  @Transactional
  override fun update(perpetrator: CrimePerpetrator) {
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