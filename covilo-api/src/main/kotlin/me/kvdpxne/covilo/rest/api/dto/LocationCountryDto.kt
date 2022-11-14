package me.kvdpxne.covilo.rest.api.dto

import me.kvdpxne.covilo.domain.models.LocationCountries
import me.kvdpxne.covilo.domain.models.LocationCountry

/**
 * Shortcut to the [LocationCountryDto] collection.
 */
typealias LocationCountriesDto = Collection<LocationCountryDto>

/**
 * TODO doc
 */
data class LocationCountryDto(
  val key: String
)

/**
 * Transforms the [LocationCountry] entity to [LocationCountryDto].
 */
fun LocationCountry.toDto(): LocationCountryDto {
  return LocationCountryDto(
    key
  )
}

/**
 * Transforms all [LocationCountry] entities that are in the collection
 * and are not null to [LocationCountryDto].
 */
fun LocationCountries.toDto(): LocationCountriesDto {
  return mapNotNull {
    it.toDto()
  }
}
