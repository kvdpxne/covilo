package me.kvdpxne.covilo.infrastructure.jdbc.dao

import me.kvdpxne.covilo.domain.COLUMN_AGE
import me.kvdpxne.covilo.domain.COLUMN_CITY
import me.kvdpxne.covilo.domain.COLUMN_CLASSIFICATION
import me.kvdpxne.covilo.domain.COLUMN_IS_CONFIRMED
import me.kvdpxne.covilo.domain.COLUMN_COUNTRY
import me.kvdpxne.covilo.domain.COLUMN_DATE
import me.kvdpxne.covilo.domain.COLUMN_DESCRIPTION
import me.kvdpxne.covilo.domain.COLUMN_DOMESTIC_NAME
import me.kvdpxne.covilo.domain.COLUMN_FIRST_NAME
import me.kvdpxne.covilo.domain.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.domain.COLUMN_IS_CAUGHT
import me.kvdpxne.covilo.domain.COLUMN_KEY
import me.kvdpxne.covilo.domain.COLUMN_LAST_NAME
import me.kvdpxne.covilo.domain.COLUMN_PERPETRATOR
import me.kvdpxne.covilo.domain.COLUMN_REGION
import me.kvdpxne.covilo.domain.TABLE_CRIME
import me.kvdpxne.covilo.domain.TABLE_CRIME_CLASSIFICATION
import me.kvdpxne.covilo.domain.TABLE_CRIME_PERPETRATOR
import me.kvdpxne.covilo.domain.TABLE_LOCATION_CITY
import me.kvdpxne.covilo.domain.TABLE_LOCATION_COUNTRY
import me.kvdpxne.covilo.domain.TABLE_LOCATION_REGION
import me.kvdpxne.covilo.domain.model.Crime
import me.kvdpxne.covilo.domain.model.Crimes
import me.kvdpxne.covilo.domain.persistence.CrimeRepository
import me.kvdpxne.covilo.util.count
import me.kvdpxne.covilo.infrastructure.jdbc.mapping.CrimeMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

/**
 * @see CrimeRepository
 */
@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
@Component
class CrimeDao @Autowired(required = true) constructor(
  private val template: NamedParameterJdbcTemplate
) : CrimeRepository {

  companion object {
    private const val T0 = TABLE_CRIME
    private const val R0 = TABLE_LOCATION_CITY
    private const val R1 = TABLE_LOCATION_REGION
    private const val R2 = TABLE_LOCATION_COUNTRY
    private const val R3 = TABLE_CRIME_CLASSIFICATION
    private const val R4 = TABLE_CRIME_PERPETRATOR
    private const val C0 = COLUMN_IDENTIFIER
    private const val C1 = COLUMN_KEY
    private const val C2 = COLUMN_DOMESTIC_NAME
    private const val C3 = COLUMN_DATE
    private const val C4 = COLUMN_FIRST_NAME
    private const val C5 = COLUMN_LAST_NAME
    private const val C6 = COLUMN_AGE
    private const val C7 = COLUMN_IS_CAUGHT
    private const val C8 = COLUMN_DESCRIPTION
    private const val C9 = COLUMN_IS_CONFIRMED
    private const val K0 = COLUMN_CITY
    private const val K1 = COLUMN_REGION
    private const val K2 = COLUMN_COUNTRY
    private const val K3 = COLUMN_CLASSIFICATION
    private const val K4 = COLUMN_PERPETRATOR
  }

  // frequently used query
  private val selectQuery = "SELECT $T0.$C0," +
    "$T0.$C3," +
    "$R0.$C0," +
    "$R0.$C1," +
    "$R0.$C2," +
    "$R1.$C0," +
    "$R1.$C1," +
    "$R1.$C2," +
    "$R2.$C0," +
    "$R2.$C1," +
    "$R3.$C0," +
    "$R3.$C1," +
    "$R4.$C0," +
    "$R4.$C4," +
    "$R4.$C5," +
    "$R4.$C6," +
    "$R4.$C7," +
    "$T0.$C8," +
    "$T0.$C9 " +
    "FROM $T0 " +
    "INNER JOIN $R0 ON $T0.$K0 = $R0.$C0 " +
    "INNER JOIN $R1 ON $R0.$K1 = $R1.$C0 " +
    "INNER JOIN $R2 ON $R1.$K2 = $R2.$C0 " +
    "INNER JOIN $R3 ON $T0.$K3 = $R3.$C0 " +
    "INNER JOIN $R4 ON $T0.$K4 = $R4.$C0"

  override fun findByIdentifier(p0: UUID): Crime? {
    val v0 = p0.toString()
    return runCatching {
      template.queryForObject(
        "$selectQuery WHERE $T0.$C0 = :$C0;",
        mapOf(C0 to v0),
        CrimeMapper
      )
    }.getOrNull()
  }

  override fun findAll(): Crimes {
    return template.query(
      "$selectQuery;",
      CrimeMapper
    )
  }

  @Transactional
  override fun insert(p0: Crime) {
    TODO("Not yet implemented")
  }

  @Transactional
  override fun update(p0: Crime) {
    TODO("Not yet implemented")
  }

  @Transactional
  override fun delete(p0: UUID) {
    val v0 = p0.toString()
    template.update(
      "DELETE FROM $T0 WHERE $C0 = :$C0;",
      mapOf(C0 to v0)
    )
  }

  override fun deleteAll() {
    TODO("Not yet implemented")
  }

  override fun count(): Long {
    return template.count(C0, T0)
  }
}