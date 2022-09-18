package me.kvdpxne.covilo.infrastructure.jdbc.dao

import me.kvdpxne.covilo.domain.model.CrimeClassification
import me.kvdpxne.covilo.domain.model.CrimeClassifications
import me.kvdpxne.covilo.domain.persistence.CrimeClassificationRepository
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_KEY
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_CRIME_CLASSIFICATION
import me.kvdpxne.covilo.infrastructure.jdbc.callback.RowCounterCallback
import me.kvdpxne.covilo.infrastructure.jdbc.mapping.CrimeClassificationMapper
import me.kvdpxne.covilo.util.sql.QueryBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

/**
 * @see CrimeClassificationRepository
 */
@Component
class CrimeClassificationDao @Autowired(required = true) constructor(
  private val operations: NamedParameterJdbcOperations
) : CrimeClassificationRepository {

  companion object {
    /**
     *
     */
    val FIELD_ARRAY = arrayOf(
      "$TABLE_CRIME_CLASSIFICATION.$COLUMN_IDENTIFIER",
      "$TABLE_CRIME_CLASSIFICATION.$COLUMN_KEY"
    )
  }

  override fun findByIdentifier(identifier: UUID): CrimeClassification? {
    val stringIdentifier = identifier.toString()
    val query = QueryBuilder()
      .select(*FIELD_ARRAY)
      .from(TABLE_CRIME_CLASSIFICATION)
      .where(stringIdentifier, COLUMN_IDENTIFIER)
      .end()
    return runCatching {
      operations.queryForObject(
        query,
        mapOf(COLUMN_IDENTIFIER to stringIdentifier),
        CrimeClassificationMapper
      )
    }.getOrNull()
  }

  override fun findAll(): CrimeClassifications {
    val query = QueryBuilder()
      .select(*FIELD_ARRAY)
      .from(TABLE_CRIME_CLASSIFICATION)
      .end()
    return operations.query(
      query,
      CrimeClassificationMapper
    )
  }

  @Transactional
  override fun insert(classification: CrimeClassification) {
    TODO("Not yet implemented")
  }

  @Transactional
  override fun update(classification: CrimeClassification) {
    TODO("Not yet implemented")
  }

  @Transactional
  override fun delete(identifier: UUID) {
    TODO("Not yet implemented")
  }

  override fun deleteAll() {
    TODO("Not yet implemented")
  }

  override fun count(): Int {
    val counter = RowCounterCallback()
    val query = QueryBuilder()
      .count(FIELD_ARRAY.first())
      .from(TABLE_CRIME_CLASSIFICATION)
      .end()
    operations.query(query, counter)
    return counter.count
  }
}
