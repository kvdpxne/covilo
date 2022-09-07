package me.kvdpxne.covilo.infrastructure.jdbc.dao

import me.kvdpxne.covilo.domain.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.domain.COLUMN_KEY
import me.kvdpxne.covilo.domain.TABLE_LOCATION_COUNTRY
import me.kvdpxne.covilo.domain.model.LocationCountry
import me.kvdpxne.covilo.domain.persistence.LocationCountryRepository
import me.kvdpxne.covilo.infrastructure.jdbc.mapping.LocationCountryMapper
import me.kvdpxne.covilo.util.count
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

/**
 * @see LocationCountryRepository
 */
@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
@Component
class LocationCountryDao @Autowired(required = true) constructor(
  private val template: NamedParameterJdbcTemplate
) : LocationCountryRepository {

  // quick access
  private val tr = TABLE_LOCATION_COUNTRY
  private val c0 = COLUMN_IDENTIFIER
  private val c1 = COLUMN_KEY

  // frequently used query
  private val body = "SELECT $tr.$c0," +
    "$tr.$c1 " +
    "FROM $tr"

  override fun findByIdentifier(p0: UUID): LocationCountry? {
//    val query = QueryBuilder(tr, "SELECT")
//      .select(c0, c1)
//      .from()
//      .where(c0)
//      .build()
//    val v0 = p0.toString()
//    return runCatching {
//      template.queryForObject(
//        query,
//        mapOf(c0 to v0),
//        LocationCountryMapper
//      )
//    }.getOrNull()
    return runCatching {
      template.queryForObject(
        "$body WHERE $tr.$c0 = :$c0;",
        mapOf(c0 to p0.toString()),
        LocationCountryMapper
      )
    }.getOrNull()
  }

  override fun findByKey(p0: String): LocationCountry? {
    return runCatching {
      template.queryForObject(
        "$body WHERE $tr.$c1 = :$c1;",
        mapOf(c1 to p0),
        LocationCountryMapper
      )
    }.getOrNull()
  }

  override fun findAll(): Collection<LocationCountry> {
    return template.query("$body;", LocationCountryMapper)
  }

  @Transactional
  override fun insert(p0: LocationCountry) {
    TODO("Not yet implemented")
  }

  @Transactional
  override fun update(p0: LocationCountry) {
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