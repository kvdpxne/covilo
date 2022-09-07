package me.kvdpxne.covilo.domain.model

import java.io.Serializable
import java.time.LocalDate
import java.util.UUID

/**
 * Shortcut to the [Crime] collection.
 */
typealias Crimes = Collection<Crime>

data class Crime(
  val identifier: UUID = UUID.randomUUID(),
  val date: LocalDate = LocalDate.now(),
  val city: LocationCity,
  val classification: CrimeClassification,
  val perpetrator: CrimePerpetrator,
  val description: String,
  val isConfirmed: Boolean = false,
) : Serializable {

  companion object {
    private const val serialVersionUID: Long = 1542620209L
  }
}
