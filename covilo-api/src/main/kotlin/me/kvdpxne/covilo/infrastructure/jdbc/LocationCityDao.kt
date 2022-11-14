package me.kvdpxne.covilo.infrastructure.jdbc

import me.kvdpxne.covilo.CAPITAL_COLUMN
import me.kvdpxne.covilo.DOMESTIC_NAME_COLUMN
import me.kvdpxne.covilo.IDENTIFIER_COLUMN
import me.kvdpxne.covilo.KEY_COLUMN
import me.kvdpxne.covilo.LOCATION_CITY_TABLE
import me.kvdpxne.covilo.LOCATION_REGION_COLUMN
import me.kvdpxne.covilo.LOCATION_REGION_TABLE
import me.kvdpxne.covilo.POPULATION_COLUMN
import me.kvdpxne.covilo.capital
import me.kvdpxne.covilo.domain.models.LocationCities
import me.kvdpxne.covilo.domain.models.LocationCity
import me.kvdpxne.covilo.domain.repositories.LocationCityRepository
import me.kvdpxne.covilo.domesticName
import me.kvdpxne.covilo.identifier
import me.kvdpxne.covilo.key
import me.kvdpxne.covilo.population
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
  get() = LOCATION_CITY_TABLE

internal object LocationCityMapper : RowMapper<LocationCity> {

  @Throws(SQLException::class)
  override fun mapRow(result: ResultSet, row: Int): LocationCity {
    val parser = ResultSetParser(TABLE, result)

    // Parse the entity identifier
    val identifier = parser.identifier()

    // Parse relational entities to the entity
    val region = LocationRegionMapper.mapRow(result, row)

    // Parse remaining fields
    val key = parser.key()
    val domesticName = parser.domesticName()
    val population = parser.population()
    val capital = parser.capital()

    // Initialize
    return LocationCity(
      identifier,
      key,
      domesticName,
      region,
      population.toInt(),
      capital
    )
  }
}

/**
 * @see LocationCityRepository
 */
@Component
class LocationCityDao(
  private val operations: NamedParameterJdbcOperations
) : LocationCityRepository {

  companion object {

    internal val COLUMNS = sqlColumnArrayOf(
      TABLE,
      IDENTIFIER_COLUMN,
      KEY_COLUMN,
      DOMESTIC_NAME_COLUMN,
      *LocationRegionDao.COLUMNS.extend(),
      POPULATION_COLUMN,
      CAPITAL_COLUMN
    )

    internal val RELATIONS = arrayOf(
      SqlRelation(LOCATION_REGION_TABLE, TABLE, LOCATION_REGION_COLUMN),
      *LocationRegionDao.RELATIONS
    )
  }

  /**
   * Shortcut to the most used query builder based on SELECT query.
   */
  private val callBuilder: SqlCallBuilder
    get() = SqlCallBuilder().select(COLUMNS).from(TABLE).join(RELATIONS)

  override fun findByIdentifier(identifier: UUID): LocationCity? {
    val builder = callBuilder.where(IDENTIFIER_COLUMN, identifier)
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
    val builder = callBuilder.where(KEY_COLUMN, key)
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
    val builder = callBuilder.where(KEY_COLUMN, key)
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
    builder[IDENTIFIER_COLUMN] = city.identifier
    builder[KEY_COLUMN] = city.key
    builder[DOMESTIC_NAME_COLUMN] = city.domesticName
    builder[LOCATION_REGION_COLUMN] = city.region.identifier
    builder[POPULATION_COLUMN] = city.population
    builder[CAPITAL_COLUMN] = city.capital
    val query = builder.build()
    operations.jdbcOperations.update(query)
  }

  @Transactional
  override fun update(city: LocationCity) {
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