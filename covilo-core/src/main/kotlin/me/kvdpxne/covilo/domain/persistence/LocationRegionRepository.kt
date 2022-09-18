package me.kvdpxne.covilo.domain.persistence

import me.kvdpxne.covilo.domain.model.LocationRegion
import me.kvdpxne.covilo.domain.model.LocationRegions
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