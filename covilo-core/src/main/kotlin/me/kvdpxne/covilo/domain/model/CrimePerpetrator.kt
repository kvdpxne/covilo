package me.kvdpxne.covilo.domain.model

import java.io.Serializable
import java.util.UUID

/**
 * Shortcut to the [CrimePerpetrator] collection.
 */
typealias CrimePerpetrators = Collection<CrimePerpetrator>

/**
 * @param identifier
 * @param firstName
 * @param lastName
 * @param age
 * @param isCaught
 */
data class CrimePerpetrator(
  val identifier: UUID = UUID.randomUUID(),
  val firstName: String,
  val lastName: String,
  val age: Int = 0,
  var isCaught: Boolean = false
) : Serializable {

  companion object {
    private const val serialVersionUID: Long = 1794827529L
  }
}