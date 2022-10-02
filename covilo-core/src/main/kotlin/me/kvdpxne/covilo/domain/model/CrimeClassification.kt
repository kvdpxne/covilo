package me.kvdpxne.covilo.domain.model

import java.io.Serializable
import java.util.UUID

/**
 * Shortcut to the [CrimeClassification] collection.
 */
typealias CrimeClassifications = Collection<CrimeClassification>

/**
 * @param identifier
 * @param key
 */
data class CrimeClassification(
  val identifier: UUID = UUID.randomUUID(),
  var key: String
) : Serializable {

  companion object {
    private const val serialVersionUID: Long = 1639673346L
  }
}
