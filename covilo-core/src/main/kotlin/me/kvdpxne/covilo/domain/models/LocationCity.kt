package me.kvdpxne.covilo.domain.models

import java.io.Serializable
import java.util.UUID

/**
 * Shortcut to the [LocationCity] collection.
 */
typealias LocationCities = Collection<LocationCity>

/**
 * @param identifier
 * @param key
 * @param domesticName
 * @param region
 * @param population
 * @param capital
 */
data class LocationCity(
  val identifier: UUID = UUID.randomUUID(),
  val key: String,
  val domesticName: String,
  val region: LocationRegion,
  var population: Int = 0,
  var capital: CapitalType = CapitalType.NONE
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
