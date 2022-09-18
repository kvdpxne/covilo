package me.kvdpxne.covilo.domain.persistence

import me.kvdpxne.covilo.domain.model.User
import me.kvdpxne.covilo.domain.model.Users
import java.util.UUID

/**
 * @see User
 */
interface UserRepository {

  fun findByIdentifier(identifier: UUID): User?

  fun findByName(name: String): User?

  fun findAll(): Users

  fun insert(user: User)

  fun update(user: User)

  fun delete(identifier: UUID)

  fun deleteAll()

  fun count(): Int
}