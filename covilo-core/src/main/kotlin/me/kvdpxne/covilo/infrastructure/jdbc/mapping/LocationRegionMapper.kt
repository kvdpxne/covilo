package me.kvdpxne.covilo.infrastructure.jdbc.mapping

import me.kvdpxne.covilo.domain.COLUMN_DOMESTIC_NAME
import me.kvdpxne.covilo.domain.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.domain.COLUMN_KEY
import me.kvdpxne.covilo.domain.TABLE_LOCATION_REGION
import me.kvdpxne.covilo.domain.model.LocationRegion
import me.kvdpxne.covilo.util.sql.getUuid
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException

object LocationRegionMapper : RowMapper<LocationRegion> {

  private const val TR = TABLE_LOCATION_REGION
  private const val C0 = COLUMN_IDENTIFIER
  private const val C1 = COLUMN_KEY
  private const val C2 = COLUMN_DOMESTIC_NAME

  @Throws(SQLException::class)
  override fun mapRow(result: ResultSet, row: Int): LocationRegion {
    val v0 = result.getUuid("$TR.$C0")
    val v1 = result.getString("$TR.$C1")
    val v2 = result.getString("$TR.$C2")
    val v3 = LocationCountryMapper.mapRow(result, row)
    return LocationRegion(
      identifier = v0,
      key = v1,
      domesticName = v2,
      country = v3
    )
  }
}