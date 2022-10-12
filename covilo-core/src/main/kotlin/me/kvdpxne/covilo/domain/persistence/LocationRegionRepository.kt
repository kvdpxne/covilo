package me.kvdpxne.covilo.domain.persistence

import me.kvdpxne.covilo.domain.models.LocationRegion
import me.kvdpxne.covilo.domain.models.LocationRegions
import java.util.UUID

/**
 * @see LocationRegion
 */
interface LocationRegionRepository {

  fun findByIdentifier(identifier: UUID): LocationRegion?

  fun findByKey(key: String): LocationRegion?

  fun findAll(): LocationRegions

  fun insert(region: LocationRegion)

  fun update(region: LocationRegion)

  fun delete(identifier: UUID)

  fun deleteAll()

  fun count(): Int
}