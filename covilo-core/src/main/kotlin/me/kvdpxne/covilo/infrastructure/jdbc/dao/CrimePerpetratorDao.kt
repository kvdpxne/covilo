package me.kvdpxne.covilo.infrastructure.jdbc.dao

import me.kvdpxne.covilo.domain.model.CrimePerpetrator
import me.kvdpxne.covilo.domain.model.CrimePerpetrators
import me.kvdpxne.covilo.domain.persistence.CrimePerpetratorRepository
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_AGE
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_FIRST_NAME
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_IS_CAUGHT
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_LAST_NAME
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_CRIME_PERPETRATOR
import me.kvdpxne.covilo.infrastructure.jdbc.callback.RowCounterCallback
import me.kvdpxne.covilo.infrastructure.jdbc.mapping.CrimePerpetratorMapper
import me.kvdpxne.covilo.util.sql.QueryBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

/**
 * @see CrimePerpetratorRepository
 */
@Component
class CrimePerpetratorDao @Autowired(required = true) constructor(
  private val operations: NamedParameterJdbcOperations
) : CrimePerpetratorRepository {

  companion object {
    /**
     *
     */
    val FIELD_ARRAY = arrayOf(
      "$TABLE_CRIME_PERPETRATOR.$COLUMN_IDENTIFIER",
      "$TABLE_CRIME_PERPETRATOR.$COLUMN_FIRST_NAME",
      "$TABLE_CRIME_PERPETRATOR.$COLUMN_LAST_NAME",
      "$TABLE_CRIME_PERPETRATOR.$COLUMN_AGE",
      "$TABLE_CRIME_PERPETRATOR.$COLUMN_IS_CAUGHT"
    )
  }

  override fun findByIdentifier(identifier: UUID): CrimePerpetrator? {
    val stringIdentifier = identifier.toString()
    val query = QueryBuilder()
      .select(*FIELD_ARRAY)
      .from(TABLE_CRIME_PERPETRATOR)
      .where(stringIdentifier, COLUMN_IDENTIFIER)
      .end()
    return runCatching {
      operations.queryForObject(
        query,
        mapOf(COLUMN_IDENTIFIER to stringIdentifier),
        CrimePerpetratorMapper
      )
    }.getOrNull()
  }

  override fun findAll(): CrimePerpetrators {
    val query = QueryBuilder()
      .select(*FIELD_ARRAY)
      .from(TABLE_CRIME_PERPETRATOR)
      .end()
    return operations.query(
      query,
      CrimePerpetratorMapper
    )
  }

  @Transactional
  override fun insert(perpetrator: CrimePerpetrator) {
    TODO("Not yet implemented")
  }

  @Transactional
  override fun update(perpetrator: CrimePerpetrator) {
    TODO("Not yet implemented")
  }

  @Transactional
  override fun delete(identifier: UUID) {
    TODO("Not yet implemented")
  }

  @Transactional
  override fun deleteAll() {
    TODO("Not yet implemented")
  }

  override fun count(): Int {
    val counter = RowCounterCallback()
    val query = QueryBuilder()
      .count(FIELD_ARRAY.first())
      .end()
    operations.query(query, counter)
    return counter.count
  }
}