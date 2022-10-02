package me.kvdpxne.covilo.domain.model

import java.io.Serializable
import java.util.UUID

/**
 * Shortcut to the [LocationCountry] collection.
 */
typealias LocationCountries = Collection<LocationCountry>

/**
 * @param identifier
 * @param key
 */
data class LocationCountry(
  val identifier: UUID = UUID.randomUUID(),
  val key: String,
) : Serializable {

  companion object {
    private const val serialVersionUID: Long = 1399556807L
  }
}
