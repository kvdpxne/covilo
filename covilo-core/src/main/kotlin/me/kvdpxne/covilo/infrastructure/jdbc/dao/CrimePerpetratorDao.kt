package me.kvdpxne.covilo.infrastructure.jdbc.dao

import me.kvdpxne.covilo.domain.COLUMN_AGE
import me.kvdpxne.covilo.domain.COLUMN_FIRST_NAME
import me.kvdpxne.covilo.domain.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.domain.COLUMN_IS_CAUGHT
import me.kvdpxne.covilo.domain.COLUMN_LAST_NAME
import me.kvdpxne.covilo.domain.TABLE_CRIME_PERPETRATOR
import me.kvdpxne.covilo.domain.model.CrimePerpetrator
import me.kvdpxne.covilo.domain.model.CrimePerpetrators
import me.kvdpxne.covilo.domain.persistence.CrimePerpetratorRepository
import me.kvdpxne.covilo.util.count
import me.kvdpxne.covilo.infrastructure.jdbc.mapping.CrimePerpetratorMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

/**
 * @see CrimePerpetratorRepository
 */
@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
@Component
class CrimePerpetratorDao @Autowired(required = true) constructor(
  private val template: NamedParameterJdbcTemplate
) : CrimePerpetratorRepository {

  companion object {
    private const val T0 = TABLE_CRIME_PERPETRATOR
    private const val C0 = COLUMN_IDENTIFIER
    private const val C1 = COLUMN_FIRST_NAME
    private const val C2 = COLUMN_LAST_NAME
    private const val C3 = COLUMN_AGE
    private const val C4 = COLUMN_IS_CAUGHT
  }

  // frequently used query
  private val selectQuery = "SELECT $T0.$C0," +
    "$T0.$C1," +
    "$T0.$C2," +
    "$T0.$C3," +
    "$T0.$C4 " +
    "FROM $T0"

  override fun findByIdentifier(p0: UUID): CrimePerpetrator? {
    val v0 = p0.toString()
    return runCatching {
      template.queryForObject(
        "$selectQuery WHERE $T0.$C0 = :$C0;",
        mapOf(C0 to v0),
        CrimePerpetratorMapper
      )
    }.getOrNull()
  }

  override fun findAll(): CrimePerpetrators {
    return template.query(
      "$selectQuery;",
      CrimePerpetratorMapper
    )
  }

  @Transactional
  override fun insert(p0: CrimePerpetrator) {
    val v0 = p0.identifier.toString()
    val v1 = p0.firstName
    val v2 = p0.lastName
    val v3 = p0.age
    val v4 = p0.isCaught
    template.update(
      "INSERT INTO $T0($C0,$C1,$C2,$C3,$C4) VALUES(:$C0,:$C1,:$C2,:$C3,:$C4);",
      mapOf(C0 to v0, C1 to v1, C2 to v2, C3 to v3, C4 to v4)
    )
  }

  @Transactional
  override fun update(p0: CrimePerpetrator) {
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