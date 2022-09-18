package me.kvdpxne.covilo.infrastructure.jdbc.dao

import me.kvdpxne.covilo.domain.model.LocationRegion
import me.kvdpxne.covilo.domain.model.LocationRegions
import me.kvdpxne.covilo.domain.persistence.LocationRegionRepository
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_COUNTRY
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_DOMESTIC_NAME
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_KEY
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_LOCATION_COUNTRY
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_LOCATION_REGION
import me.kvdpxne.covilo.infrastructure.jdbc.callback.RowCounterCallback
import me.kvdpxne.covilo.infrastructure.jdbc.mapping.LocationRegionMapper
import me.kvdpxne.covilo.util.sql.QueryBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

/**
 * @see LocationRegionRepository
 */
@Component
class LocationRegionDao @Autowired(required = true) constructor(
  private val operations: NamedParameterJdbcOperations
) : LocationRegionRepository {

  companion object {

    /**
     * Current table
     */
    val TABLE: String
      get() = TABLE_LOCATION_REGION

    /**
     *
     */
    val FIELD_ARRAY = arrayOf(
      "$TABLE.$COLUMN_IDENTIFIER",
      "$TABLE.$COLUMN_KEY",
      "$TABLE.$COLUMN_DOMESTIC_NAME",
      *LocationCountryDao.FIELD_ARRAY
    )

    private val queryBuilder = QueryBuilder()
      .select(*FIELD_ARRAY)
      .from(TABLE)
      .join(TABLE_LOCATION_COUNTRY, TABLE, COLUMN_COUNTRY)
  }

  override fun findByIdentifier(identifier: UUID): LocationRegion? {
    val stringIdentifier = identifier.toString()
    val query = queryBuilder.where(stringIdentifier, COLUMN_IDENTIFIER).end()
    return runCatching {
      operations.queryForObject(
        query,
        mapOf(COLUMN_IDENTIFIER to stringIdentifier),
        LocationRegionMapper
      )
    }.getOrNull()
  }

  override fun findByKey(key: String): LocationRegion? {
    val query = queryBuilder.where(key, COLUMN_KEY).end()
    return runCatching {
      operations.queryForObject(
        query,
        mapOf(COLUMN_KEY to key),
        LocationRegionMapper
      )
    }.getOrNull()
  }

  override fun findAll(): LocationRegions {
    val query = queryBuilder.end()
    return operations.query(
      query,
      LocationRegionMapper
    )
  }

  @Transactional
  override fun insert(region: LocationRegion) {
    TODO("Not yet implemented")
  }

  @Transactional
  override fun update(region: LocationRegion) {
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
      .from(TABLE)
      .end()
    operations.query(query, counter)
    return counter.count
  }
}