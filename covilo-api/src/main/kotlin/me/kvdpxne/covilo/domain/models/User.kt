package me.kvdpxne.covilo.domain.models

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

/**
 * @param identifier
 * @param email
 * @param password
 * @param firstName
 * @param lastName
 * @param birthDate
 * @param createdDate
 * @param lastModifiedDate
 */
data class User(
  // the entity by default can be identifiable by identifier.
  override val identifier: UUID = UUID.randomUUID(),

  //
  override val createdDate: LocalDateTime = LocalDateTime.now(),
  override var lastModifiedDate: LocalDateTime? = null,

  // age restriction
  override var birthDate: LocalDate,
  // login credentials
  override var email: String,
  override var password: String,
  //
  var firstName: String? = null,
  var lastName: String? = null, override var enabled: Boolean,
) : Account {

  companion object {
    private const val serialVersionUID = 1609015556L
  }

  @delegate:Transient
  override val roles: Collection<String> by lazy {
    buildSet {
      add(ROLE_USER)
    }
  }
}

/**
 * Shortcut to the [User] collection.
 */
typealias Users = Collection<User>