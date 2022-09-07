package me.kvdpxne.covilo.api.rest.dto

import me.kvdpxne.covilo.domain.model.Crime
import me.kvdpxne.covilo.domain.model.CrimeClassification
import me.kvdpxne.covilo.domain.model.Crimes

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
    this.date.toString(),
    this.city.toDto(),
    this.classification.toDto(),
    this.perpetrator.toDto(),
    this.description,
    this.isConfirmed
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