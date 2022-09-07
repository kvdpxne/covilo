package me.kvdpxne.covilo.infrastructure.jdbc.mapping

import me.kvdpxne.covilo.domain.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.domain.COLUMN_KEY
import me.kvdpxne.covilo.domain.TABLE_LOCATION_COUNTRY
import me.kvdpxne.covilo.domain.model.LocationCountry
import me.kvdpxne.covilo.util.sql.getUuid
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException

object LocationCountryMapper : RowMapper<LocationCountry> {

  private const val TR = TABLE_LOCATION_COUNTRY
  private const val C0 = COLUMN_IDENTIFIER
  private const val C1 = COLUMN_KEY

  @Throws(SQLException::class)
  override fun mapRow(result: ResultSet, row: Int): LocationCountry {
    val v0 = result.getUuid("$TR.$C0")
    val v1 = result.getString("$TR.$C1")
    return LocationCountry(
      identifier = v0,
      key = v1
    )
  }
}