package me.kvdpxne.covilo.infrastructure.jdbc.dao

import me.kvdpxne.covilo.domain.models.LocationRegion
import me.kvdpxne.covilo.domain.models.LocationRegions
import me.kvdpxne.covilo.domain.persistence.LocationRegionRepository
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_COUNTRY
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_DOMESTIC_NAME
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_KEY
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_LOCATION_COUNTRY
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_LOCATION_REGION
import me.kvdpxne.covilo.infrastructure.jdbc.callback.RowCounterCallbackHandler
import me.kvdpxne.covilo.infrastructure.jdbc.mapping.LocationRegionMapper
import me.kvdpxne.covilo.util.sql.SqlCallBuilder
import me.kvdpxne.covilo.util.sql.SqlDeleteBuilder
import me.kvdpxne.covilo.util.sql.SqlInsertBuilder
import me.kvdpxne.covilo.util.sql.SqlRelation
import me.kvdpxne.covilo.util.sql.sqlColumnArrayOf
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

/**
 * @see LocationRegionRepository
 */
@Component
class LocationRegionDao @Autowired(required = true) constructor(
  private val operations: NamedParameterJdbcTemplate
) : LocationRegionRepository {

  companion object {

    /**
     * Shortcut to the current table.
     */
    private val TABLE: String
      get() = TABLE_LOCATION_REGION

    internal val COLUMNS = sqlColumnArrayOf(
      TABLE,
      COLUMN_IDENTIFIER,
      COLUMN_KEY,
      COLUMN_DOMESTIC_NAME,
      *LocationCountryDao.COLUMNS.extend()
    )

    internal val RELATIONS = arrayOf(
      SqlRelation(TABLE_LOCATION_COUNTRY, TABLE, COLUMN_COUNTRY)
    )
  }

  /**
   * Shortcut to the most used query builder based on SELECT query.
   */
  private val callBuilder: SqlCallBuilder
    get() = SqlCallBuilder().select(COLUMNS).from(TABLE).join(RELATIONS)

  override fun findByIdentifier(identifier: UUID): LocationRegion? {
    val builder = callBuilder.where(COLUMN_IDENTIFIER, identifier)
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
    val builder = callBuilder.where(key, COLUMN_KEY)
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
    builder[COLUMN_IDENTIFIER] = region.identifier
    builder[COLUMN_KEY] = region.key
    builder[COLUMN_DOMESTIC_NAME] = region.domesticName
    builder[COLUMN_COUNTRY] = region.country.identifier
    val query = builder.build()
    operations.jdbcOperations.update(query)
  }

  @Transactional
  override fun update(region: LocationRegion) {
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