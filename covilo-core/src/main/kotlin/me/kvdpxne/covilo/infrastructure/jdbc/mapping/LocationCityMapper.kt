package me.kvdpxne.covilo.infrastructure.jdbc.mapping

import me.kvdpxne.covilo.domain.COLUMN_DOMESTIC_NAME
import me.kvdpxne.covilo.domain.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.domain.COLUMN_KEY
import me.kvdpxne.covilo.domain.TABLE_LOCATION_CITY
import me.kvdpxne.covilo.domain.model.LocationCity
import me.kvdpxne.covilo.util.sql.getUuid
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException

object LocationCityMapper : RowMapper<LocationCity> {

  private const val TR = TABLE_LOCATION_CITY
  private const val C0 = COLUMN_IDENTIFIER
  private const val C1 = COLUMN_KEY
  private const val C2 = COLUMN_DOMESTIC_NAME

  @Throws(SQLException::class)
  override fun mapRow(result: ResultSet, row: Int): LocationCity {
    val v0 = result.getUuid("$TR.$C0")
    val v1 = result.getString("$TR.$C1")
    val v2 = result.getString("$TR.$C2")
    val v3 = LocationRegionMapper.mapRow(result, row)
    return LocationCity(
      identifier = v0,
      key = v1,
      domesticName = v2,
      region = v3
    )
  }
}