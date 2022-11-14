package me.kvdpxne.covilo.rest.api.dto

import me.kvdpxne.covilo.domain.models.CrimePerpetrator
import me.kvdpxne.covilo.domain.models.CrimePerpetrators

/**
 * Shortcut to the [CrimePerpetratorDto] collection.
 */
typealias CrimePerpetratorsDto = Collection<CrimePerpetratorDto>

/**
 * TODO doc
 */
data class CrimePerpetratorDto(
  val firstName: String?,
  val lastName: String?,
  val age: Int,
  val isCaught: Boolean
)

/**
 * Transforms the [CrimePerpetrator] entity to [CrimePerpetratorDto].
 */
fun CrimePerpetrator.toDto(): CrimePerpetratorDto {
  return CrimePerpetratorDto(
    firstName,
    lastName,
    age.toInt(),
    caught
  )
}

/**
 * Transforms all [CrimePerpetrator] entities that are in the collection
 * and are not null to [CrimePerpetratorDto].
 */
fun CrimePerpetrators.toDto(): CrimePerpetratorsDto {
  return mapNotNull {
    it.toDto()
  }
}