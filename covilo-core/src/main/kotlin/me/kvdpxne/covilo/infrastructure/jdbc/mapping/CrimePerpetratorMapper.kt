package me.kvdpxne.covilo.infrastructure.jdbc.mapping

import me.kvdpxne.covilo.domain.model.CrimePerpetrator
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_AGE
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_FIRST_NAME
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_IS_CAUGHT
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_LAST_NAME
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_CRIME_PERPETRATOR
import me.kvdpxne.covilo.util.sql.ResultSetParser
import me.kvdpxne.covilo.util.sql.parseIdentifier
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException

object CrimePerpetratorMapper : RowMapper<CrimePerpetrator> {

  @Throws(SQLException::class)
  override fun mapRow(result: ResultSet, row: Int): CrimePerpetrator {
    val parser = ResultSetParser(TABLE_CRIME_PERPETRATOR, result)

    // Parse the entity identifier
    val identifier = parser.parseIdentifier()

    // Parse remaining fields
    val firstName = parser.parse(COLUMN_FIRST_NAME)!!
    val lastName = parser.parse(COLUMN_LAST_NAME)!!
    val age = parser.parseInt(COLUMN_AGE)
    val isCaught = parser.parseBoolean(COLUMN_IS_CAUGHT)

    // Initialize
    return CrimePerpetrator(
      identifier,
      firstName,
      lastName,
      age,
      isCaught
    )
  }
}