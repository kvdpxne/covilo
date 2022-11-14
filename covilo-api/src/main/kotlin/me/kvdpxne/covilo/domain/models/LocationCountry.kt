package me.kvdpxne.covilo.domain.models

import java.io.Serializable
import java.util.UUID

/**
 * @param identifier
 * @param key
 */
data class LocationCountry(
  val identifier: UUID = UUID.randomUUID(),
  val key: String,
) : Serializable {

  companion object {
    private const val serialVersionUID: Long = 1399556807L
  }
}

typealias LocationCountries = Collection<LocationCountry>