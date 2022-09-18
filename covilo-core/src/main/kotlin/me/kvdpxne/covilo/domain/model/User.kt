package me.kvdpxne.covilo.domain.model

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

/**
 * Shortcut to the [User] collection.
 */
typealias Users = Collection<User>

data class User(
  val identifier: UUID = UUID.randomUUID(),
  var email: String,
  var password: String,
  var firstName: String? = null,
  var lastName: String? = null,
  var birthDate: LocalDate,
  override val createdDate: LocalDateTime = LocalDateTime.now(),
  override var lastModifiedDate: LocalDateTime? = null
) : Serializable, Auditable {

  companion object {
    private const val serialVersionUID: Long = 1609015556L
  }
}