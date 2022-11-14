package me.kvdpxne.covilo.domain.models

import java.io.Serializable
import java.util.UUID

/**
 * @param identifier
 * @param firstName
 * @param lastName
 * @param age
 * @param caught
 */
data class CrimePerpetrator(
  val identifier: UUID = UUID.randomUUID(),
  val firstName: String?,
  val lastName: String?,
  val age: UInt = 0u,
  var caught: Boolean = false
) : Serializable {

  companion object {
    private const val serialVersionUID = 1794827529L
  }
}

/**
 * Shortcut to the [CrimePerpetrator] collection.
 */
typealias CrimePerpetrators = Collection<CrimePerpetrator>