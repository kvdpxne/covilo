package me.kvdpxne.covilo.infrastructure.jdbc.mapping

import me.kvdpxne.covilo.domain.models.Crime
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_DATE
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_DESCRIPTION
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_IS_CONFIRMED
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_CRIME
import me.kvdpxne.covilo.util.sql.ResultSetParser
import me.kvdpxne.covilo.util.sql.parseIdentifier
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException

object CrimeMapper : RowMapper<Crime> {

  @Throws(SQLException::class)
  override fun mapRow(result: ResultSet, row: Int): Crime {
    val parser = ResultSetParser(TABLE_CRIME, result)

    // Parse the entity identifier
    val identifier = parser.parseIdentifier()

    // Parse relational entities to the entity
    val city = LocationCityMapper.mapRow(result, row)
    val classification = CrimeClassificationMapper.mapRow(result, row)
    val perpetrator = CrimePerpetratorMapper.mapRow(result, row)

    // Parse remaining fields
    val date = parser.parseDate(COLUMN_DATE)
    val description = parser.parse(COLUMN_DESCRIPTION)!!
    val isConfirmed = parser.parseBoolean(COLUMN_IS_CONFIRMED)

    // Initialize
    return Crime(
      identifier,
      date,
      city,
      classification,
      perpetrator,
      description,
      isConfirmed
    )
  }
}