package me.kvdpxne.covilo.infrastructure.jdbc

import me.kvdpxne.covilo.AGE_COLUMN
import me.kvdpxne.covilo.CAUGHT_COLUMN
import me.kvdpxne.covilo.CRIME_PERPETRATOR_TABLE
import me.kvdpxne.covilo.FIRST_NAME_COLUMN
import me.kvdpxne.covilo.IDENTIFIER_COLUMN
import me.kvdpxne.covilo.LAST_NAME_COLUMN
import me.kvdpxne.covilo.age
import me.kvdpxne.covilo.caught
import me.kvdpxne.covilo.domain.models.CrimePerpetrator
import me.kvdpxne.covilo.domain.models.CrimePerpetrators
import me.kvdpxne.covilo.domain.repositories.CrimePerpetratorRepository
import me.kvdpxne.covilo.firstName
import me.kvdpxne.covilo.identifier
import me.kvdpxne.covilo.lastName
import me.kvdpxne.covilo.utils.sql.ResultSetParser
import me.kvdpxne.covilo.utils.sql.SqlCallBuilder
import me.kvdpxne.covilo.utils.sql.SqlDeleteBuilder
import me.kvdpxne.covilo.utils.sql.SqlInsertBuilder
import me.kvdpxne.covilo.utils.sql.sqlColumnArrayOf
import org.springframework.jdbc.core.RowCountCallbackHandler
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.ResultSet
import java.sql.SQLException
import java.util.UUID

internal object CrimePerpetratorMapper : RowMapper<CrimePerpetrator> {

  @Throws(SQLException::class)
  override fun mapRow(result: ResultSet, row: Int): CrimePerpetrator {
    val parser = ResultSetParser(CRIME_PERPETRATOR_TABLE, result)

    // Parse the entity identifier
    val identifier = parser.identifier()

    // Parse remaining fields
    val firstName = parser.firstName()
    val lastName = parser.lastName()
    val age = parser.age()
    val isCaught = parser.caught()

    // Initialize
    return CrimePerpetrator(
      identifier = identifier,
      firstName = firstName,
      lastName = lastName,
      age = age,
      caught = isCaught
    )
  }
}

/**
 * @see CrimePerpetratorRepository
 */
@Component
class CrimePerpetratorDao(
  private val operations: NamedParameterJdbcOperations
) : CrimePerpetratorRepository {

  companion object {

    /**
     * Shortcut to the current table.
     */
    private val TABLE: String
      get() = CRIME_PERPETRATOR_TABLE

    internal val COLUMNS = sqlColumnArrayOf(
      TABLE,
      IDENTIFIER_COLUMN,
      FIRST_NAME_COLUMN,
      LAST_NAME_COLUMN,
      AGE_COLUMN,
      CAUGHT_COLUMN
    )
  }

  /**
   * Shortcut to the most used query builder based on SELECT query.
   */
  private val callBuilder: SqlCallBuilder
    get() = SqlCallBuilder().select(COLUMNS).from(TABLE)

  override fun findByIdentifier(identifier: UUID): CrimePerpetrator? {
    val builder = callBuilder.where(IDENTIFIER_COLUMN, identifier)
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
    builder[IDENTIFIER_COLUMN] = perpetrator.identifier
    builder[FIRST_NAME_COLUMN] = perpetrator.firstName
    builder[LAST_NAME_COLUMN] = perpetrator.lastName
    builder[AGE_COLUMN] = perpetrator.age
    builder[CAUGHT_COLUMN] = perpetrator.caught
    val query = builder.build()
    operations.jdbcOperations.update(query)
  }

  @Transactional
  override fun update(perpetrator: CrimePerpetrator) {
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