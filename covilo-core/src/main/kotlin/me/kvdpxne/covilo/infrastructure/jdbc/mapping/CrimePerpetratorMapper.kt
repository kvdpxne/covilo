package me.kvdpxne.covilo.infrastructure.jdbc.mapping

import me.kvdpxne.covilo.domain.COLUMN_AGE
import me.kvdpxne.covilo.domain.COLUMN_FIRST_NAME
import me.kvdpxne.covilo.domain.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.domain.COLUMN_IS_CAUGHT
import me.kvdpxne.covilo.domain.COLUMN_LAST_NAME
import me.kvdpxne.covilo.domain.TABLE_CRIME_PERPETRATOR
import me.kvdpxne.covilo.domain.model.CrimePerpetrator
import me.kvdpxne.covilo.util.sql.getUuid
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException

object CrimePerpetratorMapper : RowMapper<CrimePerpetrator> {

  private const val TR = TABLE_CRIME_PERPETRATOR
  private const val C0 = COLUMN_IDENTIFIER
  private const val C1 = COLUMN_FIRST_NAME
  private const val C2 = COLUMN_LAST_NAME
  private const val C3 = COLUMN_AGE
  private const val C4 = COLUMN_IS_CAUGHT

  @Throws(SQLException::class)
  override fun mapRow(result: ResultSet, num: Int): CrimePerpetrator {
    val v0 = result.getUuid("$TR.$C0")
    val v1 = result.getString("$TR.$C1")
    val v2 = result.getString("$TR.$C2")
    val v3 = result.getInt("$TR.$C3")
    val v4 = result.getBoolean("$TR.$C4")
    return CrimePerpetrator(
      identifier = v0,
      firstName = v1,
      lastName = v2,
      age = v3,
      isCaught = v4
    )
  }
}