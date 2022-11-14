package me.kvdpxne.covilo.domain.repositories

import me.kvdpxne.covilo.domain.models.LocationCities
import me.kvdpxne.covilo.domain.models.LocationCity
import java.util.UUID

/**
 * @see LocationCity
 */
interface LocationCityRepository {

  fun findByIdentifier(identifier: UUID): LocationCity?

  @Deprecated(
    message = "Sometimes a key can match more than one entity.",
    replaceWith = ReplaceWith("findAllByKey")
  )
  fun findByKey(key: String): LocationCity?

  fun findAll(): LocationCities

  fun findAllByKey(key: String): LocationCities

  fun insert(city: LocationCity)

  fun update(city: LocationCity)

  fun delete(identifier: UUID)

  fun deleteAll()

  fun count(): Int
}