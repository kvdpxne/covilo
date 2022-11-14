package me.kvdpxne.covilo.domain.models

import me.kvdpxne.covilo.utils.Recognizable
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

/**
 * @param identifier
 * @param date
 * @param city
 * @param classification
 * @param perpetrator
 * @param description
 * @param confirmed
 */
data class Crime(
  override val identifier: UUID = UUID.randomUUID(),
  var city: LocationCity,
  var classification: CrimeClassification,
  var perpetrator: CrimePerpetrator,
  var description: String,
  var confirmed: Boolean = false,
  override val createdDate: LocalDateTime = LocalDateTime.now(),
  override var lastModifiedDate: LocalDateTime? = null,
) : Recognizable, Auditable, Serializable {

  companion object {
    private const val serialVersionUID: Long = 1542620209L
  }
}

/**
 * Shortcut to the [Crime] collection.
 */
typealias Crimes = Collection<Crime>