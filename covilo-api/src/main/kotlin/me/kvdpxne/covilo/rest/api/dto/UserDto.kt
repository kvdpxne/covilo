package me.kvdpxne.covilo.rest.api.dto

import me.kvdpxne.covilo.domain.models.User
import me.kvdpxne.covilo.domain.models.Users

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
    identifier.toString(),
    email,
    firstName.toString(),
    lastName.toString(),
    birthDate.toString(),
    createdDate.toString(),
    lastModifiedDate.toString()
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