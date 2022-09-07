package me.kvdpxne.covilo.infrastructure.jdbc.mapping

import me.kvdpxne.covilo.domain.COLUMN_DATE
import me.kvdpxne.covilo.domain.COLUMN_DESCRIPTION
import me.kvdpxne.covilo.domain.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.domain.COLUMN_IS_CONFIRMED
import me.kvdpxne.covilo.domain.TABLE_CRIME
import me.kvdpxne.covilo.domain.model.Crime
import me.kvdpxne.covilo.util.sql.getUuid
import me.kvdpxne.covilo.util.sql.getLocalDate
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException

object CrimeMapper : RowMapper<Crime> {

  private const val TR = TABLE_CRIME
  private const val C0 = COLUMN_IDENTIFIER
  private const val C1 = COLUMN_DATE
  private const val C2 = COLUMN_DESCRIPTION
  private const val C3 = COLUMN_IS_CONFIRMED

  @Throws(SQLException::class)
  override fun mapRow(result: ResultSet, row: Int): Crime {
    val v0 = result.getUuid("$TR.$C0")
    val v1 = result.getLocalDate("$TR.$C1")
    val v2 = LocationCityMapper.mapRow(result, row)
    val v3 = CrimeClassificationMapper.mapRow(result, row)
    val v4 = CrimePerpetratorMapper.mapRow(result, row)
    val v5 = result.getString("$TR.$C2")
    val v6 = result.getBoolean("$TR.$C3")
    return Crime(
      identifier = v0,
      date = v1,
      city = v2,
      classification = v3,
      perpetrator = v4,
      description = v5,
      isConfirmed = v6
    )
  }
}