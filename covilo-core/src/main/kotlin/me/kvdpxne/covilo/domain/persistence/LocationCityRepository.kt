package me.kvdpxne.covilo.domain.persistence

import me.kvdpxne.covilo.domain.model.LocationCities
import me.kvdpxne.covilo.domain.model.LocationCity
import java.util.UUID

/**
 * @see LocationCity
 */
interface LocationCityRepository {

  fun findByIdentifier(identifier: UUID): LocationCity?
  fun findBySpecificKey(country: String, region: String, key: String): LocationCity?
  fun findAll(): LocationCities

  fun insert(city: LocationCity)
  fun update(city: LocationCity)

  fun delete(identifier: UUID)
  fun deleteAll()

  fun count(): Long
}