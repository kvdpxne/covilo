package me.kvdpxne.covilo.infrastructure.jdbc

import me.kvdpxne.covilo.CRIME_CLASSIFICATION_TABLE
import me.kvdpxne.covilo.IDENTIFIER_COLUMN
import me.kvdpxne.covilo.KEY_COLUMN
import me.kvdpxne.covilo.domain.models.CrimeClassification
import me.kvdpxne.covilo.domain.models.CrimeClassifications
import me.kvdpxne.covilo.domain.repositories.CrimeClassificationRepository
import me.kvdpxne.covilo.identifier
import me.kvdpxne.covilo.key
import me.kvdpxne.covilo.utils.sql.ResultSetParser
import me.kvdpxne.covilo.utils.sql.SqlCallBuilder
import me.kvdpxne.covilo.utils.sql.SqlDeleteBuilder
import me.kvdpxne.covilo.utils.sql.SqlInsertBuilder
import me.kvdpxne.covilo.utils.sql.sqlColumnArrayOf
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.RowCountCallbackHandler
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.ResultSet
import java.sql.SQLException
import java.util.UUID

internal object CrimeClassificationMapper : RowMapper<CrimeClassification> {

  @Throws(SQLException::class)
  override fun mapRow(result: ResultSet, row: Int): CrimeClassification {
    val parser = ResultSetParser(CRIME_CLASSIFICATION_TABLE, result)

    // Parse the entity identifier
    val identifier = parser.identifier()

    // Parse remaining fields
    val key = parser.key()

    // Initialize
    return CrimeClassification(
      identifier = identifier,
      key = key
    )
  }
}

/**
 * @see CrimeClassificationRepository
 */
@Component
class CrimeClassificationDao(
  private val operations: NamedParameterJdbcOperations
) : CrimeClassificationRepository {

  companion object {

    /**
     * Shortcut to the current table.
     */
    private val TABLE: String
      get() = CRIME_CLASSIFICATION_TABLE

    internal val COLUMNS = sqlColumnArrayOf(
      TABLE,
      IDENTIFIER_COLUMN,
      KEY_COLUMN
    )
  }

  /**
   * Shortcut to the most used query builder based on SELECT query.
   */
  private val callBuilder: SqlCallBuilder
    get() = SqlCallBuilder().select(COLUMNS).from(TABLE)

  override fun findByIdentifier(identifier: UUID): CrimeClassification? {
    val builder = callBuilder.where(IDENTIFIER_COLUMN, identifier)
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
    builder[IDENTIFIER_COLUMN] = classification.identifier
    builder[KEY_COLUMN] = classification.key
    val query = builder.build()
    operations.jdbcOperations.update(query)
  }

  @Transactional
  override fun update(classification: CrimeClassification) {
    TODO("Not yet implemented")
  }

  @Transactional
  override fun delete(identifier: UUID) {
    val builder = SqlDeleteBuilder(TABLE).where(IDENTIFIER_COLUMN, identifier)
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
    val counter = RowCountCallbackHandler()
    val query = SqlCallBuilder().count().from(TABLE).build()
    operations.query(query, counter)
    return counter.rowCount
  }
}
