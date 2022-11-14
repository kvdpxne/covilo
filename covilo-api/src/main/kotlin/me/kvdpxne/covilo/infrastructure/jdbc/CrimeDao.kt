package me.kvdpxne.covilo.infrastructure.jdbc

import me.kvdpxne.covilo.CONFIRMED_COLUMN
import me.kvdpxne.covilo.CREATED_DATE_COLUMN
import me.kvdpxne.covilo.CRIME_CLASSIFICATION_COLUMN
import me.kvdpxne.covilo.CRIME_CLASSIFICATION_TABLE
import me.kvdpxne.covilo.CRIME_PERPETRATOR_COLUMN
import me.kvdpxne.covilo.CRIME_PERPETRATOR_TABLE
import me.kvdpxne.covilo.CRIME_TABLE
import me.kvdpxne.covilo.DESCRIPTION_COLUMN
import me.kvdpxne.covilo.IDENTIFIER_COLUMN
import me.kvdpxne.covilo.LAST_MODIFIED_DATE_COLUMN
import me.kvdpxne.covilo.LOCATION_CITY_COLUMN
import me.kvdpxne.covilo.LOCATION_CITY_TABLE
import me.kvdpxne.covilo.confirmed
import me.kvdpxne.covilo.createdDate
import me.kvdpxne.covilo.description
import me.kvdpxne.covilo.domain.models.Crime
import me.kvdpxne.covilo.domain.models.Crimes
import me.kvdpxne.covilo.domain.repositories.CrimeRepository
import me.kvdpxne.covilo.identifier
import me.kvdpxne.covilo.lastModifiedDate
import me.kvdpxne.covilo.utils.sql.ResultSetParser
import me.kvdpxne.covilo.utils.sql.SqlCallBuilder
import me.kvdpxne.covilo.utils.sql.SqlDeleteBuilder
import me.kvdpxne.covilo.utils.sql.SqlInsertBuilder
import me.kvdpxne.covilo.utils.sql.SqlRelation
import me.kvdpxne.covilo.utils.sql.sqlColumnArrayOf
import org.springframework.jdbc.core.RowCountCallbackHandler
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.ResultSet
import java.sql.SQLException
import java.util.UUID

/**
 * Shortcut to the current table.
 */
private val TABLE: String
  get() = CRIME_TABLE

internal object CrimeMapper : RowMapper<Crime> {

  @Throws(SQLException::class)
  override fun mapRow(result: ResultSet, row: Int): Crime {
    val parser = ResultSetParser(TABLE, result)

    // Parse the entity identifier
    val identifier = parser.identifier()

    // Parse relational entities to the entity
    val city = LocationCityMapper.mapRow(result, row)
    val classification = CrimeClassificationMapper.mapRow(result, row)
    val perpetrator = CrimePerpetratorMapper.mapRow(result, row)

    // Parse remaining fields
    val createdDate = parser.createdDate()
    val lastModifiedDate = parser.lastModifiedDate()
    val description = parser.description()
    val confirmed = parser.confirmed()

    // Initialize
    return Crime(
      identifier = identifier,
      city = city,
      classification = classification,
      perpetrator = perpetrator,
      createdDate = createdDate,
      lastModifiedDate = lastModifiedDate,
      description = description,
      confirmed = confirmed
    )
  }
}

/**
 * @see CrimeRepository
 */
@Component
class CrimeDao(
  private val operations: NamedParameterJdbcOperations
) : CrimeRepository {

  companion object {

    internal val COLUMNS = sqlColumnArrayOf(
      TABLE,
      IDENTIFIER_COLUMN,
      *LocationCityDao.COLUMNS.extend(),
      *CrimeClassificationDao.COLUMNS.extend(),
      *CrimePerpetratorDao.COLUMNS.extend(),
      DESCRIPTION_COLUMN,
      CONFIRMED_COLUMN,
      CREATED_DATE_COLUMN,
      LAST_MODIFIED_DATE_COLUMN
    )

    internal val RELATIONS = arrayOf(
      SqlRelation(LOCATION_CITY_TABLE, TABLE, LOCATION_CITY_COLUMN),
      *LocationCityDao.RELATIONS,
      SqlRelation(
        CRIME_CLASSIFICATION_TABLE,
        TABLE,
        CRIME_CLASSIFICATION_COLUMN
      ),
      SqlRelation(CRIME_PERPETRATOR_TABLE, TABLE, CRIME_PERPETRATOR_COLUMN)
    )
  }

  /**
   * Shortcut to the most used query builder based on SELECT query.
   */
  private val callBuilder: SqlCallBuilder
    get() = SqlCallBuilder().select(COLUMNS).from(TABLE).join(RELATIONS)

  override fun findByIdentifier(identifier: UUID): Crime? {
    val builder = callBuilder.where(IDENTIFIER_COLUMN, identifier)
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
    builder[IDENTIFIER_COLUMN] = crime.identifier
    builder[LOCATION_CITY_COLUMN] = crime.city.identifier
    builder[CRIME_CLASSIFICATION_COLUMN] = crime.classification.identifier
    builder[CRIME_PERPETRATOR_COLUMN] = crime.perpetrator.identifier
    builder[DESCRIPTION_COLUMN] = crime.description
    builder[CONFIRMED_COLUMN] = crime.confirmed
    builder[CREATED_DATE_COLUMN] = crime.createdDate
    builder[LAST_MODIFIED_DATE_COLUMN] = crime.lastModifiedDate
    val query = builder.build()
    operations.jdbcOperations.update(query)
  }

  @Transactional
  override fun update(crime: Crime) {
    TODO("Not yet implemented")
  }

  @Transactional
  override fun delete(identifier: UUID) {
    val builder = SqlDeleteBuilder(TABLE).where(IDENTIFIER_COLUMN, identifier)
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
    val counter = RowCountCallbackHandler()
    val query = SqlCallBuilder().count().from(TABLE).build()
    operations.query(query, counter)
    return counter.rowCount
  }
}