package me.kvdpxne.covilo.infrastructure.jdbc

import me.kvdpxne.covilo.IDENTIFIER_COLUMN
import me.kvdpxne.covilo.KEY_COLUMN
import me.kvdpxne.covilo.LOCATION_COUNTRY_TABLE
import me.kvdpxne.covilo.domain.models.LocationCountries
import me.kvdpxne.covilo.domain.models.LocationCountry
import me.kvdpxne.covilo.domain.repositories.LocationCountryRepository
import me.kvdpxne.covilo.identifier
import me.kvdpxne.covilo.key
import me.kvdpxne.covilo.utils.sql.ResultSetParser
import me.kvdpxne.covilo.utils.sql.SqlCallBuilder
import me.kvdpxne.covilo.utils.sql.SqlDeleteBuilder
import me.kvdpxne.covilo.utils.sql.SqlInsertBuilder
import me.kvdpxne.covilo.utils.sql.sqlColumnArrayOf
import org.springframework.jdbc.core.RowCountCallbackHandler
import org.springframework.jdbc.core.RowMapper
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
  get() = LOCATION_COUNTRY_TABLE

internal object LocationCountryMapper : RowMapper<LocationCountry> {

  @Throws(SQLException::class)
  override fun mapRow(result: ResultSet, row: Int): LocationCountry {
    val parser = ResultSetParser(TABLE, result)

    // Parse the entity identifier
    val identifier = parser.identifier()

    // Parse remaining fields
    val key = parser.key()

    // Initialize
    return LocationCountry(
      identifier = identifier,
      key = key
    )
  }
}

/**
 * @see LocationCountryRepository
 */
@Component
class LocationCountryDao(
  private val operations: NamedParameterJdbcOperations
) : LocationCountryRepository {

  companion object {

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

  override fun findByIdentifier(identifier: UUID): LocationCountry? {
    val builder = callBuilder.where(IDENTIFIER_COLUMN, identifier)
    val query = builder.build()
    return runCatching {
      operations.queryForObject(
        query,
        builder.parameters,
        LocationCountryMapper
      )
    }.getOrNull()
  }

  override fun findByKey(key: String): LocationCountry? {
    val builder = callBuilder.where(KEY_COLUMN, key)
    val query = builder.build()
    return runCatching {
      operations.queryForObject(
        query,
        builder.parameters,
        LocationCountryMapper
      )
    }.getOrNull()
  }

  override fun findAll(): LocationCountries {
    val query = callBuilder.build()
    return operations.query(
      query,
      LocationCountryMapper
    )
  }

  @Transactional
  override fun insert(country: LocationCountry) {
    val builder = SqlInsertBuilder(TABLE)
    builder[IDENTIFIER_COLUMN] = country.identifier
    builder[KEY_COLUMN] = country.key
    val query = builder.build()
    operations.jdbcOperations.update(query)
  }

  @Transactional
  override fun update(country: LocationCountry) {
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