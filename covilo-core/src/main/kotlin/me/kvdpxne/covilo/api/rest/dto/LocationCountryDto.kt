package me.kvdpxne.covilo.api.rest.dto

import me.kvdpxne.covilo.domain.model.LocationCountries
import me.kvdpxne.covilo.domain.model.LocationCountry

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
