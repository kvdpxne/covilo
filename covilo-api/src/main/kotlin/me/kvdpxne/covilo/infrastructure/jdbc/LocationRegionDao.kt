package me.kvdpxne.covilo.infrastructure.jdbc

import me.kvdpxne.covilo.DOMESTIC_NAME_COLUMN
import me.kvdpxne.covilo.IDENTIFIER_COLUMN
import me.kvdpxne.covilo.KEY_COLUMN
import me.kvdpxne.covilo.LOCATION_COUNTRY_COLUMN
import me.kvdpxne.covilo.LOCATION_COUNTRY_TABLE
import me.kvdpxne.covilo.LOCATION_REGION_TABLE
import me.kvdpxne.covilo.domain.models.LocationRegion
import me.kvdpxne.covilo.domain.models.LocationRegions
import me.kvdpxne.covilo.domain.repositories.LocationRegionRepository
import me.kvdpxne.covilo.domesticName
import me.kvdpxne.covilo.identifier
import me.kvdpxne.covilo.key
import me.kvdpxne.covilo.utils.sql.ResultSetParser
import me.kvdpxne.covilo.utils.sql.SqlCallBuilder
import me.kvdpxne.covilo.utils.sql.SqlDeleteBuilder
import me.kvdpxne.covilo.utils.sql.SqlInsertBuilder
import me.kvdpxne.covilo.utils.sql.SqlRelation
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
  get() = LOCATION_REGION_TABLE

internal object LocationRegionMapper : RowMapper<LocationRegion> {

  @Throws(SQLException::class)
  override fun mapRow(result: ResultSet, row: Int): LocationRegion {
    val parser = ResultSetParser(TABLE, result)

    // Parse the entity identifier
    val identifier = parser.identifier()

    // Parse relational entities to the entity
    val country = LocationCountryMapper.mapRow(result, row)

    // Parse remaining fields
    val key = parser.key()
    val domesticName = parser.domesticName()

    // Initialize
    return LocationRegion(
      identifier = identifier,
      key = key,
      domesticName = domesticName,
      country = country
    )
  }
}

/**
 * @see LocationRegionRepository
 */
@Component
class LocationRegionDao(
  private val operations: NamedParameterJdbcOperations
) : LocationRegionRepository {

  companion object {

    internal val COLUMNS = sqlColumnArrayOf(
      TABLE,
      IDENTIFIER_COLUMN,
      KEY_COLUMN,
      DOMESTIC_NAME_COLUMN,
      *LocationCountryDao.COLUMNS.extend()
    )

    internal val RELATIONS = arrayOf(
      SqlRelation(LOCATION_COUNTRY_TABLE, TABLE, LOCATION_COUNTRY_COLUMN)
    )
  }

  /**
   * Shortcut to the most used query builder based on SELECT query.
   */
  private val callBuilder: SqlCallBuilder
    get() = SqlCallBuilder().select(COLUMNS).from(TABLE).join(RELATIONS)

  override fun findByIdentifier(identifier: UUID): LocationRegion? {
    val builder = callBuilder.where(IDENTIFIER_COLUMN, identifier)
    val query = builder.build()
    return runCatching {
      operations.queryForObject(
        query,
        builder.parameters,
        LocationRegionMapper
      )
    }.getOrNull()
  }

  override fun findByKey(key: String): LocationRegion? {
    val builder = callBuilder.where(key, KEY_COLUMN)
    val query = builder.build()
    return runCatching {
      operations.queryForObject(
        query,
        builder.parameters,
        LocationRegionMapper
      )
    }.getOrNull()
  }

  override fun findAll(): LocationRegions {
    val query = callBuilder.build()
    return operations.query(
      query,
      LocationRegionMapper
    )
  }

  @Transactional
  override fun insert(region: LocationRegion) {
    val builder = SqlInsertBuilder(TABLE)
    builder[IDENTIFIER_COLUMN] = region.identifier
    builder[KEY_COLUMN] = region.key
    builder[DOMESTIC_NAME_COLUMN] = region.domesticName
    builder[LOCATION_COUNTRY_COLUMN] = region.country.identifier
    val query = builder.build()
    operations.jdbcOperations.update(query)
  }

  @Transactional
  override fun update(region: LocationRegion) {
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