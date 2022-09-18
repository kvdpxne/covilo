package me.kvdpxne.covilo.infrastructure.jdbc.dao

import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_KEY
import me.kvdpxne.covilo.infrastructure.jdbc.TABLE_LOCATION_COUNTRY
import me.kvdpxne.covilo.domain.model.LocationCountries
import me.kvdpxne.covilo.domain.model.LocationCountry
import me.kvdpxne.covilo.domain.persistence.LocationCountryRepository
import me.kvdpxne.covilo.infrastructure.jdbc.callback.RowCounterCallback
import me.kvdpxne.covilo.infrastructure.jdbc.mapping.LocationCountryMapper
import me.kvdpxne.covilo.util.sql.QueryBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

/**
 * @see LocationCountryRepository
 */
@Component
class LocationCountryDao @Autowired(required = true) constructor(
  private val operations: NamedParameterJdbcOperations
) : LocationCountryRepository {

  companion object {

    /**
     * Current table
     */
    val TABLE: String
      get() = TABLE_LOCATION_COUNTRY

    /**
     *
     */
    val FIELD_ARRAY = arrayOf(
      "$TABLE.$COLUMN_IDENTIFIER",
      "$TABLE.$COLUMN_KEY"
    )

    private val queryBuilder = QueryBuilder()
      .select(*FIELD_ARRAY)
      .from(TABLE)
  }


  override fun findByIdentifier(identifier: UUID): LocationCountry? {
    val stringIdentifier = identifier.toString()
    val query = queryBuilder.where(stringIdentifier, COLUMN_IDENTIFIER).end()
    return runCatching {
      operations.queryForObject(
        query,
        mapOf(COLUMN_IDENTIFIER to stringIdentifier),
        LocationCountryMapper
      )
    }.getOrNull()
  }

  override fun findByKey(key: String): LocationCountry? {
    val query = queryBuilder.where(key, COLUMN_KEY).end()
    return runCatching {
      operations.queryForObject(
        query,
        mapOf(COLUMN_KEY to key),
        LocationCountryMapper
      )
    }.getOrNull()
  }

  override fun findAll(): LocationCountries {
    val query = queryBuilder.end()
    return operations.query(
      query,
      LocationCountryMapper
    )
  }

  @Transactional
  override fun insert(country: LocationCountry) {
    TODO("Not yet implemented")
  }

  @Transactional
  override fun update(country: LocationCountry) {
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
      .from(TABLE)
      .end()
    operations.query(query, counter)
    return counter.count
  }
}