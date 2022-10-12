package me.kvdpxne.covilo.infrastructure.jdbc.dao

import me.kvdpxne.covilo.domain.models.LocationCountries
import me.kvdpxne.covilo.domain.models.LocationCountry
import me.kvdpxne.covilo.domain.persistence.LocationCountryRepository
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_KEY
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_LOCATION_COUNTRY
import me.kvdpxne.covilo.infrastructure.jdbc.callback.RowCounterCallbackHandler
import me.kvdpxne.covilo.infrastructure.jdbc.mapping.LocationCountryMapper
import me.kvdpxne.covilo.util.sql.SqlCallBuilder
import me.kvdpxne.covilo.util.sql.SqlDeleteBuilder
import me.kvdpxne.covilo.util.sql.SqlInsertBuilder
import me.kvdpxne.covilo.util.sql.sqlColumnArrayOf
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

/**
 * @see LocationCountryRepository
 */
@Component
class LocationCountryDao @Autowired(required = true) constructor(
  private val operations: NamedParameterJdbcTemplate
) : LocationCountryRepository {

  companion object {

    /**
     * Shortcut to the current table.
     */
    private val TABLE: String
      get() = TABLE_LOCATION_COUNTRY

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

  override fun findByIdentifier(identifier: UUID): LocationCountry? {
    val builder = callBuilder.where(COLUMN_IDENTIFIER, identifier)
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
    val builder = callBuilder.where(COLUMN_KEY, key)
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
    builder[COLUMN_IDENTIFIER] = country.identifier
    builder[COLUMN_KEY] = country.key
    val query = builder.build()
    operations.jdbcOperations.update(query)
  }

  @Transactional
  override fun update(country: LocationCountry) {
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