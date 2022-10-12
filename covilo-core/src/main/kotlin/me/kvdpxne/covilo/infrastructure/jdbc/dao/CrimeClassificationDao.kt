package me.kvdpxne.covilo.infrastructure.jdbc.dao

import me.kvdpxne.covilo.domain.models.CrimeClassification
import me.kvdpxne.covilo.domain.models.CrimeClassifications
import me.kvdpxne.covilo.domain.persistence.CrimeClassificationRepository
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_KEY
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_CRIME_CLASSIFICATION
import me.kvdpxne.covilo.infrastructure.jdbc.callback.RowCounterCallbackHandler
import me.kvdpxne.covilo.infrastructure.jdbc.mapping.CrimeClassificationMapper
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
 * @see CrimeClassificationRepository
 */
@Component
class CrimeClassificationDao @Autowired(required = true) constructor(
  private val operations: NamedParameterJdbcOperations
) : CrimeClassificationRepository {

  companion object {

    /**
     * Shortcut to the current table.
     */
    private val TABLE: String
      get() = TABLE_CRIME_CLASSIFICATION

    internal val COLUMNS = sqlColumnArrayOf(
      TABLE,
      COLUMN_IDENTIFIER,
      COLUMN_KEY
    )
  }

  /**
   * Shortcut to the most used query builder based on SELECT query.
   */
  private val callBuilder: SqlCallBuilder
    get() = SqlCallBuilder().select(COLUMNS).from(TABLE)

  override fun findByIdentifier(identifier: UUID): CrimeClassification? {
    val builder = callBuilder.where(COLUMN_IDENTIFIER, identifier)
    val query = builder.build()
    return runCatching {
      operations.queryForObject(
        query,
        builder.parameters,
        CrimeClassificationMapper
      )
    }.getOrNull()
  }

  override fun findAll(): CrimeClassifications {
    val query = callBuilder.build()
    return operations.query(
      query,
      CrimeClassificationMapper
    )
  }

  @Transactional
  override fun insert(classification: CrimeClassification) {
    val builder = SqlInsertBuilder(TABLE)
    builder[COLUMN_IDENTIFIER] = classification.identifier
    builder[COLUMN_KEY] = classification.key
    val query = builder.build()
    operations.jdbcOperations.update(query)
  }

  @Transactional
  override fun update(classification: CrimeClassification) {
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
