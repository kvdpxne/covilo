package me.kvdpxne.covilo.infrastructure.jdbc.dao

import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_IS_CONFIRMED
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_DATE
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_DESCRIPTION
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_CRIME
import me.kvdpxne.covilo.domain.model.Crime
import me.kvdpxne.covilo.domain.model.Crimes
import me.kvdpxne.covilo.domain.persistence.CrimeRepository
import me.kvdpxne.covilo.infrastructure.jdbc.callback.RowCounterCallback
import me.kvdpxne.covilo.infrastructure.jdbc.mapping.CrimeMapper
import me.kvdpxne.covilo.util.sql.QueryBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

/**
 * @see CrimeRepository
 */
@Component
class CrimeDao @Autowired(required = true) constructor(
  private val template: NamedParameterJdbcTemplate
) : CrimeRepository {

  companion object {
    /**
     *
     */
    val FIELD_ARRAY = arrayOf(
      "$TABLE_CRIME.$COLUMN_IDENTIFIER",
      "$TABLE_CRIME.$COLUMN_DATE",
      *LocationCityDao.FIELD_ARRAY,
      *CrimeClassificationDao.FIELD_ARRAY,
      *CrimePerpetratorDao.FIELD_ARRAY,
      "$TABLE_CRIME.$COLUMN_DESCRIPTION",
      "$TABLE_CRIME.$COLUMN_IS_CONFIRMED"
    )
  }

  override fun findByIdentifier(identifier: UUID): Crime? {
    val stringIdentifier = identifier.toString()
    val query = QueryBuilder()
      .select(*FIELD_ARRAY)
      .from(TABLE_CRIME)
      .where(stringIdentifier, COLUMN_IDENTIFIER)
      .end()
    return runCatching {
      template.queryForObject(
        query,
        mapOf(COLUMN_IDENTIFIER to stringIdentifier),
        CrimeMapper
      )
    }.getOrNull()
  }

  override fun findAll(): Crimes {
    val query = QueryBuilder()
      .select(*FIELD_ARRAY)
      .from(TABLE_CRIME)
      .end()
    return template.query(
      query,
      CrimeMapper
    )
  }

  @Transactional
  override fun insert(crime: Crime) {
    TODO("Not yet implemented")
  }

  @Transactional
  override fun update(crime: Crime) {
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
    template.query(query, counter)
    return counter.count
  }
}