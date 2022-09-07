package me.kvdpxne.covilo.infrastructure.jdbc.dao

import me.kvdpxne.covilo.domain.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.domain.COLUMN_KEY
import me.kvdpxne.covilo.domain.TABLE_CRIME_CLASSIFICATION
import me.kvdpxne.covilo.domain.model.CrimeClassification
import me.kvdpxne.covilo.domain.model.CrimeClassifications
import me.kvdpxne.covilo.domain.persistence.CrimeClassificationRepository
import me.kvdpxne.covilo.util.count
import me.kvdpxne.covilo.infrastructure.jdbc.mapping.CrimeClassificationMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

/**
 * @see CrimeClassificationRepository
 */
@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
@Component
class CrimeClassificationDao @Autowired(required = true) constructor(
  private val template: NamedParameterJdbcTemplate
) : CrimeClassificationRepository {

  companion object {
    private const val T0 = TABLE_CRIME_CLASSIFICATION
    private const val C0 = COLUMN_IDENTIFIER
    private const val C1 = COLUMN_KEY
  }

  // frequently used query
  private val selectQuery = "SELECT $T0.$C0," +
    "$T0.$C1 " +
    "FROM $T0"

  override fun findByIdentifier(p0: UUID): CrimeClassification? {
    val v0 = p0.toString()
    return runCatching {
      template.queryForObject(
        "$selectQuery WHERE $T0.$C0 = :$C0;",
        mapOf(C0 to v0),
        CrimeClassificationMapper
      )
    }.getOrNull()
  }

  override fun findAll(): CrimeClassifications {
    return template.query(
      "$selectQuery;",
      CrimeClassificationMapper
    )
  }

  @Transactional
  override fun insert(p0: CrimeClassification) {
    val v0 = p0.identifier.toString()
    val v1 = p0.key
    template.update(
      "INSERT INTO $T0($C0,$C1) VALUES(:$C0,:$C1);",
      mapOf(C0 to v0, C1 to v1)
    )
  }

  @Transactional
  override fun update(p0: CrimeClassification) {
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
