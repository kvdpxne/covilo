package me.kvdpxne.covilo.infrastructure.jdbc.dao

import me.kvdpxne.covilo.domain.model.LocationCities
import me.kvdpxne.covilo.domain.model.LocationCity
import me.kvdpxne.covilo.domain.persistence.LocationCityRepository
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_CAPITAL
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_DOMESTIC_NAME
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_KEY
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_POPULATION
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_REGION
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_LOCATION_CITY
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_LOCATION_REGION
import me.kvdpxne.covilo.infrastructure.jdbc.callback.RowCounterCallbackHandler
import me.kvdpxne.covilo.infrastructure.jdbc.mapping.LocationCityMapper
import me.kvdpxne.covilo.util.sql.SqlCallBuilder
import me.kvdpxne.covilo.util.sql.SqlDeleteBuilder
import me.kvdpxne.covilo.util.sql.SqlInsertBuilder
import me.kvdpxne.covilo.util.sql.SqlRelation
import me.kvdpxne.covilo.util.sql.sqlColumnArrayOf
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
     * Shortcut to the current table.
     */
    private val TABLE: String
      get() = TABLE_LOCATION_CITY

    internal val COLUMNS = sqlColumnArrayOf(
      TABLE,
      COLUMN_IDENTIFIER,
      COLUMN_KEY,
      COLUMN_DOMESTIC_NAME,
      *LocationRegionDao.COLUMNS.extend(),
      COLUMN_POPULATION,
      COLUMN_CAPITAL
    )

    internal val RELATIONS = arrayOf(
      SqlRelation(TABLE_LOCATION_REGION, TABLE, COLUMN_REGION),
      *LocationRegionDao.RELATIONS
    )
  }

  /**
   * Shortcut to the most used query builder based on SELECT query.
   */
  private val callBuilder: SqlCallBuilder
    get() = SqlCallBuilder().select(COLUMNS).from(TABLE).join(RELATIONS)

  override fun findByIdentifier(identifier: UUID): LocationCity? {
    val builder = callBuilder.where(COLUMN_IDENTIFIER, identifier)
    val query = builder.build()
    return runCatching {
      operations.queryForObject(
        query,
        builder.parameters,
        LocationCityMapper
      )
    }.getOrNull()
  }

  @Deprecated(
    message = "Sometimes a key can match more than one entity.",
    replaceWith = ReplaceWith("findAllByKey")
  )
  override fun findByKey(key: String): LocationCity? {
    val builder = callBuilder.where(COLUMN_KEY, key)
    val query = builder.build()
    return runCatching {
      operations.queryForObject(
        query,
        builder.parameters,
        LocationCityMapper
      )
    }.getOrNull()
  }

  override fun findAll(): LocationCities {
    val query = callBuilder.build()
    return operations.query(
      query,
      LocationCityMapper
    )
  }

  override fun findAllByKey(key: String): LocationCities {
    val builder = callBuilder.where(COLUMN_KEY, key)
    val query = builder.build()
    return operations.query(
      query,
      builder.parameters,
      LocationCityMapper
    )
  }

  @Transactional
  override fun insert(city: LocationCity) {
    val builder = SqlInsertBuilder(TABLE)
    builder[COLUMN_IDENTIFIER] = city.identifier
    builder[COLUMN_KEY] = city.key
    builder[COLUMN_DOMESTIC_NAME] = city.domesticName
    builder[COLUMN_REGION] = city.region.identifier
    builder[COLUMN_POPULATION] = city.population
    builder[COLUMN_CAPITAL] = city.capital
    val query = builder.build()
    operations.jdbcOperations.update(query)
  }

  @Transactional
  override fun update(city: LocationCity) {
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