package me.kvdpxne.covilo.infrastructure.jdbc.mapping

import me.kvdpxne.covilo.domain.models.CapitalType
import me.kvdpxne.covilo.domain.models.LocationCity
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_CAPITAL
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_POPULATION
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_LOCATION_CITY
import me.kvdpxne.covilo.util.sql.ResultSetParser
import me.kvdpxne.covilo.util.sql.parseDomesticName
import me.kvdpxne.covilo.util.sql.parseIdentifier
import me.kvdpxne.covilo.util.sql.parseKey
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException

object LocationCityMapper : RowMapper<LocationCity> {

  @Throws(SQLException::class)
  override fun mapRow(result: ResultSet, row: Int): LocationCity {
    val parser = ResultSetParser(TABLE_LOCATION_CITY, result)

    // Parse the entity identifier
    val identifier = parser.parseIdentifier()

    // Parse relational entities to the entity
    val region = LocationRegionMapper.mapRow(result, row)

    // Parse remaining fields
    val key = parser.parseKey()
    val domesticName = parser.parseDomesticName()
    val population = parser.parseInt(COLUMN_POPULATION)
    val capital = parser.parseEnum(COLUMN_CAPITAL, CapitalType.values())
      ?: CapitalType.NONE

    // Initialize
    return LocationCity(
      identifier,
      key,
      domesticName,
      region,
      population,
      capital
    )
  }
}