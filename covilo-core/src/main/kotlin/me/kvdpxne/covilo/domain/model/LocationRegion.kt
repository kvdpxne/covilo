package me.kvdpxne.covilo.domain.model

import java.io.Serializable
import java.util.UUID

/**
 * Shortcut to the [LocationRegion] collection.
 */
typealias LocationRegions = Collection<LocationRegion>

/**
 * @param identifier
 * @param key
 * @param domesticName
 * @param country
 */
data class LocationRegion(
  val identifier: UUID = UUID.randomUUID(),
  val key: String,
  val domesticName: String,
  val country: LocationCountry
) : Serializable {

  companion object {
    private const val serialVersionUID: Long = 2045985117L
  }

  /**
   * Quick access to the [LocationCountry] key field.
   */
  val countryKey: String
    get() = country.key
}
