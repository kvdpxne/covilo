package me.kvdpxne.covilo.api.rest.dto

import me.kvdpxne.covilo.domain.model.User
import me.kvdpxne.covilo.domain.model.Users

/**
 * Shortcut to the [UserDto] collection.
 */
typealias UsersDto = Collection<UserDto>

/**
 * TODO doc
 */
data class UserDto(
  val identifier: String,
  val email: String,
  val firstName: String,
  val lastName: String,
  val brithDate: String,
  val createdDate: String,
  val lastModifiedDate: String
)

/**
 * Transforms the [User] entity to [UserDto].
 */
fun User.toDto(): UserDto {
  return UserDto(
    this.identifier.toString(),
    this.email,
    this.firstName.toString(),
    this.lastName.toString(),
    this.birthDate.toString(),
    this.createdDate.toString(),
    this.lastModifiedDate.toString()
  )
}

/**
 * Transforms all [User] entities that are in the collection
 * and are not null to [UserDto].
 */
fun Users.toDto(): UsersDto {
  return mapNotNull {
    it.toDto()
  }
}