package me.kvdpxne.covilo.infrastructure.jdbc.mapping

import me.kvdpxne.covilo.domain.model.CrimeClassification
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_CRIME_CLASSIFICATION
import me.kvdpxne.covilo.util.sql.ResultSetParser
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException

object CrimeClassificationMapper : RowMapper<CrimeClassification> {

  @Throws(SQLException::class)
  override fun mapRow(result: ResultSet, row: Int): CrimeClassification {
    val parser = ResultSetParser(TABLE_CRIME_CLASSIFICATION, result)

    // Parse the entity identifier
    val identifier = parser.parseIdentifier()

    // Parse remaining fields
    val key = parser.parseKey()

    // Initialize
    return CrimeClassification(
      identifier,
      key
    )
  }
}