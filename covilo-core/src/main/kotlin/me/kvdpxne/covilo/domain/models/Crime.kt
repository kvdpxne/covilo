package me.kvdpxne.covilo.domain.models

import java.io.Serializable
import java.time.LocalDate
import java.util.UUID

/**
 * Shortcut to the [Crime] collection.
 */
typealias Crimes = Collection<Crime>

/**
 * @param identifier
 * @param date
 * @param city
 * @param classification
 * @param perpetrator
 * @param description
 * @param isConfirmed
 */
data class Crime(
  val identifier: UUID = UUID.randomUUID(),
  var date: LocalDate = LocalDate.now(),
  var city: LocationCity,
  var classification: CrimeClassification,
  var perpetrator: CrimePerpetrator,
  var description: String,
  var isConfirmed: Boolean = false,
) : Serializable {

  companion object {
    private const val serialVersionUID: Long = 1542620209L
  }
}
