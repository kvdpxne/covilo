package me.kvdpxne.covilo.infrastructure.jdbc.dao

import me.kvdpxne.covilo.domain.COLUMN_COUNTRY
import me.kvdpxne.covilo.domain.COLUMN_DOMESTIC_NAME
import me.kvdpxne.covilo.domain.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.domain.COLUMN_KEY
import me.kvdpxne.covilo.domain.COLUMN_REGION
import me.kvdpxne.covilo.domain.TABLE_LOCATION_CITY
import me.kvdpxne.covilo.domain.TABLE_LOCATION_COUNTRY
import me.kvdpxne.covilo.domain.TABLE_LOCATION_REGION
import me.kvdpxne.covilo.domain.model.LocationCity
import me.kvdpxne.covilo.domain.persistence.LocationCityRepository
import me.kvdpxne.covilo.infrastructure.jdbc.mapping.LocationCityMapper
import me.kvdpxne.covilo.util.count
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

/**
 * @see LocationCityRepository
 */
@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
@Component
class LocationCityDao @Autowired(required = true) constructor(
  private val template: NamedParameterJdbcTemplate
) : LocationCityRepository {

  // quick access
  private val tr = TABLE_LOCATION_CITY
  private val r0 = TABLE_LOCATION_REGION
  private val r1 = TABLE_LOCATION_COUNTRY
  private val c0 = COLUMN_IDENTIFIER
  private val c1 = COLUMN_KEY
  private val c2 = COLUMN_DOMESTIC_NAME
  private val k0 = COLUMN_REGION
  private val k1 = COLUMN_COUNTRY

  // frequently used query
  private val body = "SELECT $tr.$c0," +
    "$tr.$c1," +
    "$tr.$c2," +
    "$r0.$c0," +
    "$r0.$c1," +
    "$r0.$c2," +
    "$r1.$c0," +
    "$r1.$c1 " +
    "FROM $tr " +
    "INNER JOIN $r0 ON $tr.$k0 = $r0.$c0 " +
    "INNER JOIN $r1 ON $r0.$k1 = $r1.$c0"

  override fun findByIdentifier(p0: UUID): LocationCity? {
    return runCatching {
      template.queryForObject(
        "$body WHERE $tr.$c0 = :$c0;",
        mapOf(c0 to p0.toString()),
        LocationCityMapper
      )
    }.getOrNull()
  }

  override fun findBySpecificKey(
    p0: String, p1: String, p2: String
  ): LocationCity? {
    return runCatching {
      template.queryForObject(
        "$body WHERE $r1.$c1 = :$k1 AND $r0.$c1 = :$k0 AND $tr.$c1 = :$c1;",
        mapOf(k1 to p0, k0 to p1, c1 to p2),
        LocationCityMapper
      )
    }.getOrNull()
  }

  override fun findAll(): Collection<LocationCity> {
    return template.query("$body;", LocationCityMapper)
  }

  @Transactional
  override fun insert(p0: LocationCity) {
    TODO("Not yet implemented")
  }

  @Transactional
  override fun update(p0: LocationCity) {
    TODO("Not yet implemented")
  }

  @Transactional
  override fun delete(p0: UUID) {
    TODO("Not yet implemented")
  }

  override fun deleteAll() {
    TODO("Not yet implemented")
  }

  override fun count(): Long {
    return template.count("$tr.$c0", tr)
  }
}