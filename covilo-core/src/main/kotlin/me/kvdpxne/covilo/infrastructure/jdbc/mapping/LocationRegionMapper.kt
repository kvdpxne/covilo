package me.kvdpxne.covilo.infrastructure.jdbc.mapping

import me.kvdpxne.covilo.domain.model.LocationRegion
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_LOCATION_REGION
import me.kvdpxne.covilo.util.sql.ResultSetParser
import me.kvdpxne.covilo.util.sql.parseDomesticName
import me.kvdpxne.covilo.util.sql.parseIdentifier
import me.kvdpxne.covilo.util.sql.parseKey
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException

object LocationRegionMapper : RowMapper<LocationRegion> {

  @Throws(SQLException::class)
  override fun mapRow(result: ResultSet, row: Int): LocationRegion {
    val parser = ResultSetParser(TABLE_LOCATION_REGION, result)

    // Parse the entity identifier
    val identifier = parser.parseIdentifier()

    // Parse relational entities to the entity
    val country = LocationCountryMapper.mapRow(result, row)

    // Parse remaining fields
    val key = parser.parseKey()
    val domesticName = parser.parseDomesticName()

    // Initialize
    return LocationRegion(
      identifier,
      key,
      domesticName,
      country
    )
  }
}