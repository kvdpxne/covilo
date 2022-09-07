package me.kvdpxne.covilo.infrastructure.jdbc.dao

import me.kvdpxne.covilo.domain.COLUMN_COUNTRY
import me.kvdpxne.covilo.domain.COLUMN_DOMESTIC_NAME
import me.kvdpxne.covilo.domain.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.domain.COLUMN_KEY
import me.kvdpxne.covilo.domain.TABLE_LOCATION_COUNTRY
import me.kvdpxne.covilo.domain.TABLE_LOCATION_REGION
import me.kvdpxne.covilo.domain.model.LocationRegion
import me.kvdpxne.covilo.domain.persistence.LocationRegionRepository
import me.kvdpxne.covilo.infrastructure.jdbc.mapping.LocationRegionMapper
import me.kvdpxne.covilo.util.count
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

/**
 * @see LocationRegionRepository
 */
@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
@Component
class LocationRegionDao @Autowired(required = true) constructor(
  private val template: NamedParameterJdbcTemplate
) : LocationRegionRepository {

  // quick access
  private val tr = TABLE_LOCATION_REGION
  private val r0 = TABLE_LOCATION_COUNTRY
  private val c0 = COLUMN_IDENTIFIER
  private val c1 = COLUMN_KEY
  private val c2 = COLUMN_DOMESTIC_NAME
  private val k0 = COLUMN_COUNTRY

  // frequently used query
  private val body = "SELECT $tr.$c0," +
    "$tr.$c1," +
    "$tr.$c2," +
    "$r0.$c0," +
    "$r0.$c1 " +
    "FROM $tr " +
    "INNER JOIN $r0 ON $tr.$k0 = $r0.$c0"

  override fun findByIdentifier(p0: UUID): LocationRegion? {
    return runCatching {
      template.queryForObject(
        "$body WHERE $tr.$c0 = :$c0;",
        mapOf(c0 to p0.toString()),
        LocationRegionMapper
      )
    }.getOrNull()
  }

  override fun findByKey(p0: String): LocationRegion? {
    return runCatching {
      template.queryForObject(
        "$body WHERE $tr.$c1 = :$c1;",
        mapOf(c1 to p0),
        LocationRegionMapper
      )
    }.getOrNull()
  }

  override fun findAll(): Collection<LocationRegion> {
    return template.query("$body;", LocationRegionMapper)
  }

  @Transactional
  override fun insert(p0: LocationRegion) {
    TODO("Not yet implemented")
  }

  @Transactional
  override fun update(p0: LocationRegion) {
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