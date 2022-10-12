package me.kvdpxne.covilo.api.rest.dto

import me.kvdpxne.covilo.domain.models.CrimeClassification
import me.kvdpxne.covilo.domain.models.CrimeClassifications

/**
 * Shortcut to the [CrimeClassificationDto] collection.
 */
typealias CrimeClassificationsDto = Collection<CrimeClassificationDto>

/**
 * TODO doc
 */
data class CrimeClassificationDto(
  val key: String
)

/**
 * Transforms the [CrimeClassification] entity to [CrimeClassificationDto].
 */
fun CrimeClassification.toDto(): CrimeClassificationDto {
  return CrimeClassificationDto(
    key
  )
}

/**
 * Transforms all [CrimeClassification] entities that are in the collection
 * and are not null to [CrimeClassificationDto].
 */
fun CrimeClassifications.toDto(): CrimeClassificationsDto {
  return mapNotNull {
    it.toDto()
  }
}