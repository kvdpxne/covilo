package me.kvdpxne.covilo.infrastructure.jdbc.dao

import me.kvdpxne.covilo.domain.model.LocationCities
import me.kvdpxne.covilo.domain.model.LocationCity
import me.kvdpxne.covilo.domain.persistence.LocationCityRepository
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_CAPITAL
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_COUNTRY
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_DOMESTIC_NAME
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_KEY
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_POPULATION
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_REGION
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_LOCATION_CITY
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_LOCATION_COUNTRY
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_LOCATION_REGION
import me.kvdpxne.covilo.infrastructure.jdbc.callback.RowCounterCallback
import me.kvdpxne.covilo.infrastructure.jdbc.mapping.LocationCityMapper
import me.kvdpxne.covilo.util.sql.QueryBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

/**
 * @see LocationCityRepository
 */
@Component
class LocationCityDao @Autowired(required = true) constructor(
  private val operations: NamedParameterJdbcOperations
) : LocationCityRepository {

  companion object {

    /**
     * Current table
     */
    val TABLE: String
      get() = TABLE_LOCATION_CITY

    /**
     *
     */
    val FIELD_ARRAY = arrayOf(
      "$TABLE.$COLUMN_IDENTIFIER",
      "$TABLE.$COLUMN_KEY",
      "$TABLE.$COLUMN_DOMESTIC_NAME",
      *LocationRegionDao.FIELD_ARRAY,
      "$TABLE.$COLUMN_POPULATION",
      "$TABLE.$COLUMN_CAPITAL",
    )

    private val queryBuilder = QueryBuilder()
      .select(*FIELD_ARRAY)
      .from(TABLE_LOCATION_CITY)
      .join(TABLE_LOCATION_REGION, TABLE, COLUMN_REGION)
      .join(TABLE_LOCATION_COUNTRY, TABLE_LOCATION_REGION, COLUMN_COUNTRY)
  }

  override fun findByIdentifier(identifier: UUID): LocationCity? {
    val stringIdentifier = identifier.toString()
    val query = queryBuilder.where(stringIdentifier, COLUMN_IDENTIFIER).end()
    return runCatching {
      operations.queryForObject(
        query,
        mapOf(COLUMN_IDENTIFIER to stringIdentifier),
        LocationCityMapper
      )
    }.getOrNull()
  }

  @Deprecated(
    message = "Sometimes a key can match more than one entity.",
    replaceWith = ReplaceWith("findAllByKey")
  )
  override fun findByKey(key: String): LocationCity? {
    val query = queryBuilder.where(key, COLUMN_KEY).end()
    return runCatching {
      operations.queryForObject(
        query,
        mapOf(key to COLUMN_KEY),
        LocationCityMapper
      )
    }.getOrNull()
  }

  override fun findAll(): Collection<LocationCity> {
    val query = queryBuilder.end()
    return operations.query(
      query,
      LocationCityMapper
    )
  }

  override fun findAllByKey(key: String): LocationCities {
    val query = queryBuilder.where(key, COLUMN_KEY).end()
    return operations.query(
      query,
      mapOf(COLUMN_KEY to key),
      LocationCityMapper
    )
  }

  @Transactional
  override fun insert(city: LocationCity) {
    TODO("Not yet implemented")
  }

  @Transactional
  override fun update(city: LocationCity) {
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