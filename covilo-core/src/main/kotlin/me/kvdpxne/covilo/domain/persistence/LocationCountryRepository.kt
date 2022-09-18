package me.kvdpxne.covilo.domain.persistence

import me.kvdpxne.covilo.domain.model.LocationCountries
import me.kvdpxne.covilo.domain.model.LocationCountry
import java.util.UUID

/**
 * @see LocationCountry
 */
interface LocationCountryRepository {

  fun findByIdentifier(identifier: UUID): LocationCountry?

  fun findByKey(key: String): LocationCountry?

  fun findAll(): LocationCountries

  fun insert(country: LocationCountry)

  fun update(country: LocationCountry)

  fun delete(identifier: UUID)

  fun deleteAll()

  fun count(): Int
}