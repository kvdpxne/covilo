package me.kvdpxne.covilo.rest.api.dto

import me.kvdpxne.covilo.domain.models.Crime
import me.kvdpxne.covilo.domain.models.Crimes

/**
 * Shortcut to the [CrimeDto] collection.
 */
typealias CrimesDto = Collection<CrimeDto>

/**
 * TODO doc
 */
data class CrimeDto(
  val date: String,
  val city: LocationCityDto,
  val classification: CrimeClassificationDto,
  val perpetrator: CrimePerpetratorDto,
  val description: String,
  var confirmed: Boolean
)

/**
 * Transforms the [Crime] entity to [CrimeDto].
 */
fun Crime.toDto(): CrimeDto {
  return CrimeDto(
    createdDate.toString(),
    city.toDto(),
    classification.toDto(),
    perpetrator.toDto(),
    description,
    confirmed
  )
}

/**
 * Transforms all [Crime] entities that are in the collection
 * and are not null to [CrimeDto].
 */
fun Crimes.toDto(): CrimesDto {
  return mapNotNull {
    it.toDto()
  }
}