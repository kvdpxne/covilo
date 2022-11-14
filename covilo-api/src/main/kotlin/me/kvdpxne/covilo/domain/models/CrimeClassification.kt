package me.kvdpxne.covilo.domain.models

import java.io.Serializable
import java.util.UUID

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

/**
 * Shortcut to the [CrimeClassification] collection.
 */
typealias CrimeClassifications = Collection<CrimeClassification>