package me.kvdpxne.covilo.infrastructure.jdbc.dao

import me.kvdpxne.covilo.domain.model.Crime
import me.kvdpxne.covilo.domain.model.Crimes
import me.kvdpxne.covilo.domain.persistence.CrimeRepository
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_CITY
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_CLASSIFICATION
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_DATE
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_DESCRIPTION
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_IS_CONFIRMED
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_PERPETRATOR
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_CRIME
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_CRIME_CLASSIFICATION
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_CRIME_PERPETRATOR
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_LOCATION_CITY
import me.kvdpxne.covilo.infrastructure.jdbc.callback.RowCounterCallbackHandler
import me.kvdpxne.covilo.infrastructure.jdbc.mapping.CrimeMapper
import me.kvdpxne.covilo.util.sql.SqlCallBuilder
import me.kvdpxne.covilo.util.sql.SqlDeleteBuilder
import me.kvdpxne.covilo.util.sql.SqlInsertBuilder
import me.kvdpxne.covilo.util.sql.SqlRelation
import me.kvdpxne.covilo.util.sql.sqlColumnArrayOf
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

/**
 * @see CrimeRepository
 */
@Component
class CrimeDao @Autowired(required = true) constructor(
  private val operations: NamedParameterJdbcOperations
) : CrimeRepository {

  companion object {

    /**
     * Shortcut to the current table.
     */
    private val TABLE: String
      get() = TABLE_CRIME

    internal val COLUMNS = sqlColumnArrayOf(
      TABLE,
      COLUMN_IDENTIFIER,
      COLUMN_DATE,
      *LocationCityDao.COLUMNS.extend(),
      *CrimeClassificationDao.COLUMNS.extend(),
      *CrimePerpetratorDao.COLUMNS.extend(),
      COLUMN_DESCRIPTION,
      COLUMN_IS_CONFIRMED
    )

    internal val RELATIONS = arrayOf(
      SqlRelation(TABLE_LOCATION_CITY, TABLE, COLUMN_CITY),
      *LocationCityDao.RELATIONS,
      SqlRelation(TABLE_CRIME_CLASSIFICATION, TABLE, COLUMN_CLASSIFICATION),
      SqlRelation(TABLE_CRIME_PERPETRATOR, TABLE, COLUMN_PERPETRATOR)
    )
  }

  /**
   * Shortcut to the most used query builder based on SELECT query.
   */
  private val callBuilder: SqlCallBuilder
    get() = SqlCallBuilder().select(COLUMNS).from(TABLE).join(RELATIONS)

  override fun findByIdentifier(identifier: UUID): Crime? {
    val builder = callBuilder.where(COLUMN_IDENTIFIER, identifier)
    val query = builder.build()
    return runCatching {
      operations.queryForObject(
        query,
        builder.parameters,
        CrimeMapper
      )
    }.getOrNull()
  }

  override fun findAll(): Crimes {
    val query = callBuilder.build()
    return operations.query(
      query,
      CrimeMapper
    )
  }

  @Transactional
  override fun insert(crime: Crime) {
    val builder = SqlInsertBuilder(TABLE)
    builder[COLUMN_IDENTIFIER] = crime.identifier
    builder[COLUMN_DATE] = crime.date
    builder[COLUMN_CITY] = crime.city.identifier
    builder[COLUMN_CLASSIFICATION] = crime.classification.identifier
    builder[COLUMN_PERPETRATOR] = crime.perpetrator.identifier
    builder[COLUMN_DESCRIPTION] = crime.description
    builder[COLUMN_IS_CONFIRMED] = crime.isConfirmed
    val query = builder.build()
    operations.jdbcOperations.update(query)
  }

  @Transactional
  override fun update(crime: Crime) {
    TODO("Not yet implemented")
  }

  @Transactional
  override fun delete(identifier: UUID) {
    val builder = SqlDeleteBuilder(TABLE).where(COLUMN_IDENTIFIER, identifier)
    val query = builder.build()
    operations.update(
      query,
      builder.parameters
    )
  }

  @Transactional
  override fun deleteAll() {
    val query = SqlDeleteBuilder(TABLE).build()
    operations.jdbcOperations.update(query)
  }

  override fun count(): Int {
    val counter = RowCounterCallbackHandler()
    val query = SqlCallBuilder().count().from(TABLE).build()
    operations.query(query, counter)
    return counter.count
  }
}