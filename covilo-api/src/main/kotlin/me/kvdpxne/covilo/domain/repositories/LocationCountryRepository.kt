package me.kvdpxne.covilo.domain.repositories

import me.kvdpxne.covilo.domain.models.LocationCountries
import me.kvdpxne.covilo.domain.models.LocationCountry
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