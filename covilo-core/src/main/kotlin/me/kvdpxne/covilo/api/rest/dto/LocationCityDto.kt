package me.kvdpxne.covilo.api.rest.dto

import me.kvdpxne.covilo.domain.models.LocationCities
import me.kvdpxne.covilo.domain.models.LocationCity

/**
 * Shortcut to the [LocationCityDto] collection.
 */
typealias LocationCitiesDto = Collection<LocationCityDto>

/**
 * TODO doc
 */
data class LocationCityDto(
  val key: String,
  val domesticName: String,
  val region: LocationRegionDto,
  val population: Int,
  val capital: String
)

/**
 * Transforms the [LocationCity] entity to [LocationCityDto].
 */
fun LocationCity.toDto(): LocationCityDto {
  return LocationCityDto(
    key,
    domesticName,
    region.toDto(),
    population,
    capital.toString()
  )
}

/**
 * Transforms all [LocationCity] entities that are in the collection
 * and are not null to [LocationCityDto].
 */
fun LocationCities.toDto(): LocationCitiesDto {
  return mapNotNull {
    it.toDto()
  }
}
