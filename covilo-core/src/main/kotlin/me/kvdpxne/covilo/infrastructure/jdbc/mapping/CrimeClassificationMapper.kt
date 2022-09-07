package me.kvdpxne.covilo.infrastructure.jdbc.mapping

import me.kvdpxne.covilo.domain.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.domain.COLUMN_KEY
import me.kvdpxne.covilo.domain.TABLE_CRIME_CLASSIFICATION
import me.kvdpxne.covilo.domain.model.CrimeClassification
import me.kvdpxne.covilo.util.sql.getUuid
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException

object CrimeClassificationMapper : RowMapper<CrimeClassification> {

  private const val TR = TABLE_CRIME_CLASSIFICATION
  private const val C0 = COLUMN_IDENTIFIER
  private const val C1 = COLUMN_KEY

  @Throws(SQLException::class)
  override fun mapRow(result: ResultSet, row: Int): CrimeClassification {
    val v0 = result.getUuid("$TR.$C0")
    val v1 = result.getString("$TR.$C1")
    return CrimeClassification(
      identifier = v0,
      key = v1
    )
  }
}