package me.kvdpxne.covilo.infrastructure.jdbc.mapping

import me.kvdpxne.covilo.domain.models.LocationCountry
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_LOCATION_COUNTRY
import me.kvdpxne.covilo.util.sql.ResultSetParser
import me.kvdpxne.covilo.util.sql.parseIdentifier
import me.kvdpxne.covilo.util.sql.parseKey
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException

object LocationCountryMapper : RowMapper<LocationCountry> {

  @Throws(SQLException::class)
  override fun mapRow(result: ResultSet, row: Int): LocationCountry {
    val parser = ResultSetParser(TABLE_LOCATION_COUNTRY, result)

    // Parse the entity identifier
    val identifier = parser.parseIdentifier()

    // Parse remaining fields
    val key = parser.parseKey()

    // Initialize
    return LocationCountry(
      identifier,
      key
    )
  }
}