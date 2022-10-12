package me.kvdpxne.covilo.api.rest.dto

import me.kvdpxne.covilo.domain.models.LocationRegion
import me.kvdpxne.covilo.domain.models.LocationRegions

/**
 * Shortcut to the [LocationRegionDto] collection.
 */
typealias LocationRegionsDto = Collection<LocationRegionDto>

/**
 * TODO doc
 */
data class LocationRegionDto(
  val key: String,
  val domesticName: String,
  val country: LocationCountryDto
)

/**
 * Transforms the [LocationRegion] entity to [LocationRegionDto].
 */
fun LocationRegion.toDto(): LocationRegionDto {
  return LocationRegionDto(
    key,
    domesticName,
    country.toDto()
  )
}

/**
 * Transforms all [LocationRegion] entities that are in the collection
 * and are not null to [LocationRegionDto].
 */
fun LocationRegions.toDto(): LocationRegionsDto {
  return mapNotNull {
    it.toDto()
  }
}