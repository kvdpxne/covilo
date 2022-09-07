package me.kvdpxne.covilo.domain.model

import java.io.Serializable
import java.util.UUID

/**
 * Shortcut to the [LocationCity] collection.
 */
typealias LocationCities = Collection<LocationCity>

data class LocationCity(
  val identifier: UUID = UUID.randomUUID(),
  val key: String,
  val domesticName: String,
  val region: LocationRegion
) : Serializable {

  companion object {
    private const val serialVersionUID: Long = 1150363534L
  }

  /**
   * Quick access to the [LocationCountry] key field.
   */
  val countryKey: String
    get() = region.countryKey

  /**
   * Quick access to the [LocationRegion] key field.
   */
  val regionKey: String
    get() = region.key
}
